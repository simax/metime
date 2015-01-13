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

(defn insert-department [data]
  (let [department (get data "department")
        manager-id (get data "manager-id")]
    ;;(spit "sql-departments.txt" (str ">>>" department)))
  ;;(spit "sql-departments.txt" (sql-only (insert departments (values {:department department :manager-id manager-id}))))))
  (insert departments (values {:department department :managerid manager-id}))))