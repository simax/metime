(ns metime.employees.subs
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [reagent.ratom :refer [make-reaction]]
            [re-frame.core :refer [register-sub]]
            [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame-tracer.core :refer [tracer]]))

;(trace-forms {:tracer (tracer :color "brown")}

(register-sub
  :departments
  (fn [db _]
    (make-reaction (fn sub-departments [] (:departments @db)))))

(register-sub
  :department-id
  (fn [db _]
    (make-reaction (fn sub-department-id [] (:department-id @db)))))

(register-sub
  :employee-dob-show-error
  (fn [db _]
    (make-reaction (fn sub-employee-dob-show-error [] (not (empty? (seq (get-in @db [:employee :validation-errors :dob]))))))))

(register-sub
  :employee-startdate-show-error
  (fn [db _]
    (make-reaction (fn sub-employee-startdate-show-error [] (not (empty? (seq (get-in @db [:employee :validation-errors :startdate]))))))))

(register-sub
  :employee-enddate-show-error
  (fn [db _]
    (make-reaction (fn sub-employee-enddate-show-error [] (not (empty? (seq (get-in @db [:employee :validation-errors :enddate]))))))))

(register-sub
  :employee-dob-error-message
  (fn [db _]
    (make-reaction (fn sub-employee-dob-error-message [] (if-let [errors (seq (get-in @db [:employee :validation-errors :dob]))]
                                                           (first errors)
                                                           "")))))
(register-sub
  :employee-startdate-error-message
  (fn [db _]
    (make-reaction (fn sub-employee-startdate-error-message [] (if-let [errors (seq (get-in @db [:employee :validation-errors :startdate]))]
                                                                 (first errors)
                                                                 "")))))

(register-sub
  :employee-enddate-error-message
  (fn [db _]
    (make-reaction (fn sub-employee-enddate-error-message [] (if-let [errors (seq (get-in @db [:employee :validation-errors :enddate]))]
                                                               (first errors)
                                                               "")))))

(register-sub
  :department-id
  (fn [db _]
    (make-reaction (fn sub-department-id [] (:department-id @db)))))

(register-sub
  :manager-id
  (fn [db _]
    (make-reaction (fn sub-manager-id [] (:manager-id @db)))))

(register-sub
  :department-draw-open-class
  (fn [db [_ department-id]]
    (make-reaction (fn sub-department-draw-open-class [] (if (= (:department-draw-open-id @db) department-id)
                                                           "panel-body panel-collapse collapse in"
                                                           "panel-body panel-collapse collapse")))))

(register-sub
  :authentication-failed
  (fn [db _]
    (make-reaction (fn sub-authentication-failed [] (:authentication-failed-msg @db)))))

(register-sub
  :employee-errors
  (fn [db _]
    (make-reaction (fn sub-employee-errors [] (get-in @db [:employee :validation-errors])))))


;)