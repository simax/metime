(ns metime.calendar.views
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require
    [metime.common.views :refer [loader-component date-component show-error valid-input-style invalid-input-style]]
    [cljs.core.async :refer [put! take! <! >! chan timeout]]
    [metime.navigation.subs]
    [metime.employees.subs]
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
    [metime.utils :as utils]
    [reagent.core :as reagent]))


;(trace-forms
;  {:tracer (tracer :color "indigo")}

(defn booking-close-button-component [edit-mode {:keys [booking-id]}]
  (if (utils/is-mutating-mode? edit-mode)
    [h-box
     :align :center
     ;:gap "10px"
     ;:width "250px"
     :justify :end
     :children
     [
      [md-circle-icon-button
       :style {:margin "10px"}
       :emphasise? false
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

(defn booking-edit-delete-buttons-component [edit-mode {:keys [booking-id]}]
  [h-box
   :align :center
   :gap "10px"
   :width "300px"
   :justify :end
   :children
   [
    [md-icon-button
     :md-icon-name "zmdi-edit"                              ;
     :style {:color "#b2c831"}
     :on-click #(dispatch [:edit-booking booking-id])]

    [md-icon-button
     :md-icon-name "zmdi-delete"                            ;
     :style {:color "Red"}
     :on-click #(dispatch [:booking-delete booking-id])]
    [box :child [:div]]]])


(defn booking-save-button-component [edit-mode {:keys [booking-id]}]
  (if (utils/is-mutating-mode? edit-mode)
    [h-box
     :align :center
     ;:gap "10px"
     ;:width "250px"
     :justify :end
     :children
     [
      [md-circle-icon-button
       :style {:margin "10px"}
       :emphasise? true
       :size :smaller
       :md-icon-name "zmdi-floppy"
       :tooltip "Save booking"
       :on-click #(dispatch [:booking-save])]]]
    ))


(defn booking-start-types [edit-mode booking]
  (let [start-stypes (subscribe [:start-types])]
    (if (utils/is-mutating-mode? edit-mode)
      [v-box
       :justify :start
       :children
       [
        [box :width "150px" :child [label :class "control-label" :label "Start types"]]
        [single-dropdown
         :width "150px"
         :model (:start-type booking)
         :choices @start-stypes
         :on-change #(dispatch [:start-type-change %])]]]
      )))

(defn booking-end-types [edit-mode booking]
  (let [end-stypes (subscribe [:end-types])]
    (if (utils/is-mutating-mode? edit-mode)
      [v-box
       :justify :start
       :children
       [
        [box :width "150px" :child [label :class "control-label" :label "End types"]]
        [single-dropdown
         :width "150px"
         :model (:end-type booking)
         :choices @end-stypes
         :on-change #(dispatch [:start-type-change %])]]]
      )))

(defn booking-type-ddl [edit-mode booking]
  (let [leave-types (subscribe [:sorted-leave-types-by-deduction])]
    (if (utils/is-mutating-mode? edit-mode)
      [v-box
       :justify :start
       :children
       [
        [box :width "150px" :child [label :class "control-label" :label "Type"]]
        [single-dropdown
         :width "150px"
         :model (:leave-type-id booking)
         :placeholder "Leave type"
         :choices @leave-types
         :id-fn #(:leave-type-id %)
         :group-fn #(if (= (:reduce-leave %) 1) "Deductable" "Non-deductable")
         :label-fn #(:leave-type %)
         :on-change #(dispatch [:leave-type-change %])]]]
      )))

(defn booking-type [edit-mode booking]
  (let [booking-type (:leave-type booking)]
    (if (utils/is-mutating-mode? edit-mode)
      [input-text
       :width "200px"
       :model booking-type
       :on-change #(dispatch [:input-change-booking-type %])
       :status (when (seq (get-in booking [:validation-errors :leave-type])) :error)
       :status-icon? (seq (get-in booking [:validation-errors :leave-type]))
       :status-tooltip (apply str (get-in booking [:validation-errors :leave-type]))
       :change-on-blur? false]
      [box
       :width "100px"
       :child [:span booking-type]])))

(defn booking-end-date [edit-mode booking]
  (let [end-date (:end-date booking)
        error-message (subscribe [:booking-end-date-error-message])
        showing-error-icon? (subscribe [:booking-end-date-show-error])]
    (if (utils/is-mutating-mode? edit-mode)
      [h-box
       :gap "20px"
       :children
       [
        [v-box
         :children
         [
          [box :width "150px" :child [label :class "control-label" :label "To"]]
          (date-component
            :model-key :booking
            :model booking
            :field :end-date
            :place-holder "End date"
            :popup-position :below-center
            :error-message error-message
            :showing-error-icon? showing-error-icon?)]]
        [booking-end-types edit-mode booking]
        ]]
      [box
       :width "400px"
       :child [:span end-date]])))

(defn booking-start-date [edit-mode booking]
  (let [start-date (:start-date booking)
        error-message (subscribe [:booking-start-date-error-message])
        showing-error-icon? (subscribe [:booking-start-date-show-error])]
    (if (utils/is-mutating-mode? edit-mode)
      [h-box
       :gap "20px"
       :children
       [
        [v-box
         :children
         [
          [box :width "150px" :child [label :class "control-label" :label "From"]]
          (date-component
            :model-key :booking
            :model booking
            :field :start-date
            :place-holder "Start date"
            :popup-position :below-center
            :error-message error-message
            :showing-error-icon? showing-error-icon?)]]
        [booking-start-types edit-mode booking]]]
      [box
       :width "400px"
       :child [:span start-date]])))


(defn booking-employee-ddl [edit-mode booking]
  (let [sorted-employees (subscribe [:sorted-departments-with-employees])
        id-fn #(:id %)
        group-fn #(str (:department %))
        label-fn #(str (:firstname %) " " (:lastname %))
        selected-employee-id (reagent/atom (:employee-id booking))
        emp-error-message (reagent/atom "An employee is required")
        emp-showing-error-icon? (reagent/atom (seq (get-in booking [:validation-errors :employee-id])))
        emp-showing-tooltip? (reagent/atom false)]

    (if (utils/is-mutating-mode? edit-mode)
      [v-box
       :children
       [
        [box :width "150px" :child [label :class "control-label" :label "For"]]
        [h-box
         :gap "5px"
         :width "315px"
         :children
         [
          [single-dropdown
           :style (if @emp-showing-error-icon? invalid-input-style valid-input-style)
           :width "230px"
           :placeholder "Employee"
           :choices @sorted-employees
           :id-fn id-fn
           :label-fn label-fn
           :group-fn group-fn
           :model selected-employee-id
           :filter-box? true
           :on-change #(dispatch [:set-booking-employee-id %])]
          (show-error emp-error-message emp-showing-error-icon? emp-showing-tooltip?)]]]]
      )))


(defn booking-employee-and-type [edit-mode booking]
  [h-box
   :gap "20px"
   :align :center
   :children
   [
    [booking-type-ddl edit-mode booking]
    [booking-employee-ddl edit-mode booking]
    ]])

(defn booking-dates [edit-mode booking]
  [h-box
   :gap "20px"
   :align :center
   :children
   [
    [booking-start-date edit-mode booking]
    [booking-end-date edit-mode booking]]])

(defn booking-reason-component [edit-mode booking]
  (if (utils/is-mutating-mode? edit-mode)
    [box :size "auto" :child [input-text
                              :width "315px"
                              :model (:reason booking)
                              :placeholder "Reason (optional)"
                              :on-change #(dispatch [:input-change :firstname %])
                              :change-on-blur? false]]))


  (defn booking-component [bkng]
    (let [edit-mode (subscribe [:booking-edit-mode (:booking-id bkng)])
          booking (if (utils/is-mutating-mode? @edit-mode) (deref (subscribe [:booking])) bkng)]
      [v-box
       :class (if (utils/is-mutating-mode? @edit-mode) "mutating" "panel panel-default")
       ;:style (if (utils/is-mutating-mode? @edit-mode) {:background-color "Gainsboro" :border-width "1px" :border-style "solid" :border-color "white" :margin-bottom "20px"} {})
       :children
       [
        [booking-close-button-component @edit-mode booking]
        [booking-dates @edit-mode booking]
        [booking-employee-and-type @edit-mode booking]
        [booking-reason-component @edit-mode booking]
        [booking-save-button-component @edit-mode booking]
        ]]))

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
       [booking-close-button-component :display bkng]
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



