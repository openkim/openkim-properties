(defproject definition-validator "1.0.0"
  :description "KIM Property Definition Validator"
  :url "https://github.com/openkim/openkim-properties"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [prismatic/schema "1.1.0"]
                 [org.clojure/tools.cli "0.3.3"]
                 [fipp "0.6.4"]
                 [me.raynes/fs "1.4.6"]]
  :target-path ".target"
  :compile-path ".target/classes"
  :main definition-validator.validator)
