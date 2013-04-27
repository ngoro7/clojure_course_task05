(ns pokupki.main
  (:require 
  	[enfocus.core :as ef]
  	[helpers.util :as util])
  (:require-macros [enfocus.macros :as em])
  (:use [jayq.core :only [$ css inner]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Snippets
;;

(em/defsnippet sn-good-item "/html/snippets.html" [:.gooditem]
  [{:keys [id itemno name comment]}]
  [:.gooditem] (em/set-attr :id (str "good" id))
  [:.goodno] (em/content (str itemno ". "))
  [:strong] (em/content name)
  [:small] (em/content (if (nil? comment) "" comment))
  [:.btndel] (em/set-attr :onclick (str "pokupki.main.delete_good_item(" id ");")))

(em/defsnippet sn-good-group "/html/snippets.html" [:.goodsgroup]
  [{{:keys [id name]} :category goods :goods}]
  [:.goodsgroup] (em/set-attr :id (str "group" id))
  [:h4] (em/content name)
  [:.goods] (em/content (map sn-good-item goods)))

(defn category-options [category-list]
  (let [res (clojure.string/join
    (map (fn [{:keys [id name]}]
      (str "<option value='" id  "'>" name "</option>")) category-list))]
    ;(.log js/console res)
    res))

(em/defsnippet sn-category-list "/html/snippets.html" [:select]
  [category-list]
  [:select] (em/html-content (category-options category-list)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Show snippets
;;

(defn populate-category-list [data]
  ;(.log js/console data)
  (em/at js/document
  	["#category"] (em/content (sn-category-list data))))

(defn populate-goods-list [data]
  (em/at js/document
  	["#goodslist"] (em/content (map sn-good-group data))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Actions
;;
(declare ^:export redraw-page)

(defn ^:export delete-good-item [id]
  (util/post-data (str "/good/delete/" id) redraw-page))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Entry point
;;

(defn ^:export redraw-page []
  (util/get-data "/category-list" populate-category-list)
  (util/get-data "/goods-list" populate-goods-list))


(set! (.-onload js/window) redraw-page)
