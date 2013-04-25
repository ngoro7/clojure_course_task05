(ns pokupki.views.main
  (:require [pokupki.models.goods :as goods])
  (:use compojure.core)
  (:require [compojure.route :as route]
            [noir.session :as session]
            [noir.response :as resp]))

(defn get-goods-list [list-id]
  (str (goods/get-list-items list-id)))


(defroutes app-routes
  (GET "/goods-list" [list-id] (get-goods-list list-id)))
