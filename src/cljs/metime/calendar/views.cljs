(ns metime.calendar.views
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require
    [metime.common.views :refer [loader-component date-component show-error valid-input-style invalid-input-style]]
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

(defn booking-buttons-component [edit-mode {:keys [booking-id]}]
  (if (utils/is-mutating-mode? edit-mode)
    [h-box
     :align :center
     :gap "10px"
     :width "250px"
     :justify :end
     :children
     [
      [md-circle-icon-button
       :emphasise? true
       :size :smaller
       :md-icon-name "zmdi-floppy"
       :tooltip "Save booking"
       :on-click #(dispatch [:booking-save])]
      [md-circle-icon-button
       :emphasise? true
       :size :smaller
       :md-icon-name "zmdi-close-circle-o"
       :tooltip "Cancel"
       :on-click #(dispatch [:close-all-booking-drawers])]]]

    [h-box
     :align :center
     :gap "10px"
     :width "300px"
     :justify :end
     :children
     [
      [md-icon-button
       :md-icon-name "zmdi-edit"                            ;
       :style {:color "#b2c831"}
       :on-click #(dispatch [:edit-booking booking-id])]

      [md-icon-button
       :md-icon-name "zmdi-delete"                          ;
       :style {:color "Red"}
       :on-click #(dispatch [:booking-delete booking-id])]
      [box :child [:div]]]]))


(defn booking-type [edit-mode booking]
  (let [booking-type (:leave-type booking)]
    (if (utils/is-mutating-mode? edit-mode)
      [input-text
       :width "200px"
       :model booking-type
       :on-change #(dispatch [:input-change-booking-type %])
       :status (when (seq (get-in booking [:validation-errors :booking])) :error)
       :status-icon? (seq (get-in booking [:validation-errors :booking]))
       :status-tooltip (apply str (get-in booking [:validation-errors :booking]))
       :change-on-blur? false]
      [box
       :width "100px"
       :child [:span booking-type]])))


;(defn booking-start-date [booking]
;  (let [error-message (subscribe [:booking-start-date-error-message])
;        showing-error-icon? (subscribe [:booking-start-date-show-error])]
;    (date-component booking :start-date "Start date" "start-date" error-message showing-error-icon? false)))

(defn booking-start-date [edit-mode booking]
  (let [start-date (:start-date booking)
        error-message (subscribe [:booking-start-date-error-message])
        showing-error-icon? (subscribe [:booking-start-date-show-error])]
    (if (utils/is-mutating-mode? edit-mode)
      (date-component
        :db-model booking
        :field :start-date
        :field-label "Start date"
        :place-holder "Start date"
        :popup-position :below-center
        :error-message error-message
        :showing-error-icon? showing-error-icon?)
      [box
       :width "400px"
       :child [:span start-date]])))

(defn booking-component [bkng]
  (let [edit-mode (subscribe [:booking-edit-mode (:booking-id bkng)])
        booking (if (utils/is-mutating-mode? @edit-mode) (deref (subscribe [:booking])) bkng)]
    [box
     :class (if (utils/is-mutating-mode? @edit-mode) "mutating" "panel panel-default")
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
         [booking-start-date @edit-mode booking]
         [booking-type @edit-mode booking]
         ]]

       [booking-buttons-component @edit-mode booking]
       ]]]))

(defn booking-component-display [bkng]
  [box
   :class "panel panel-default"
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
       [booking-start-date :display bkng]
       [booking-type :display bkng]
       ]]

     [booking-buttons-component :display bkng]
     ]]])

(defn booking-list-item [booking]
  [v-box
   :children
   [
    [booking-component-display booking]]])


(defn bookings-list [bookings]
  (let [booking (subscribe [:booking])]
    [scroller
     :v-scroll :auto
     :height "780px"
     :width "1000px"
     :child
     [v-box
      :width "950px"
      :children
      [
       (when (and (seq @booking) (= (:booking-id @booking) 0))
         [booking-component @booking])
       (for [booking bookings]
         ^{:key (:booking-id booking)}
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



