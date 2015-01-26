(ns instance-validator.validator-test
  (:require [clojure.test :refer :all]
            cheshire.core
            [fipp.edn :refer (pprint) :rename {pprint fipp}]
            [instance-validator.validator :refer :all]))

(defn refer-private [ns]
  (doseq [[symbol var] (ns-interns ns)]
    (when (:private (meta var))
      (intern *ns* symbol var))))

(refer-private 'instance-validator.validator)

(defn parse-errors [a]
  (let [arr (cheshire.core/parse-string a true)
        entry (first arr)]
    (:errors entry)))

(defn test-helper [s]
  (let [result (with-out-str
                 (validate-instances
                  (str (clojure.java.io/file (clojure.java.io/resource (str "test-data/" s))))
                  (clojure.java.io/resource "test-properties")))]
    (parse-errors result)))

(deftest test-01-source-unit-not-found
  (let [res (test-helper "01-source-unit-not-found.edn")]
    (is (= res
           {:source-unit-not-found ["unrelaxed-periodic-cell-vector-2"]}))))

(deftest test-02-source-unit-found-but-not-allowed
  (let [res (test-helper "02-source-unit-found-but-not-allowed.edn")]
    (is (= res
           {:source-unit-found-but-not-allowed ["species"]}))))
