(ns parseapp-clj.app)

(defn parse-app [id rest-api-key]
  {::id id
   ::rest-api-key rest-api-key})
