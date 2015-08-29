(ns metime.data.employees
  (:require [cheshire.core :as json]
            [digest :as hashgen]
            [yesql.core :refer [defqueries defquery]]
            [metime.data.database :as db]))


(defqueries "metime/data/sql/metime.sql" {:connection db/db-spec})

(defn uuid [] (str (java.util.UUID/randomUUID)))

(defn prepare-for-insert [data]
  "Prepare the incoming data ready for inserting into the DB."
  "Generate a salted password and remove extraneous keys from the map"
  (let [salt (uuid)
        hashed-password (hashgen/md5 (str salt (:password data)))]
        (-> data
            (dissoc :password-confirm)
            (assoc
              :salt salt
              :password hashed-password))))


(defn get-all-employees []
  (db-get-all-employees {} {:connection db/db-spec}))

(defn get-employee-by-id [id]
  (first (db-get-employee-by-id {:id id} {:connection db/db-spec})))

(defn get-employee-by-email [email]
  (first (db-get-employee-by-email {:email email} {:connection db/db-spec})))


(defn insert-employee! [data]
    ;; Needed to use this syntax here rather than :keyword lookup
    ;; Because sqlite returns a key of last_insert_rowid().
    ;; The parens at the end of the keyword cause problems trying to use the keyword as a function.
  (let [result (db-insert-employee<! (prepare-for-insert data) {:connection db/db-spec})]
    (first (vals result))))

(defn update-employee! [data]
  (db-update-employee! data {:connection db/db-spec}))

(defn delete-employee! [id]
  "Delete the employee with the given id"
  (= 1 (db-delete-employee! {:id id} {:connection db/db-spec})))

(defn delete-all-employees! []
  "Probably only usefull while testing/debugging. Don't know a use case
  for this function in production"

  (db-delete-all-employees! {} {:connection db/db-spec}))


