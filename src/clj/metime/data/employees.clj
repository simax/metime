(ns metime.data.employees
  (:require [cheshire.core :as json]
            [digest :as hashgen]
            [yesql.core :refer [defqueries defquery]]
            [metime.data.database :as db]))

(def db-spec
  {:classname "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname "data/metime.sqlite"})

(defqueries "metime/data/sql/metime.sql" {:connection db-spec})

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
  (db-get-all-employees {} {:connection db-spec}))

(defn get-employee-by-id [id]
  (first (db-get-employee-by-id {:id id} {:connection db-spec})))

(defn insert-employee! [data]
    ;; Needed to use this syntax here rather than :keyword lookup
    ;; Because sqlite returns a key of last_insert_rowid().
    ;; The parens at the end of the keyword cause problems trying to use the keyword as a function.
  (let [result (db-insert-employee<! (prepare-for-insert data) {:connection db-spec})]
    (first (vals result))))

(defn update-employee! [data]
  (db-update-employee! data {:connection db-spec}))

(defn delete-employee! [id]
  "Delete the employee with the given id"
  (db-delete-employee! {:id id} {:connection db-spec}))

(defn delete-all-employees! []
  (db-delete-all-employees! {} {:connection db-spec}))

