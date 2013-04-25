(defproject pokupki "0.1.0-SNAPSHOT"
  :description "Shopping list"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [lib-noir "0.4.9"]
                 [enfocus "1.0.1"]
                 [jayq "2.3.0"]
                 [mysql/mysql-connector-java "5.1.24"]
                 [korma "0.3.0-RC4"]]

  :plugins [[lein-ring "0.8.3"]
            [lein-cljsbuild "0.3.0"]]

  :ring {:handler pokupki.handler/app}

  :aot []
  :source-paths ["src/clj" "src/cljs"]

  :cljsbuild {
    :builds [{
    :source-paths ["src/clj" "src/cljs"]
    :compiler {
      :pretty-print true,
      :output-to "resources/public/js/pokupki.js",
      :warnings true,
      :externs ["externs/jquery-1.9.js"],
      ;; :optimizations :advanced,
      :optimizations :whitespace,
      :print-input-delimiter false}}]}

  :war {:name "pokupki.war"})