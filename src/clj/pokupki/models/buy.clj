(ns pokupki.models.buy
  (:require [pokupki.db :as db])
  (:use [korma db core]))

(def buylists
  [{:id 2 :name "Текущий"}
   {:id 3 :name "Пикник"}])

(def category
  [{:id 1 :name "Продукты"}
   {:id 2 :name "Хозяйственные товары"}
   {:id 100 :name "Другое"}])

(def buyitems
  [{:id 1 :list 2 :name "Творог" :category 1 :qnt "1"}
   {:id 2 :list 2 :name "Яблоки" :category 1 :qnt "3" :comment "Красные"}
   {:id 3 :list 2 :name "Майонез" :category 1 :qnt "500"}
   {:id 4 :list 2 :name "Шланг" :category 2 :qnt "1" :comment "для душа"}])

(def buyitems-completed
  [{:id 5 :list 2 :name "Огурцы" :category 1 :qnt "4"}
   {:id 6 :list 2 :name "Колбаса" :category 1 :qnt "300" :comment "Вареная"}])
