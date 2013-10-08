(ns parseapp-clj.query
  (:require [parseapp-clj.rest :as rest]
            [clojure.data.json :as json])
  (:import java.net.URLEncoder))

(defn get-result [response count?]
  (if count?
    (:count response)
    (:results response)))

(defn query [app class query & {:keys [limit skip count] :or {limit 100 skip 0 count false}}]
  (-> (rest/GET app (str "/classes/"class
                         "?where="(URLEncoder/encode (json/write-str query))
                         "&limit="limit"&skip="skip"&count="(if count 1 0)))
      :body
      (json/read-str :key-fn keyword)
      (get-result count)))
