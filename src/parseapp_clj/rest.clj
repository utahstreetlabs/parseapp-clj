(ns parseapp-clj.rest
  (:require [parseapp-clj.app :as app]
            [clj-http.client :as http]))


(defn make-request [app path & [request]]
  (-> request
      (assoc :url (str "https://api.parse.com/1" path))
      (update-in [:headers] #(merge % {"X-Parse-Application-Id" (::app/id app)
                                       "X-Parse-REST-API-Key" (::app/rest-api-key app)}))))

(defn GET [app path & [request]]
  (http/request (assoc (make-request app path request) :method :get)))

(defn POST [app path & [request]]
  (http/request (-> {:content-type :json}
                    (merge (make-request app path request))
                    (assoc :method :post))))

(defn DELETE [app path & [request]]
  (http/request (assoc (make-request app path request) :method :delete)))
