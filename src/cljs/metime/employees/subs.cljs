(ns metime.employees.subs
  (:require-macros [cljs.core.async.macros :refer [go alt!]]
                   [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [register-sub]]))

(defn departments-query
  [db _]
  (reaction (get-in @db [:deps])))

(register-sub
  :deps
  departments-query)

(register-sub
  :department-id
  (fn [db _]
    (reaction (:department-id @db))))

(register-sub
  :manager-id
  (fn [db _]
    (reaction (:manager-id @db))))


