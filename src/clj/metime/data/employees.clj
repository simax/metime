(ns metime.data.employees
  (:require [cheshire.core :as json]
            [korma.db :refer :all]
            [korma.core :refer :all]
            [metime.data.database :refer :all]))


(defn get-all []
   (select employees (with departments)))
