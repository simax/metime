(ns metime.data.departments
  (:require [cheshire.core :as json]
            [korma.db :refer :all]
            [korma.core :refer :all]
            [metime.data.database :refer :all]
            [clojure.walk :as walk]))

(defn get-all []
   (select departments
           (fields :department :id :managerid)
           (with employees
                 (fields
                  :managerid
                  :enddate
                  :startdate
                  :email
                  :lastname
                  :firstname
                  :id))
            (with manager
                  )))
