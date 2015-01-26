(defproject instance-validator "1.0.0"
  :description "KIM Property Instance Validator"
  :url "https://github.com/openkim/openkim-properties"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [prismatic/schema "0.2.4"]
                 [org.clojure/tools.cli "0.3.1"]
                 [fipp "0.4.2"]
                 [me.raynes/fs "1.4.6"]
                 ;;[net.mikera/core.matrix "0.24.0"]
                 [compojure "1.1.8"]
                 [ring "1.3.0"]
                 [cheshire "5.3.1"]
                 [ns-tracker "0.2.2"]]
  :target-path ".target"
  :compile-path ".target/classes"
  :resource-paths ["test/resources"]
  :profiles {:dev {:resource-paths ["test/resources"]
                   :test-paths ["test"]}}
  :main instance-validator.validator)
