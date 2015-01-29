(ns metime.data.departments
  (:require [cheshire.core :as json]
            [yesql.core :refer [defqueries]]
            [metime.data.database :as db]))

(defqueries "metime/data/sql/metime.sql" {:connection db/db-spec})

(defn get-all-departments []
  (db-get-all-departments))

(defn get-all-departments-with-employees []
  (db-get-all-departments))

(defn get-department-by-id [id]
  (first (db-get-department-by-id {:id id})))

(defn get-department-by-id-with-employees [id]
  (first (db-get-department-by-id-with-employees {:id id})))

(defn get-department-by-name [department]
  (db-get-department-by-name {:department department}))

(defn insert-department! [data]
  ;; Needed to use this syntax here rather than :keyword lookup
  ;; Because sqlite returns a key of last_insert_rowid().
  ;; The parens at the end of the keyword cause problems trying to use the keyword as a function.
  (let [result (db-insert-department<! data)]
    (first (vals result))))

(defn update-department! [data]
  (db-update-department! data))

(defn delete-department! [id]
  "Delete the department with the given id - providing it doesn't conatin any employees"
  (if (empty? (:employees (db-get-department-by-id {:id id})))
    (db-delete-department! {:id id})))

(defn delete-all-departments! []
  (db-delete-all-departments!))

