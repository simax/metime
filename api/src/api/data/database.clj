(ns api.data.database
  (:require [korma.db :refer :all]
            [korma.core :refer :all]))


(defdb db (postgres {:db "mytime"
                     :user "simonlomax"
                     :password "nomisxamol"}))
(declare departments)

(defentity employees
  ;(entity-fields :id :firstname :lastname :email :startdate :enddate :active)
  (belongs-to departments))

(defentity departments
  (has-many employees))

