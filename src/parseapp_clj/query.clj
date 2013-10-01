(ns parseapp-clj.query
  (:require [parseapp-clj.rest :as rest]
            [clojure.data.json :as json])
  (:import java.net.URLEncoder))

(defn query [app class query]
  (-> (rest/GET app (str "/classes/"class"?where="
                         (URLEncoder/encode (json/write-str query))))
      :body
      (json/read-str :key-fn keyword)
      :results))
