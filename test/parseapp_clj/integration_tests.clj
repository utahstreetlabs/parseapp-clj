(ns parseapp-clj.integration-tests
  (:require [parseapp-clj.app :refer [parse-app]]
            [clojure.data.json :as json]
            [clojure.test :refer :all]
            [parseapp-clj.rest :refer :all]))


(def app (let [file (str (System/getProperty "user.home")"/.parseapp-clj")
               {:keys [app-id rest-api-key]} (read-string (slurp file))]
           (parse-app app-id rest-api-key)))

(def baseline-data (atom {}))

(defn with-baseline-parse-data [data]
  (fn [test]
    (doseq [[key datum] data]
      (let [response (POST app "/classes/Whatsit" (json/write-str {:name "a whatsit"}))
            saved-datum (json/read-str (:body response) :key-fn keyword)]
        (swap! baseline-data #(assoc % key (merge datum saved-datum)))))
    (test)
    (doseq [[_ saved-datum] @baseline-data]
      (DELETE app (str "/classes/Whatsit/"(:objectId saved-datum))))))
