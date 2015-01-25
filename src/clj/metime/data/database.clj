(ns metime.data.database
  (:require [korma.db :refer :all]
            [korma.core :refer :all]))


(def sqll (sqlite3
           {:classname "org.sqlite.JDBC"
            :subprotocol "sqlite"
            :subname "data/metime.sqlite"}))


(defdb db sqll)

(declare departments manager)

(defentity employees
  (table :employees)
  (has-one departments))


(defentity manager
  (table :employees)
  (pk :managerid)
  (entity-fields
   [:firstname :manager-firstname]
   [:lastname :manager-lastname]
   [:email :manager-email]))

(defentity departments
  (has-one manager)
  (has-many employees))
