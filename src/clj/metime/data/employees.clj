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
   (select employees (where {:id id})))
