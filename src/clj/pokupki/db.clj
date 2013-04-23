(ns pokupki.db)

(def default-conn {:classname "com.mysql.jdbc.Driver"
                   :subprotocol "mysql"
                   :user "pok"
                   :password "pokkey"
                   :subname "//127.0.0.1:3306/hiredoer?useUnicode=true&characterEncoding=utf8"
                   :delimiters "`"})
