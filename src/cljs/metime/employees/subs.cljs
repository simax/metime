(ns metime.employees.subs
  (:require-macros [cljs.core.async.macros :refer [go alt!]]
                   [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [register-sub]]))


(register-sub
  :departments
  (fn [db _]
    (reaction (:departments @db ))))

(register-sub
  :department-id
  (fn [db _]
    (reaction (:department-id @db))))

(register-sub
  :employee-dob-show-error
  (fn [db _]
    (reaction (not (empty? (seq (get-in @db [:employee :validation-errors :dob])))))))

(register-sub
  :employee-startdate-show-error
  (fn [db _]
    (reaction (not (empty? (seq (get-in @db [:employee :validation-errors :startdate])))))))

(register-sub
  :employee-enddate-show-error
  (fn [db _]
    (reaction (not (empty? (seq (get-in @db [:employee :validation-errors :enddate])))))))

(register-sub
  :employee-dob-error-message
  (fn [db _]
    (reaction (if-let [errors (seq (get-in @db [:employee :validation-errors :dob]))]
                (first errors)
                ""))))
(register-sub
  :employee-startdate-error-message
  (fn [db _]
    (reaction (if-let [errors (seq (get-in @db [:employee :validation-errors :startdate]))]
                (first errors)
                ""))))

(register-sub
  :employee-enddate-error-message
  (fn [db _]
    (reaction (if-let [errors (seq (get-in @db [:employee :validation-errors :enddate]))]
                (first errors)
                ""))))

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

(register-sub
  :authentication-failed
  (fn [db _]
    (reaction (:authentication-failed-msg @db))))