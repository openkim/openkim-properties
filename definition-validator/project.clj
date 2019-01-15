(defproject definition-validator "1.0.0"
  :description "KIM Property Definition Validator"
  :url "https://github.com/openkim/openkim-properties"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [prismatic/schema "1.1.9"]
                 [org.clojure/tools.cli "0.4.1"]
                 [fipp "0.6.14"]
                 [me.raynes/fs "1.4.6"]]
  :target-path ".target"
  :compile-path ".target/classes"
  :main definition-validator.validator)
