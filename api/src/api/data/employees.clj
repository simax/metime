(ns api.data.employees
  (:require [cheshire.core :as json]
            [korma.db :refer :all]
            [korma.core :refer :all]
            [api.data.database :refer :all]))


(defn get-all []
  (json/encode
;   [{:id 1, :name "EKM"}
;    {:id 2, :name "Development"}
;    {:id 3, :name "Design"}
;    {:id 4, :name "Customer Support"}]

   (select employees (with departments))))
