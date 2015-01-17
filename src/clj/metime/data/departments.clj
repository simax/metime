(ns metime.data.departments
  (:require [cheshire.core :as json]
            [korma.db :refer :all]
            [korma.core :refer :all]
            [metime.data.database :refer :all]
            [clojure.walk :as walk]))

(defn get-all-with-employees []
  "Get all departments including their employees"
  (select departments
          (fields :department
                  :id
                  :managerid
                  [:employees.email :manager-email]
                  [:employees.lastname :manager-lastname]
                  [:employees.firstname :manager-firstname])
          (order :department)
          (with employees
                (order :lastname))
          (join manager (= :employees.id :managerid))))

(defn get-department-by-id [id]
  "Get the department with the given id"
  (select departments
          (fields :department
                  :id
                  :managerid
                  [:employees.email :manager-email]
                  [:employees.lastname :manager-lastname]
                  [:employees.firstname :manager-firstname])
          (order :department)
          (with employees
                (order :lastname))
          (where {:id id})
          (join manager (= :employees.id :managerid))))


(defn insert-department [data]
  "Insert a new department"
  (let [result (insert departments (values (walk/keywordize-keys data)))
        new-id (first (vals result))]
      new-id))