(ns pokupki.views.main
  (:require [pokupki.models.goods :as goods])
  (:use compojure.core)
  (:require [compojure.route :as route]
            [noir.session :as session]
            [noir.response :as resp]))


(defn get-goods-list [list-id]
  (str (goods/get-list-items list-id)))

(defn get-category-list []
  (str (goods/get-category-list)))

(defn delete-good-item [id]
  (println "delete-good-item " id)
  (goods/delete-good-item id)
  "done")

(defroutes app-routes
  (GET "/goods-list" [list-id] (get-goods-list list-id))
  (GET "/category-list" [] (get-category-list))
  (POST "/good/delete/:id" [id] (delete-good-item id)))
