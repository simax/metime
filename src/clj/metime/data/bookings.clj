(ns metime.data.bookings
  (:require [metime.data.database :as db]
            [hugsql.core :as hugsql]))


;(defqueries "metime/data/sql/metime.sql" {:connection db/db-spec})

(hugsql/def-db-fns "metime/data/sql/metime.sql")
(hugsql/def-sqlvec-fns "metime/data/sql/metime.sql")

;(defn insert-holiday-request! [data]
;  ;; Needed to use this syntax here rather than :keyword lookup
;  ;; Because sqlite returns a key of last_insert_rowid().
;  ;; The parens at the end of the keyword cause problems trying to use the keyword as a function.
;  (let [result (db-insert-holiday-request<! data {:connection db/db-spec})]
;    (first (vals result))))

;(defn get-holidays []
;  (db-get-holidays {} {:connection db/db-spec}))


