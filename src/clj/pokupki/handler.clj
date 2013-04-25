(ns pokupki.handler
  (:use compojure.core
        [korma db core])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [noir.util.middleware :as noir]
            [noir.session :as session]
            [noir.response :as resp]
            [pokupki.views.main :as v-main]
            ))

;(def korma-db {:classname "com.mysql.jdbc.Driver"
;                   :subprotocol "mysql"
;                   :user "pok"
;                   :password "pokkey"
;                   :subname "//127.0.0.1:3306/pokupki?useUnicode=true&characterEncoding=utf8"
;                   :delimiters "`"})


(defroutes app-routes
  (GET "/" [] (resp/redirect "/html/main.html"))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> [v-main/app-routes
       (handler/site app-routes)]
      noir/app-handler
      noir/war-handler))

