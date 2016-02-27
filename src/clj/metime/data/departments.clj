(ns metime.data.departments
  (:require [metime.data.database :as db]
            [hugsql.core :as hugsql]))

(hugsql/def-db-fns "metime/data/sql/metime.sql")
(hugsql/def-sqlvec-fns "metime/data/sql/metime.sql")

;(defqueries "metime/data/sql/metime.sql" {:connection db/db-spec})

;(defn get-all-departments []
;  (db-get-all-departments {} {:connection db/db-spec}))

;(defn get-all-departments-with-employees []
;  (db-get-all-departments-with-employees {} {:connection db/db-spec}))

;(defn get-department-by-id [id]
;  (first (db-get-department-by-id {:id id} {:connection db/db-spec})))

;(defn get-department-by-id-with-employees [id]
;  (db-get-department-by-id-with-employees {:id id} {:connection db/db-spec}))


;(defn get-department-by-name [department]
;  (db-get-department-by-name {:department department} {:connection db/db-spec}))

;(defn insert-department! [data]
;  ;; Needed to use this syntax here rather than :keyword lookup
;  ;; Because sqlite returns a key of last_insert_rowid().
;  ;; The parens at the end of the keyword cause problems trying to use the keyword as a function.
;  (let [result (db-insert-department<! data {:connection db/db-spec})]
;    (first (vals result))))

;(defn update-department! [data]
;  (db-update-department! data {:connection db/db-spec}))

;TODO: Move all this logic into SQL statement then we can call this like any other HugSql function
(defn delete-department! [id]
  "Delete the department with the given id - providing it doesn't contain any employees"
  (let [department-with-employees (get-department-by-id-with-employees db/db-spec {:id id})]
    (if (empty? department-with-employees)
      true
      (if (every? #(nil? (:lastname %)) department-with-employees)
        (= 1 (delete-department db/db-spec {:id id}))
        false))))

;(defn delete-all-departments! []
;  "Probably only useful while testing/debugging. Don't know a use case
;  for this function in production"
;  (db-delete-all-departments! {} {:connection db/db-spec}))

