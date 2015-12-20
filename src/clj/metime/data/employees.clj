(ns metime.data.employees
  (:require [buddy.hashers :as hashers]
            [buddy.sign.jws :as jws]
            [clj-time.core :as time]
            [yesql.core :refer [defqueries defquery]]
            [metime.data.database :as db]))


(defqueries "metime/data/sql/metime.sql" {:connection db/db-spec})

(defn prepare-for-insert [data]
  "Prepare the incoming data ready for inserting into the DB."
  "Generate a salted password and remove extraneous keys from the map"
  (let [hashed-password (hashers/encrypt (:password data) {:alg :pbkdf2+sha256})]
        (-> data
            (dissoc :password-confirm)
            (assoc :password hashed-password))))

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
  "Probably only useful while testing/debugging. Unaware of a use case
  for this function in production"

  (db-delete-all-employees! {} {:connection db/db-spec}))

;; Move following funcs to a security namespace?
(defn auth-user [credentials]
  (let [user (get-employee-by-email (:email credentials))
        unauthed [false {:message "Invalid email or password"}]]
    (if user
      (if (hashers/check (:password credentials) (:password user))
        [true {:user (dissoc user :password)}]
        unauthed)
      unauthed)))

(defn create-auth-token [auth-conf credentials]
  "If we wanted to use public/private key we could pass auth-conf and use it
   See buddy documentation for that."
  (let [[ok? res] (auth-user credentials)
        exp (-> (time/plus (time/now) (time/seconds 15) ))
        claims {:user res :exp exp}]
    (if ok?
      ;; Should probably use a UUID here instead of "secret" (like timetastic?)
      [true {:token (jws/sign claims "secret")}]
      [false res])))

;; Example of token usage
(def token (let [[ok? token] (create-auth-token {} {:email "simonlomax@ekmsystems.co.uk" :password "p@ssw0rd"})]
             (when ok? (:token token))))



