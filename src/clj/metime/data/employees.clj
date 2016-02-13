(ns metime.data.employees
  (:require [buddy.hashers :as hashers]
            [yesql.core :refer [defqueries defquery]]
            [metime.data.database :as db]
            [metime.formatting :as fmt]))

(defqueries "metime/data/sql/metime.sql" {:connection db/db-spec})

(defn format-dates [employee]
  (let [emp (assoc employee :dob (fmt/format-date-yyyy-mm-dd (:dob employee))
                            :startdate (fmt/format-date-yyyy-mm-dd (:startdate employee))
                            :enddate (fmt/format-date-yyyy-mm-dd (:enddate employee)))]
    emp))

(defn prepare-for-insert [data]
  "Prepare the incoming data ready for inserting into the DB."
  "Generate a salted password and remove extraneous keys from the map"
  (let [hashed-password (hashers/encrypt (:password data) {:alg :pbkdf2+sha256})]
    (-> data
        (format-dates)
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
  (-> data
      (format-dates)
      (db-update-employee! {:connection db/db-spec})))

(defn delete-employee! [id]
  "Delete the employee with the given id"
  (= 1 (db-delete-employee! {:id id} {:connection db/db-spec})))

(defn delete-all-employees! []
  "Probably only useful while testing/debugging. Can't envisage a use case
  for this function in production"
  (db-delete-all-employees! {} {:connection db/db-spec}))


(defn get-departments-with-employees []
  "Inner join of departments and employees"
  (db-departments-with-employees))

(defn get-departments-without-employees []
  "Departments with no associated employees"
  (db-departments-without-employees))

(defn get-all-employees-by-department []
  "Get all employees by department. Effectively a left join of departments and their emmployees.
   i.e. Fetch all departments and their employees whether the department has employees or not"
  (db-all-departments-and-employees {} {:connection db/db-spec}))



