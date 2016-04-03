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
  :sorted-departments-with-employees
  (fn [db _]
    ;; This setup is more efficient because deps-with-employees reaction will always be computed whenever app-db changes.
    ;; However, the sort-by function will only ever be executed if deps-with-employees has changed IN VALUE
    (let [deps-with-employees (reagent.ratom/reaction (get-in @db [:departments-with-employees]))]
      (make-reaction (fn sub-sorted-employees [] (sort-by (juxt :department :lastname) @deps-with-employees))))))

(register-sub
  :department-manager-email
  (fn [db _]
    (make-reaction (fn department-manager-email [] (get-in @db [:department :manager-email])))))

(register-sub
  :edit-mode
  (fn [db [_ department-id]]
    (make-reaction
      (fn sub-department []
        (cond
          (and (= (get-in @db [:department :department-id]) 0) (= 0 department-id)) :add
          (and (> (get-in @db [:department :department-id]) 0) (= (get-in @db [:department :department-id]) department-id)) :edit
          :else :display)))))

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
  :department-draw-open-id
  (fn [db [_]]
    (make-reaction (fn sub-department-draw-open-class [] (:department-draw-open-id @db)))))



(register-sub
  :authentication-failed
  (fn [db _]
    (make-reaction (fn sub-authentication-failed [] (:authentication-failed-msg @db)))))

(register-sub
  :employee-errors
  (fn [db _]
    (make-reaction (fn sub-employee-errors [] (get-in @db [:employee :validation-errors])))))


;)