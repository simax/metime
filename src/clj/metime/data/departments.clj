(ns metime.data.departments
  (:require [cheshire.core :as json]
            [yesql.core :refer [defqueries defquery]]
            ;;[metime.data.database :as db]
            ))


(def db-spec
  {:classname "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname "data/metime.sqlite"})

(defqueries "metime/data/sql/metime.sql" {:connection db-spec})

(defn get-all-departments []
  (db-get-all-departments {} {:connection db-spec}))

(defn get-all-departments-with-employees []
  (db-get-all-departments {} {:connection db-spec}))

(defn get-department-by-id [id]
  (first (db-get-department-by-id {:id id} {:connection db-spec})))

(defn get-department-by-id-with-employees [id]
  (first (db-get-department-by-id-with-employees {:id id} {:connection db-spec})))

(defn get-department-by-name [department]
  (db-get-department-by-name {:department department} {:connection db-spec}))

(defn insert-department! [data]
  ;; Needed to use this syntax here rather than :keyword lookup
  ;; Because sqlite returns a key of last_insert_rowid().
  ;; The parens at the end of the keyword cause problems trying to use the keyword as a function.
  (let [result (db-insert-department<! data {:connection db-spec})]
    (first (vals result))))

(defn update-department! [data]
  (db-update-department! data {:connection db-spec}))

(defn delete-department! [id]
  "Delete the department with the given id - providing it doesn't conatin any employees"
  (if (empty? (:employees (db-get-department-by-id {:connection db-spec} {:id id})))
    (db-delete-department! {:id id} {:connection db-spec} )))

(defn delete-all-departments! []
  (db-delete-all-departments! {} {:connection db-spec}))

