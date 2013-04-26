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
   {:id 3 :name "Детские товары"}
   {:id 4 :name "Лекарства"}
   {:id 100 :name "Другое"}])

(def gooditems (atom
                 [{:category {:id 1 :name "Продукты"}
                   :goods [{:id 1 :list 2 :name "Творог" :category 1 :qnt "1"}
                           {:id 2 :list 2 :name "Яблоки" :category 1 :qnt "3" :comment "Красные"}
                           {:id 3 :list 2 :name "Майонез" :category 1 :qnt "500"}
                           ]}
                  {:category {:id 2 :name "Хозяйственные товары"}
                   :goods [{:id 4 :list 2 :name "Шланг" :category 2 :qnt "1" :comment "для душа"}
                           {:id 5 :list 2 :name "Вантуз" :category 2 :qnt "2" :comment "да побольше!"}]}
                  ] ))


(def gooditems-deleted (atom
  [{:id 5 :list 2 :name "Огурцы" :category 1 :qnt "4"}
   {:id 6 :list 2 :name "Колбаса" :category 1 :qnt "300" :comment "Вареная"}]))

;;
(defn get-list-items [list-id]
  ; TODO: multiple shop lists
  (vec @gooditems))

(defn -remove-good-from-group [{:keys [category goods]} id]
  {:category category :goods (remove #(= id (:id %)) goods)})

(defn -remove-empty-group [items]
  (remove (fn [{:keys [category goods]}] (empty? goods)) items))

(defn -remove-good [gooditems id]
  (let [gooditems  (map #(-remove-good-from-group % id) gooditems)]
    (-remove-empty-group gooditems)))

(def pint (fn [n] (Integer/parseInt n)))

(defn delete-good-item [id]
  (let [id (pint id)
        deleted (first (filter #(= id (:id %)) (flatten (map :goods @gooditems))))]
    (println "deleted: " deleted)
    (swap! gooditems-deleted conj deleted)
    (swap! gooditems -remove-good id)
    (println "gooditems: " @gooditems)
    (println "gooditems-deleted: " @gooditems-deleted)
    ))
