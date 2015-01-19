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
  (first (select departments
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
          (join manager (= :employees.id :managerid)))))

(defn get-department-by-name [name]
  "Get the department with the given name"
  (first (select departments
                 (fields :department
                         :id)
                 (where {:department name}))))

(defn insert-department [data]
  "Insert a new department"
  (let [result (insert departments (values (walk/keywordize-keys data)))]
    ;; Needed to use this syntax here rather than :keyword lookup
    ;; Because sqlite returns a key of last_insert_rowid().
    ;; The parens at the end of the keyword cause problems for clojure.
    (first (vals result))))

(defn update-department [data]
  (update departments
          (set-fields data)
          (where {:id (:id data)})))

(defn delete-department [id]
  "Delete the department with the given id - providing it doesn't conatin any employees"
  (if (empty? (:employees (get-department-by-id id)))
    (delete departments (where {:id id}))))

