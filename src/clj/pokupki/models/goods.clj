(ns pokupki.models.goods
  (:use [korma db core]))

(def default-conn {:classname "com.mysql.jdbc.Driver"
                   :subprotocol "mysql"
                   :user "pok"
                   :password "pokkey"
                   :subname "//127.0.0.1:3306/hiredoer?useUnicode=true&characterEncoding=utf8"
                   :delimiters "`"})

(def goodlists
  [{:id 2 :name "Текущий"}
   {:id 3 :name "Пикник"}])

(def category
  [{:id 1 :name "Продукты"}
   {:id 2 :name "Хозяйственные товары"}
   {:id 100 :name "Другое"}])

(def gooditems
  [{:id 1 :list 2 :name "Творог" :category 1 :qnt "1"}
   {:id 2 :list 2 :name "Яблоки" :category 1 :qnt "3" :comment "Красные"}
   {:id 3 :list 2 :name "Майонез" :category 1 :qnt "500"}
   {:id 4 :list 2 :name "Шланг" :category 2 :qnt "1" :comment "для душа"}])

(def gooditems-completed
  [{:id 5 :list 2 :name "Огурцы" :category 1 :qnt "4"}
   {:id 6 :list 2 :name "Колбаса" :category 1 :qnt "300" :comment "Вареная"}])

;;
(defn get-list-items [list-id]
  [{:category {:id 1 :name "Продукты"}
   :goods [{:id 1 :list 2 :name "Творог" :category 1 :qnt "1"}
           {:id 2 :list 2 :name "Яблоки" :category 1 :qnt "3" :comment "Красные"}
           {:id 3 :list 2 :name "Майонез" :category 1 :qnt "500"}
          ]}] )

