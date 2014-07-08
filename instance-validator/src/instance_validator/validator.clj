(ns instance-validator.validator
  (:require schema.core
            clojure.edn
            clojure.tools.cli
            [me.raynes.fs :as fs]
            [fipp.edn :refer (pprint) :rename {pprint fipp}]
            [compojure.route]
            [compojure.handler]
            [compojure.core]
            cheshire.core
            [ring.adapter.jetty]
            ns-tracker.core))

(defn- add-to-errors
  [m k item]
  (let [previous (m k)
        previous (if (nil? previous)
                   []
                   previous)
        updated (into previous item)]
    (assoc m k updated)))

(defn- validate-property-id-format
  [s]
  (when-not (re-find #"^tag:[^A-Z]*@[^A-Z]*,\d{4}-\d{2}-\d{2}:property/[a-z0-9\-]*$" s)
    (throw (Exception. (str "property-id format invalid: " s)))))

(defn- property-id->path
  "Given a property-id in Tag URI form, such as:

   tag:staff@noreply.openkim.org,2014-04-15:property/cohesive-energy-relation-cubic-crystal

   returns a relative path:

   cohesive-energy-relation-cubic-crystal/2014-04-15-staff@noreply.openkim.org/cohesive-energy-relation-cubic-crystal.edn"
  ([property-id]
   (validate-property-id-format property-id)
   (let [v (clojure.string/split property-id #"tag:|,|:property/")
         email (v 1)
         date (v 2)
         pname (v 3)
         path (str pname "/" date "-" email "/" pname ".edn")]
     path))
  ([property-id pre-path]
   (let [pre-path (clojure.string/replace pre-path #"/$" "")]
     (str pre-path "/" (property-id->path property-id)))))

(defn- property-id->path->edn
  [property-id pre-path]
  (clojure.edn/read-string (slurp (property-id->path property-id pre-path))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; checks for required keys

(defn- check-property-id-format
  [m errors]
  (if-not (re-find #"^tag:[^A-Z]*@[^A-Z]*,\d{4}-\d{2}-\d{2}:property/[a-z0-9\-]*$" (m "property-id"))
    (add-to-errors errors :property-id ["invalid format"])
    errors))

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
           (check-property-id-format m)
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

(defn- items-to-types1
  [item]
  (cond
    (string? item) "string"
    (float? item) "float"
    (integer? item) "int"
    (or (= true item) (= false item)) "bool"
    :else (throw (Exception. (str "unrecognized type in Propety Instance for \"" item "\"")))))

(defn- items-to-types
  [items]
  (let [flattened (flatten (vector items))
        types (map items-to-types1 flattened)]
    (set types)))

(defn- check-key-type
  [k v mdef errors]
  (let [source-value (v "source-value")
        set-of-types (items-to-types source-value)
        definition-type (mdef "type")]
    (condp = definition-type
      "string" (if (= set-of-types #{"string"})
                 errors
                 (add-to-errors errors k ["not of declared type string"]))
      "float" (if (or (= set-of-types #{"float"})
                      (= set-of-types #{"int"})
                      (= set-of-types #{"float" "int"}))
                errors
                (add-to-errors errors k ["not of declared type float"]))
      "int" (if (= set-of-types #{"int"})
              errors
              (add-to-errors errors k ["not of declared type int"]))
      "bool" (if (= set-of-types #{"bool"})
               errors
               (add-to-errors errors k ["not of declared type bool"]))
      (throw (Exception. (str "unrecognized type in Propety definition \"" definition-type "\""))))))

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
           (check-key-type k v mdef)))))

(defn- check-optional-keys
  "In map m, for each key-value pair, the value should be a map."
  [m property-id definitions-dir]
  (let [errors {}
        mdef (property-id->path->edn property-id definitions-dir)]
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
  [m property-id definitions-dir errors]
  (let [mdef (property-id->path->edn property-id definitions-dir)
        keys-not-found-in-instance (remove nil?
                                           (map #(when-not (m %) %)
                                                (optional-keys-marked-required mdef)))]
    (if-not (empty? keys-not-found-in-instance)
      (add-to-errors errors :required-keys-not-found keys-not-found-in-instance)
      {})))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; main

(defn validate [path-instances-file instance definitions-dir]
  (let [errors1 (check-required-keys instance)
        property-id (instance "property-id")
        errors2 (if (empty? errors1)
                  (apply (partial merge-with into)
                         (check-optional-keys (map-with-only-optional-keys instance) property-id definitions-dir))
                  {})
        errors3 (if (empty? errors1)
                  (check-optional-keys-marked-required-are-present (map-with-only-optional-keys instance) property-id definitions-dir {})
                  {})
        merged (into errors1 errors2)
        merged (into merged errors3)
        valid (empty? merged)
        report {"property-id" (instance "property-id")
                "filepath" path-instances-file
                "valid" valid}
        report (if valid
                 report
                 (into report {"errors" merged}))]
    report))

(defn validate-instances [path-instances-file path-definitions-dir]
  (when-not (fs/directory? path-definitions-dir)
    (throw (Exception. (str "Error, not a directory: " path-definitions-dir))))
  (when-not (fs/file? path-instances-file)
    (throw (Exception. (str "Error, not a file: " path-instances-file))))
  (let [instances (flatten
                    (clojure.edn/read-string
                      (str "[" (slurp path-instances-file) "]")))]
    (println (cheshire.core/generate-string
               (doall (map #(validate path-instances-file % path-definitions-dir) instances)) {:pretty true}))))

(defn web-validate-instances [instances-content path-definitions-dir]
  (when-not (fs/directory? path-definitions-dir)
    (throw (Exception. (str "Error, not a directory: " path-definitions-dir))))
  (let [path-instances-file "web submission"
        instances (flatten
                    (clojure.edn/read-string
                      (str "[" instances-content "]")))]
    (cheshire.core/generate-string
      (doall (map #(validate path-instances-file % path-definitions-dir) instances)) {:pretty true})))

(def atom-path-definitions-dir (atom ()))
(def atom-port (atom ()))

(compojure.core/defroutes routes
  (compojure.core/GET "/" []
                      (str "Send a POST request to this webservice, for example: <br><br><tt>curl -X POST http://localhost:" @atom-port " -d @my-property-instance.edn</tt>"))
  (compojure.core/POST "/" {body :body}
                       (web-validate-instances (slurp body) @atom-path-definitions-dir)))

(def webapp
  (-> (compojure.core/routes
        routes)))

(defn webserver
  [port path-definitions-dir]
  (reset! atom-path-definitions-dir path-definitions-dir)
  (reset! atom-port port)
  (ring.adapter.jetty/run-jetty #'webapp
                                {:port port :join? false})
  (println "Started web service on port" port))

(defn -main [& args]
  (let [[opts args banner] (clojure.tools.cli/cli args
                                                  ["-h" "--help" "Print this help"
                                                   :default false :flag true]
                                                  ["-i" "--input" "Input Property Instance file (EDN format)"]
                                                  ["-d" "--definitions-path" "Path to directory containing Property Definition files (EDN format)\nThe subdirectories are expected conform to the structure:\n[definitions path]/[property short name]/[date]-[email]/[property short name].edn\n"]
                                                  ["-p" "--port" "Port for web service\nThis takes the place of the `-i` option, the `-d` option must still be specified"
                                                   :parse-fn #(Integer/parseInt %)
                                                   :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]])]
    (when (:help opts)
      (println banner))
    (cond
      (not (:definitions-path opts)) (do
                                       (println "Error: please specify path to Property Definition files")
                                       (println banner))
      (and (:input opts)
           (:definitions-path opts)) (validate-instances (:input opts) (:definitions-path opts))
      (and (:port opts)
           (:definitions-path opts)) (webserver (:port opts) (:definitions-path opts))
      :else (do (println "Error: please specify Property Instance file, or port for web service")
                (println banner)))))

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
