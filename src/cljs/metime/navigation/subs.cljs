(ns metime.navigation.subs
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [reagent.ratom :refer [make-reaction]]
            [re-frame.core :refer [register-sub]]
            [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame-tracer.core :refer [tracer]]))

;(trace-forms
; {:tracer (tracer :color "brown")}

(register-sub
  :current-employee
  (fn [db _]
    (make-reaction (fn sub-current-employee [] (:employee @db)))))

(register-sub
  :department-employees
  (fn [db _]
    (make-reaction (fn sub-departments-employees [] (:department-employees @db)))))

(register-sub
  :employee
  (fn [db _]
    (make-reaction (fn sub-employee [] (:employee @db)))))

(register-sub
  :department
  (fn [db _]
    (make-reaction (fn sub-department [] (:department @db)))))

(register-sub
  :current-view
  (fn [db _]
    (make-reaction (fn sub-current-view [] (:view @db)))))

(register-sub
  :current-nav-bar
  (fn [db _]
    (make-reaction (fn sub-current-nav-bar [] (:nav-bar @db)))))

(register-sub
  :current-route
  (fn [db _]
    (make-reaction (fn sub-current-route [] (:route-params @db)))))

(register-sub
  :nav-bars
  (fn [db _]
    (make-reaction (fn sub-nav-bars [] (:nav-bars @db)))))

(register-sub
  :initialised?
  (fn [db _]
    (make-reaction (fn sub-initialised? [] (not-empty @db)))))

(register-sub
  :logged-in
  (fn [db _]
    (make-reaction (fn sub-logged-in [] (:authentication-token @db)))))
;)