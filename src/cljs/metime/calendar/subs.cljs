(ns metime.calendar.subs
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [reagent.ratom :refer [make-reaction]]
            [re-frame.core :refer [register-sub]]
            [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame-tracer.core :refer [tracer]]))


;(trace-forms {:tracer (tracer :color "brown")}

  (register-sub
    :booking-edit-mode
    (fn [db [_ booking-id]]
      (make-reaction
        (fn sub-booking []
          ;(println (str "booking-id: " booking-id))
          ;(cond
          ;  (and (= (get-in @db [:booking-drawer-open-id]) nil) (= (get-in @db [:booking :booking-id]) 0) (= 0 booking-id)) :add
          ;  (and (= (get-in @db [:booking-drawer-open-id]) nil) (> (get-in @db [:booking :booking-id]) 0) (= (get-in @db [:booking :booking-id]) booking-id)) :edit
          ;  :else :display)
          :display))))


  (register-sub
    :my-bookings
    (fn [db _]
      (make-reaction (fn sub-my-bookings [] (:employee-bookings @db)))))

;)