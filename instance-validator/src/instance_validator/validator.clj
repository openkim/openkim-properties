(ns instance-validator.validator
  (:require schema.core
            clojure.edn
            clojure.tools.cli
            clojure.core.matrix
            [me.raynes.fs :as fs]
            [fipp.edn :refer (pprint) :rename {pprint fipp}]
            ns-tracker.core))

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

(def content (clojure.edn/read-string (slurp "instance.edn")))

(defn- check-property-id-present
  [m errors]
  (if-not (m "property-id")
    (add-to-errors errors :property-id ["required key not found"])
    errors))

(defn- check-instance-id-present
  [m errors]
  (if-not (m "instance-id")
    (add-to-errors errors :instance-id ["required key not found"])
    errors))

(defn- check-required-keys-present
  [m errors]
  (->> (check-property-id-present m errors)
       (check-instance-id-present m)))

(defn- check-instance-id-is-an-integer
  [m errors]
  (if-not (integer? (m "instance-id"))
    (add-to-errors errors :instance-id ["not an integer"])
    errors))

(defn- check-required-keys
  [m]
  (let [errors {}
        errors (check-required-keys-present m errors)]
    (if-not (empty? errors)
      errors
      (->> (check-instance-id-is-an-integer m errors)
           #_(check- m)))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; checks for optional keys

(defn- check-value-is-a-map
  [k m errors]
  (if-not (map? m)
    (add-to-errors errors (keyword k) ["value is not a map"])
    errors))

(defn- check-key-has-a-definition
  [k mdef errors]
  (if-not mdef ;; mdef is nil
    (add-to-errors errors k ["does not have corresponding definition"])
    errors))

(def required-keys
  ["property-id"
   "instance-id"])

(defn- map-with-only-optional-keys
  [m]
  (apply (partial dissoc m) required-keys))

(defn- check-extent-isnt-a-collection
  [k item errors]
  (if (coll? item)
    (add-to-errors errors k ["extent specifies single item, but source-value is in an array"])
    {}))

(defn- check-extent-is-a-collection
  [k item errors]
  (if-not (coll? item)
    (add-to-errors errors k ["extent specifies array of items, but source-value is a single item"])
    {}))

(defn- check-extent-basic
  [k v mdef errors]
  (let [source-value (v "source-value")
        definition-extent (mdef "extent")]
    (if (empty? definition-extent)
      (check-extent-isnt-a-collection k source-value errors)
      (check-extent-is-a-collection k source-value errors))))

(defn- check-source-value-collection-have-same-value
  [k v errors]
  (let [source-value (v "source-value")
        flattened (flatten (vector source-value))
        colltype (type (first flattened))
        flattened-without-colltype (remove #(= colltype (type %)) flattened)]
    (if-not (empty? flattened-without-colltype)
      (add-to-errors errors k ["not all items are of the same type"])
      errors)))

(defn- check-key-type
  [k v mdef errors]
  (let [source-value (v "source-value")
        flattened (flatten (vector source-value))
        first-flattened (first flattened)
        definition-type (mdef "type")]
    (condp = definition-type
      "string" (if (string? first-flattened)
                 errors
                 (add-to-errors errors k ["not of declared type string"]))
      "float" (if (float? first-flattened)
                errors
                (add-to-errors errors k ["not of declared type float"]))
      "int" (if (integer? first-flattened)
              errors
              (add-to-errors errors k ["not of declared type int"]))
      "bool" (if (or (= false first-flattened)
                     (= true first-flattened))
               errors
               (add-to-errors errors k ["not of declared type bool"]))
      (throw (Exception. (str "unrecognized type in Propety definition \"" type "\""))))))

#_(defn- check-extent-dimensions
    [k v mdef errors]
    (let [source-value (v "source-value")
          definition-extent (mdef "extent")]))

(defn- check-optional-key
  [k v mdef errors]
  "k - optional key name
   v - map containing `source-value`
   mdef - map containing definition corresponding to optional key name"
  (let [tmperrors (check-value-is-a-map k v errors)
        tmperrors (check-key-has-a-definition k mdef errors)]
    (if-not (empty? tmperrors)
      ;; Don't proceed if the value isn't a map
      (into errors tmperrors)
      (->> (check-extent-basic k v mdef errors)
           (check-source-value-collection-have-same-value k v)
           (check-key-type k v mdef)))))

(defn- check-optional-keys
  "In map m, for each key-value pair, the value should be a map."
  [m mdef]
  (let [errors {}
        property-id (m "property-id")]
    (map
      (fn [[k v]] (check-optional-key k v (mdef k) errors))
      m)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; checks for optional keys that are marked required in the definition

(def definition-required-keys
  ["property-id"
   "property-title"
   "property-description"])

(defn- definition-map-with-only-optional-keys
  [m]
  (apply (partial dissoc m) definition-required-keys))

(defn- optional-keys-marked-required
  [mdef]
  (remove nil?
          (map
            (fn [[k v]]
              (let [required (v "required")]
                (when required k)))
            (definition-map-with-only-optional-keys mdef))))

(defn- check-optional-keys-marked-required-are-present
  [m mdef errors]
  (let [keys-not-found-in-instance (remove nil?
                                           (map #(when-not (m %) %)
                                             (optional-keys-marked-required mdef)))]
    (if-not (empty? keys-not-found-in-instance)
      (add-to-errors errors :required-keys-not-found keys-not-found-in-instance)
      {})))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; main

(defn validate [instance-s definition-s]
  (let [instance (clojure.edn/read-string instance-s)
        definition (clojure.edn/read-string definition-s)
        errors1 (check-required-keys instance)
        errors2 (apply (partial merge-with into)
                       (check-optional-keys (map-with-only-optional-keys instance) definition))
        errors3 (check-optional-keys-marked-required-are-present (map-with-only-optional-keys instance) definition {})
        merged (into errors1 errors2)
        merged (into merged errors3)]
    (if (empty? merged)
      (println "Property Instance passed Instance Validator\n" (instance "property-id") "\n")
      (binding [*out* *err*]
        (println "Errors for" (instance "property-id") "\n")
        (fipp merged)
        (println)))))

(defn validate-file [path-instance path-definition]
  (cond
    (not (fs/exists? path-instance))
    (println (str "Error: file \"" path-instance "\" does not exist"))
    (not (fs/exists? path-definition))
    (println (str "Error: file \"" path-definition "\" does not exist"))
    :else (validate (slurp path-instance) (slurp path-definition))))

(defn -main [& args]
  (let [[opts args banner] (clojure.tools.cli/cli args
                                                  ["-h" "--help" "Print this help"
                                                   :default false :flag true]
                                                  ["-i" "--input" "Input Property Instance file (EDN format)"]
                                                  ["-d" "--definition" "Input Property Definition file (EDN format)"])]
    (when (:help opts)
      (println banner))
    (when-not (:input opts)
      (println "Error: please specify Property Instance file"))
    (when-not (:definition opts)
      (println "Error: please specify Property Definition file"))
    (if (and (:input opts)
             (:definition opts))
      (validate-file (:input opts)
                     (:definition opts))
      (println banner))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; development

(defn check-namespace-changes [track]
  (try
    (doseq [ns-sym (track)]
      (println "\nReloading namespace:" ns-sym)
      (require ns-sym :reload))
    (catch Throwable e (.printStackTrace e)))
  (Thread/sleep 500))

(defn start-nstracker []
  (let [track (ns-tracker.core/ns-tracker ["src" "checkouts"])]
    (doto
      (Thread.
        #(while true
           (check-namespace-changes track)))
      (.setDaemon true)
      (.start))))

#_(start-nstracker)

(require 'clojure.repl)
