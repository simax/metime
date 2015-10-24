(ns metime.employees.subs
  (:refer-clojure :exclude [run!])
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
  :error-employee-dob
  (fn [db _]
    (reaction (seq (get-in @db [:employee :validation-errors :dob])))))

(register-sub
  :department-id
  (fn [db _]
    (reaction (:department-id @db))))

(register-sub
  :manager-id
  (fn [db _]
    (reaction (:manager-id @db))))

(register-sub
  :department-draw-open-class
  (fn [db [_ department-id]]
    (reaction (if (= (:department-draw-open-id @db) department-id)
                "panel-body panel-collapse collapse in"
                "panel-body panel-collapse collapse"))))