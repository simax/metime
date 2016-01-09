(ns metime.navigation.subs
  (:require-macros [cljs.core.async.macros :refer [go alt!]]
                   [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [register-sub]]))

(register-sub
  :departments-and-employees
  (fn [db _]
    (reaction (:departments-and-employees @db))))

(register-sub
  :employee
  (fn [db _]
    (reaction (:employee @db))))

(register-sub
  :current-view
  (fn [db _]
    (reaction (:view @db))))

(register-sub
  :current-nav-bar
  (fn [db _]
    (reaction (:nav-bar @db))))

(register-sub
  :current-route
  (fn [db _]
    (reaction (:route-params @db))))

(register-sub
  :nav-bars
  (fn [db _]
    (reaction (:nav-bars @db))))

(register-sub
  :initialised?
  (fn [db _]
    (reaction (not-empty @db))))

(register-sub
  :logged-in
  (fn [db _]
    (reaction (:authentication-token @db))))

