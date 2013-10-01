(ns parseapp-clj.rest-test
  (:require [parseapp-clj.app :refer [parse-app]]
            [parseapp-clj.integration-tests :refer [app with-baseline-parse-data baseline-data]]
            [clojure.data.json :as json]

            [clojure.test :refer :all]
            [parseapp-clj.rest :refer :all]))

(use-fixtures :each (with-baseline-parse-data {:whatsit {:name "a whatsit"}}))

(deftest get-test
  (testing "getting a whatsit"
    (is (= "a whatsit" (-> (GET app (str "/classes/Whatsit/"(:objectId (:whatsit @baseline-data))))
                           :body
                           (json/read-str :key-fn keyword)
                           :name)))))
