(ns metime.calendar.subs
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [reagent.ratom :refer [make-reaction]]
            [re-frame.core :refer [register-sub]]
            [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame-tracer.core :refer [tracer]]))


;(trace-forms {:tracer (tracer :color "brown")}


(register-sub
  :booking
  (fn [db _]
    (make-reaction (fn sub-booking [] (:booking @db)))))

(register-sub
  :my-bookings
  (fn [db _]
    (make-reaction (fn sub-my-bookings [] (:employee-bookings @db)))))

(register-sub
  :booking-edit-mode
  (fn [db [_ booking-id]]
    (make-reaction
      (fn sub-booking []
        (let [is-adding? (and (= (get-in @db [:booking-drawer-open-id]) nil) (= (get-in @db [:booking :booking-id]) 0) (= 0 booking-id))
              is-editing? (and (= (get-in @db [:booking-drawer-open-id]) nil) (> (get-in @db [:booking :booking-id]) 0) (= (get-in @db [:booking :booking-id]) booking-id))]
          (println (str "is-adding?: " is-adding?))
          ;(println (str "is-adding?: " is-editing?))
          (cond
            is-adding? :add
            is-editing? :edit
            :else :display))
        ))))

(register-sub
  :booking-id
  (fn [db _]
    (make-reaction (fn sub-booking-id [] (:booking-id @db)))))

(register-sub
  :booking-drawer-open-id
  (fn [db [_]]
    (make-reaction (fn sub-booking-drawer-open-class [] (:booking-drawer-open-id @db)))))


;)