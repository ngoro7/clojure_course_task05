(ns pokupki.main
  (:require 
  	[enfocus.core :as ef]
  	[helpers.util :as util])
  (:require-macros [enfocus.macros :as em])
  (:use [jayq.core :only [$ css inner]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Snippets
;;

(em/defsnippet sn-good-group "/html/snippets.html" [:.goodsgroup]
  [{:keys [id name]}]
  [:.goodsgroup] (em/set-attr :id (str "group" id))
  [:h4] (em/content name))


(em/defsnippet sn-good-item "/html/snippets.html" [:.gooditem]
  [{:keys [id itemno name comment]}]
  [:.gooditem] (em/set-attr :id (str "good" id))
  [:.goodno] (em/content (str itemno ". "))
  [:strong] (em/content name)
  [:small] (em/content (if (nil? comment) "" comment)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Show snippets
;;

(defn full-page [args]
  (em/at js/document
  	["#goodslist"] (em/content (:goodslist args))))

(defn ^:export populate-list [data]
  (let [gr (key (first data))]
    (full-page {:goodslist (sn-good-group gr)})))




;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Entry point
;;

(defn ^:export redraw-page []
   (util/get-data "/goods-list" populate-list))

(set! (.-onload js/window) redraw-page)
