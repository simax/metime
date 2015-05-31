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
  :db-changed?
  (fn [db _]
    (reaction @db)))

(register-sub
  :initialised?
  (fn [db _]
    (reaction (not (empty? @db)))))

