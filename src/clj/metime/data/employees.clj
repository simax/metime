(ns metime.data.employees
  (:require [cheshire.core :as json]
            [korma.db :refer :all]
            [korma.core :refer :all]
            [metime.data.database :refer :all]))


(defn get-all []
  "Get all employees"
   (select employees))

(defn get-employee-by-id [id]
  "Get an indivdual employee by their id"
  (first
   (select employees
           (where {:manager.id id})
           (fields
            [:employees.email :manager-email]
            [:employees.lastname :manager-lastname]
            [:employees.firstname :manager-firstname]
            [:employees.dob :manager-dob]
            [:employees.this_year_opening   :manager-this_year_opening]
            [:employees.this_year_remaining :manager-this_year_remaining]
            [:employees.next_year_opening   :manager-next_year_opening]
            [:employees.next_year_remaining :manager-next_year_remaining]

            [:manager.lastname :lastname]
            [:manager.firstname :firstname]
            [:manager.email :email]
            [:manager.startdate :startdate]
            [:manager.enddate :enddate]

            )
           (order :id)
           (join [employees :manager]
                 (= :employees.id :manager.managerid)))))


;; (with manager)
;; (join manager (= :employees.id :managerid))

