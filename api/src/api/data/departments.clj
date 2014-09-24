(ns api.data.departments
  (:require [cheshire.core :as json]
            [korma.db :refer :all]
            [korma.core :refer :all]
            [api.data.database :refer :all]
            [clojure.walk :as walk]))


(defn get-all []

      ;;(json/encode

;   [{:id 1, :name "EKM"}
;    {:id 2, :name "Development"}
;    {:id 3, :name "Design"}
;    {:id 4, :name "Customer Support"}]

   (let [deps (into [] (partition 2 (select departments #_(with employees))))
         ]
     (println deps)
     ;;(walk/keywordize-keys deps)
     deps))
