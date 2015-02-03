(ns metime.data.database)

;; Test DB
(def db-spec
  {:classname "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname "data/metime.sqlite"})

