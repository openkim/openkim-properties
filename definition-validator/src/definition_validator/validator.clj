(ns definition-validator.validator
  (:require schema.core
            clojure.edn
            clojure.tools.cli
            [me.raynes.fs :as fs]
            [fipp.edn :refer (pprint) :rename {pprint fipp}]))

(defn- check-minimum-java-version []
  (let [java-version (System/getProperty "java.version")
        [a b] (clojure.string/split java-version #"\.")]
    (when (and (<= (read-string a) 1)
               (<  (read-string b) 7))
      (throw (Exception. (format "Java version 1.7 or greater required, detected %s" java-version))))))

(check-minimum-java-version)

(defn- add-to-errors
  [m k item]
  (let [previous (m k)
        previous (if (nil? previous)
                   []
                   previous)
        updated (into previous item)]
    (assoc m k updated)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; checks for required keys

(defn- check-property-id-present
  [m errors]
  (if-not (m "property-id")
    (add-to-errors errors :property-id ["required key not found"])
    errors))

(defn- check-property-title-present
  [m errors]
  (if-not (m "property-title")
    (add-to-errors errors :property-title ["required key not found"])
    errors))

(defn- check-property-description-present
  [m errors]
  (if-not (m "property-description")
    (add-to-errors errors :property-description ["required key not found"])
    errors))

(defn- check-required-keys-present
  [m errors]
  (->> (check-property-id-present m errors)
       (check-property-title-present m)
       (check-property-description-present m)))

(defn- check-property-id-format
  [m errors]
  (let [property-id (m "property-id")]
    (if-not (re-find #"^tag:[^A-Z]*@[^A-Z]*,\d{4}-\d{2}-\d{2}:property/[a-z0-9\-]*$" property-id)
      (add-to-errors errors :property-id ["doesn't meet format specification"])
      errors)))

(defn- check-property-title-doesnt-end-in-period
  [m errors]
  (let [property-title (m "property-title")]
    (if (re-find #"\.$" property-title)
      (add-to-errors errors :property-title ["ends in a period"])
      errors)))

(defn- check-required-keys
  [m]
  (let [errors {}
        errors (check-required-keys-present m errors)]
    (if-not (empty? errors)
      errors
      (->> (check-property-title-doesnt-end-in-period m errors)
           (check-property-id-format m)))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; checks for optional keys

(def required-keys
  ["property-id"
   "property-title"
   "property-description"])

(defn- map-with-only-optional-keys
  [m]
  (apply (partial dissoc m) required-keys))

(def optional-key-structure
  "Structure for the map corresponding to an optional key."
  {(schema.core/required-key "type") schema.core/Str
   (schema.core/required-key "has-unit") schema.core/Bool
   (schema.core/required-key "extent") [(schema.core/either schema.core/Str schema.core/Int)]
   (schema.core/required-key "required") schema.core/Bool
   (schema.core/required-key "description") schema.core/Str})

(defn- check-optional-key-structure
  [k m errors]
  (try
    (schema.core/validate optional-key-structure m)
    ;; If validation is a success, proceed to return error hash unmodified.
    ;; Otherwise, an exception will be raised.
    errors
    (catch clojure.lang.ExceptionInfo
      e
      (add-to-errors errors (keyword k) [(.getMessage e)]))))

(defn- check-value-is-a-map
  [k m errors]
  (if-not (map? m)
    (add-to-errors errors (keyword k) ["value is not a map"])
    errors))

(defn- check-key-format
  [s errors]
  (if (re-find #"[^a-z0-9\-]" s)
    (add-to-errors errors :optional-keys [(str "format doesn't meet specification for key \"" s "\"")])
    errors))

(defn- check-key-extent-string-forms1
  "Returns a vector of all invalid strings for `extent`."
  [m]
  (let [extent-vec (m "extent")]
    (vec (remove nil?
            (map #(when (and (string? %)
                             (not (= % ":")))
                    (str "invalid string for \"extent\": \"" % "\""))
                 extent-vec)))))

(defn- check-key-extent-string-forms
  "Check the strings contained in the optional key `extent`.

   The value `\":\"` is the only valid string."
  [k m errors]
  (let [ret (check-key-extent-string-forms1 m)]
    (if (empty? ret)
      errors
      (add-to-errors errors (keyword k) ret))))

(defn- check-key-type-is-valid
  "Check the optional key `type` contains a value of \"string\", \"float\",
   \"int\", or \"bool\"."
  [k m errors]
  (let [type-str (m "type")]
    (if (some #{"string" "file" "float" "int" "bool"} [type-str])
      errors
      (add-to-errors errors (keyword k) (vector (str "invalid string for \"type\": \"" type-str "\""))))))

(defn- check-optional-key
  [k v errors]
  (let [tmperrors (check-value-is-a-map k v errors)]
    (if-not (empty? tmperrors)
      ;; Don't proceed if the value isn't a map
      (into errors tmperrors)
      (->> (check-key-format k errors)
           (check-optional-key-structure k v)
           (check-key-extent-string-forms k v)
           (check-key-type-is-valid k v)))))

(defn- check-optional-keys
  "In map m, for each key-value pair, the value should be a map."
  [m]
  (let [errors {}]
    (map
      (fn [[k v]] (check-optional-key k v errors))
      m)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; main

(defn validate [s]
  (let [content (clojure.edn/read-string s)
        errors1 (check-required-keys content)
        errors2 (apply (partial merge-with into)
                       (check-optional-keys (map-with-only-optional-keys content)))
        merged (into errors1 errors2)]
    (if (empty? merged)
      (println "Property Definition passed Definition Validator\n" (content "property-id") "\n")
      (binding [*out* *err*]
        (println "Errors for" (content "property-id") "\n")
        (fipp merged)
        (println)))))

(defn validate-file [path]
  (if-not (fs/exists? path)
    (println (str "Error: file \"" path "\" does not exist"))
    (validate (slurp path))))

(defn -main [& args]
  (let [[opts args banner] (clojure.tools.cli/cli args
                                                  ["-h" "--help" "Print this help"
                                                   :default false :flag true]
                                                  ["-i" "--input" "Input file (EDN format)"])]
    (when (:help opts)
      (println banner))
    (if (:input opts)
      (validate-file (:input opts))
      (do (println "Error: please specify input file")
          (println banner)))))
