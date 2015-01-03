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
            [:manager.lastname :lastname]
            [:manager.firstname :firstname]
            [:manager.email :email]
            [:manager.startdate :startdate]
            [:manager.enddate :enddate]
            [:manager.active :active]
            )
           (order :id)
           (join [employees :manager]
                 (= :employees.id :manager.managerid)))))


;; (with manager)
;; (join manager (= :employees.id :managerid))

