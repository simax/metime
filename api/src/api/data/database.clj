(ns api.data.database
  (:require [korma.db :refer :all]
            [korma.core :refer :all]))


(defdb db (sqlite3 {:classname "org.sqlite.JDBC"
                    :subprotocol "sqlite"
                    :subname "/users/simonlomax/documents/development/clojure projects/metime/api/src/api/data/metime.sqlite"}))

(declare departments)

(defentity employees
  ;(entity-fields :id :firstname :lastname :email :startdate :enddate :active)
  (belongs-to departments))

(defentity departments
  (has-many employees))

