(ns metime.data.employees
  (:require [cheshire.core :as json]
            [korma.db :refer :all]
            [korma.core :refer :all]
            [digest :as hashgen]
            [metime.data.database :refer :all]))

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

(defn insert-employee [data]
  (let [sanitized-data data
        result (insert employees (values (prepare-for-insert data)))]
    ;; Needed to use this syntax here rather than :keyword lookup
    ;; Because sqlite returns a key of last_insert_rowid().
    ;; The parens at the end of the keyword cause problems trying to use the keyword as a function.
    (first (vals result))))


(defn get-all []
  "Get all employees"
   (select employees))

(defn get-employee-by-id [id]
  "Get an indivdual employee by their id"
  (first
   (select employees
           (where {:manager.id id})
           (fields
            [:employees.email               :manager-email]
            [:employees.lastname            :manager-lastname]
            [:employees.firstname           :manager-firstname]
            [:employees.dob                 :manager-dob]
            [:employees.this_year_opening   :this_year_opening]
            [:employees.this_year_remaining :this_year_remaining]
            [:employees.next_year_opening   :next_year_opening]
            [:employees.next_year_remaining :next_year_remaining]
            [:manager.lastname              :lastname]
            [:manager.firstname             :firstname]
            [:manager.email                 :email]
            [:manager.startdate             :startdate]
            [:manager.enddate               :enddate]

            )
           (order :id)
           (join [employees :manager]
                 (= :employees.id :manager.managerid)))))

(defn delete-employee [id]
  "Delete the employee with the given id"
    (delete employees (where {:id id})))