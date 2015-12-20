(ns metime.data.holidays
  (:require [yesql.core :refer [defqueries defquery]]
            [metime.data.database :as db]))


(defqueries "metime/data/sql/metime.sql" {:connection db/db-spec})

(defn insert-holiday-request! [data]
  ;; Needed to use this syntax here rather than :keyword lookup
  ;; Because sqlite returns a key of last_insert_rowid().
  ;; The parens at the end of the keyword cause problems trying to use the keyword as a function.
  (let [result (db-insert-holiday-request<! data {:connection db/db-spec})]
    (first (vals result))))

(defn get-holidays []
  (db-get-holidays {} {:connection db/db-spec}))


