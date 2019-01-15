(defproject instance-validator "1.0.0"
  :description "KIM Property Instance Validator"
  :url "https://github.com/openkim/openkim-properties"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [prismatic/schema "1.1.9"]
                 [org.clojure/tools.cli "0.4.1"]
                 [fipp "0.6.14"]
                 [me.raynes/fs "1.4.6"]
                 ;;[net.mikera/core.matrix "0.24.0"]
                 [compojure "1.6.1"]
                 [ring "1.7.1"]
                 [cheshire "5.8.1"]
                 [ns-tracker "0.3.1"]]
  :target-path ".target"
  :compile-path ".target/classes"
  :resource-paths ["test/resources"]
  :profiles {:dev {:resource-paths ["test/resources"]
                   :test-paths ["test"]}}
  :main instance-validator.validator)
