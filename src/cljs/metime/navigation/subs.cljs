(ns metime.navigation.subs
  (:require-macros [cljs.core.async.macros :refer [go alt!]]
                   [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [register-sub]]))

(register-sub
  :departments
  (fn [db _]
    (reaction (:deps @db))))

(register-sub
  :employee
  (fn [db _]
    (reaction (:employee @db))))

(register-sub
  :view-component
  (fn [db _]
    (reaction (:view @db))))

(register-sub
  :nav-bar
  (fn [db _]
    (reaction (:nav-bar @db))))

(register-sub
  :initialised?
  (fn [db _]
    (reaction (not (empty? @db)))))

