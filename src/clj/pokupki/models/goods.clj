(ns pokupki.models.goods
  (:use [korma db core]))

(def env (into {} (System/getenv)))
(def dbhost (get env "OPENSHIFT_MYSQL_DB_HOST"))
(def dbport (get env "OPENSHIFT_MYSQL_DB_PORT"))

(def default-conn {:classname "com.mysql.jdbc.Driver"
                   :subprotocol "mysql"
                   ;:user "pok"
                   ;:password "goods"
                   ;:subname "//127.0.0.1:3306/pokupki?useUnicode=true&characterEncoding=utf8"
                   :user "***"
                   :password "***"
                   :subname (str "//" dbhost ":" dbport "/pok?useUnicode=true&characterEncoding=utf8")
                   :delimiters "`"})

;;; test data
(comment

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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn get-list-items [list-id]
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

(defn get-category-list []
  category
  )

(defn -get-next-good-id []
  (let [m (reduce max (map :id (flatten (map :goods @gooditems))))]
    (+ m 1)))

(defn create-good [data]
  (let [newid (-get-next-good-id)]
    (println newid data)))
) ; end of comment

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defdb korma-db default-conn)

(defentity category)

(defentity good
  (has-one category))

(defn get-category-list []
  (select category (order :id)))

(defn get-goods-list [listid]
  (vec ; get rid of lazyness
    (sort-by #(get-in % [:category :id])
    (map (fn [[cid goods]] {:category (first (select category (where (= :id cid)))) :goods goods})
      (group-by :f_category (select good (where (or (= :deleted nil) (= :deleted false))) (order :tm)))))))

(defn delete-good-item [id]
  (update good
    (set-fields {:tm (new java.util.Date) :deleted true})
    (where (= :id id))))

(def pint (fn [n] (Integer/parseInt n)))

(defn create-good [{:keys [name, category, amount, comment]}]
  (insert good
    (values {:name name :f_category (pint category)
             :amount amount :comment comment})))