(ns metime.calendar.views
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [metime.formatting :as fmt]
            [metime.common.views :as common-components]
            [cljs.core.async :refer [put! take! <! >! chan timeout]]
            [metime.navigation.subs]
            [metime.calendar.subs]
            [metime.calendar.handlers]
            [re-com.core :refer [h-box v-box box gap scroller
                                 title single-dropdown label
                                 input-text input-textarea datepicker datepicker-dropdown button
                                 popover-anchor-wrapper popover-content-wrapper
                                 popover-tooltip md-icon-button md-circle-icon-button row-button]
             :refer-macros [handler-fn]]
            [re-com.datepicker :refer [iso8601->date datepicker-args-desc]]
            [cljs-time.core :refer [within? before? after? date-time now days minus day-of-week first-day-of-the-month]]
            [cljs-time.format :refer [formatter parse unparse]]
            [cljs-time.coerce]
            [goog.date]
            [re-frame.core :refer [dispatch subscribe]]
            [reagent.ratom :refer [make-reaction]]
            [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame-tracer.core :refer [tracer]]
            [cljs.pprint :refer [pprint]]
            [metime.utils :as utils]))


;(trace-forms
;  {:tracer (tracer :color "indigo")}


(defn booking-start-date [edit-mode booking]
  (let [start-date (:start-date booking)]
    (if (utils/is-mutating-mode? edit-mode)
      [input-text
       :width "400px"
       :model start-date
       :on-change #(dispatch [:input-change-booking-name %])
       :status (when (seq (get-in booking [:validation-errors :booking])) :error)
       :status-icon? (seq (get-in booking [:validation-errors :booking]))
       :status-tooltip (apply str (get-in booking [:validation-errors :booking]))
       :change-on-blur? false]
      [box
       :width "400px"
       :child [:h2 start-date]])))



(defn booking-component [bkng]
  (let [
        edit-mode (subscribe [:booking-edit-mode (:id bkng)])
        ;booking (if (utils/is-mutating-mode? @edit-mode) (deref (subscribe [:booking])) bkng)
        ]
    [box
     ;:class (if (utils/is-mutating-mode? @edit-mode) "mutating" "panel panel-default")
     ;:style (if (utils/is-mutating-mode? @edit-mode) {:background-color "Gainsboro" :border-width "1px" :border-style "solid" :border-color "white" :margin-bottom "20px"} {})
     :child
     [h-box
      :class "panel-body row"
      :height "65px"
      :justify :between
      :children
      [
       [h-box
        :gap "20px"
        :align :center
        :children
        [
         ;[manager-component @edit-mode booking]
         [booking-start-date @edit-mode bkng]
         ]]

       ;[booking-buttons-component @edit-mode booking]
       ]]]))

(defn booking-list-item [booking]
  [v-box
   :children
   [
    [booking-component booking]]])


(defn bookings-list [bookings]
  (let [my-booking (subscribe [:my-bookings])]
    [scroller
     :v-scroll :auto
     :height "780px"
     :width "1000px"
     :child
     [v-box
      :width "950px"
      :children
      [
       (when (and (seq @my-booking) (= (:booking-id @my-booking) 0))
         [booking-component @my-booking])
       (for [booking bookings]
         ^{:key (:id booking)}
         [booking-list-item booking])]]]))


(defn bookings-list-layout [bookings]
  [h-box
   :children
   [(bookings-list bookings)
    [box
     :align :end
     :child
     [md-circle-icon-button
      :md-icon-name "zmdi-plus"
      :size :larger
      :emphasise? true
      :style {:background-color "red" :border-color "red"}
      :on-click #(dispatch [:ui-new-booking-drawer-status-toggle])
      :tooltip "Add a new booking"
      :tooltip-position :right-center]]
    ]])

  ; )



