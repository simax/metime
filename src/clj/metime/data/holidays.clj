(ns metime.data.holidays
  (:require [digest :as hashgen]
            [yesql.core :refer [defqueries defquery]]
            [metime.data.database :as db]))


(defqueries "metime/data/sql/metime.sql" {:connection db/db-spec})

(defn insert-holiday-request! [data]
  ;; Needed to use this syntax here rather than :keyword lookup
  ;; Because sqlite returns a key of last_insert_rowid().
  ;; The parens at the end of the keyword cause problems trying to use the keyword as a function.
  (let [result (db-insert-holiday-request<! data {:connection db/db-spec})]
    (first (vals result))))

(defn get-all-confirmed-holidays-for-employee [id]
  (db-get-all-confirmed-holidays-for-employee {:id id} {:connection db/db-spec}))