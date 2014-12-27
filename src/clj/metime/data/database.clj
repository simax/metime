(ns metime.data.database
  (:require [korma.db :refer :all]
            [korma.core :refer :all]))


(defdb db (sqlite3 {:classname "org.sqlite.JDBC"
                    :subprotocol "sqlite"
                    :subname "C:/Development/clojure projects/metime/src/clj/metime/data/metime.sqlite"}))
                    ;;:subname "/Users/simonlomax/Documents/Development/Clojure Projects/metime/src/clj/metime/data/metime.sqlite"}))


(declare departments manager)

(defentity employees
  (belongs-to departments))

(defentity manager
  (table :employees)
         (fields [:firstname :manager-firstname]
                 [:lastname :manager-lastname]
                 [:email :manager-email]))

(defentity departments
  (has-one manager)
  (has-many employees))
