(defproject instance-validator "1.0.0"
  :description "KIM Property Instance Validator"
  :url "https://github.com/openkim/openkim-properties"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [prismatic/schema "1.1.0"]
                 [org.clojure/tools.cli "0.3.3"]
                 [fipp "0.6.4"]
                 [me.raynes/fs "1.4.6"]
                 ;;[net.mikera/core.matrix "0.24.0"]
                 [compojure "1.5.0"]
                 [ring "1.4.0"]
                 [cheshire "5.5.0"]
                 [ns-tracker "0.3.0"]]
  :target-path ".target"
  :compile-path ".target/classes"
  :resource-paths ["test/resources"]
  :profiles {:dev {:resource-paths ["test/resources"]
                   :test-paths ["test"]}}
  :main instance-validator.validator)
