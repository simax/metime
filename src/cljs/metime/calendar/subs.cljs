(ns metime.calendar.subs
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [reagent.ratom :refer [make-reaction]]
            [re-frame.core :refer [register-sub]]
            [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame-tracer.core :refer [tracer]]))


;(trace-forms {:tracer (tracer :color "brown")}

(register-sub
  :leave-types
  (fn [db _]
    (make-reaction (fn sub-leave-types [] (:leave-types @db)))))


(register-sub
  :booking-end-date-show-error
  (fn [db _]
    (make-reaction (fn sub-booking-end-date-show-error []
                     (not (empty? (seq (get-in @db [:booking :validation-errors :end-date]))))))))

(register-sub
  :booking-end-date-error-message
  (fn [db _]
    (make-reaction (fn sub-booking-end-date-error-message []
                     (if-let [errors (seq (get-in @db [:booking :validation-errors :end-date]))]
                       (first errors)
                       "")))))

(register-sub
  :booking-start-date-show-error
  (fn [db _]
    (make-reaction (fn sub-booking-start-date-show-error []
                     (not (empty? (seq (get-in @db [:booking :validation-errors :start-date]))))))))

(register-sub
  :booking-start-date-error-message
  (fn [db _]
    (make-reaction (fn sub-booking-start-date-error-message []
                     (if-let [errors (seq (get-in @db [:booking :validation-errors :start-date]))]
                       (first errors)
                       "")))))

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
          (println (str "is-editing?: " is-editing?))
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