(ns metime.leave-types.subs
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [reagent.ratom :refer [make-reaction]]
            [re-frame.core :refer [register-sub]]
            [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame-tracer.core :refer [tracer]]))

(register-sub
  :leave-types
  (fn [db _]
    (make-reaction (fn sub-leave-types [] (:leave-types @db)))))

(register-sub
  :edit-mode
  (fn [db [_ leave-type-id]]
    (make-reaction
      (fn sub-leave-type []
        (cond
          (and (= (get-in @db [:leave-type-drawer-open-id]) nil) (= (get-in @db [:leave-type :leave-type-id]) 0) (= 0 leave-type-id)) :add
          (and (= (get-in @db [:leave-type-drawer-open-id]) nil) (> (get-in @db [:leave-type :leave-type-id]) 0) (= (get-in @db [:leave-type :leave-type-id]) leave-type-id)) :edit
          :else :display)))))


(register-sub
  :leave-type-id
  (fn [db _]
    (make-reaction (fn sub-leave-type-id [] (:leave-type-id @db)))))


(register-sub
  :leave-type-drawer-open-id
  (fn [db [_]]
    (make-reaction (fn sub-leave-type-drawer-open-class [] (:leave-type-drawer-open-id @db)))))

