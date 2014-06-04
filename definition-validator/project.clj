(defproject definition-validator "1.0.0"
  :description "KIM Property Definition Validator"
  :url "https://github.com/openkim/openkim-properties"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [prismatic/schema "0.2.2"]
                 [org.clojure/tools.cli "0.3.1"]
                 [fipp "0.4.2"]
                 [me.raynes/fs "1.4.4"]]
  :target-path ".target"
  :compile-path ".target/classes"
  :main definition-validator.validator)
