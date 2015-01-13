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

(defn insert-department [new-department]
  (spit "sql-department.txt" (sql-only (insert departments (values {:department new-department})))))