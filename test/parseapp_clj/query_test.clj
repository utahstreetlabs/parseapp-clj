(ns parseapp-clj.rest-test
  (:require [parseapp-clj.app :refer [parse-app]]
            [parseapp-clj.integration-tests :refer [app with-baseline-parse-data baseline-data]]
            [clojure.data.json :as json]

            [clojure.test :refer :all]
            [parseapp-clj.query :refer :all]))

(use-fixtures :each (with-baseline-parse-data {:whatsit {:name "a whatsit"}}))

(deftest querying-test
  (testing "querying for a whatsit named hoosit"
    (is (= []  (query app 'Whatsit {:name "a hoosit"}))))
  (testing "querying for a whatsit named whatsit"
    (is (= [(:objectId (:whatsit @baseline-data))] (map :objectId (query app 'Whatsit {:name "a whatsit"}))))))
