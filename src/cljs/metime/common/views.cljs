(ns metime.common.views
  (:require [re-com.core :refer [throbber h-box v-box box gap
                                 title single-dropdown label
                                 input-text input-textarea datepicker datepicker-dropdown button
                                 popover-anchor-wrapper popover-content-wrapper
                                 popover-tooltip md-icon-button md-circle-icon-button row-button]]
            [re-com.core :refer [throbber h-box v-box box gap scroller
                                 title single-dropdown label
                                 input-text input-textarea datepicker datepicker-dropdown button
                                 popover-anchor-wrapper popover-content-wrapper
                                 popover-tooltip md-icon-button md-circle-icon-button row-button]
             :refer-macros [handler-fn]]
            [re-frame.core :refer [dispatch subscribe]]
            [reagent.core :as reagent]
            [metime.formatting :as fmt]))

(def invalid-input-style {:border-radius "4px 4px 4px 4px"
                          :border-color  "red"
                          :border-style  "solid"
                          :border-width  "1px"})

(def valid-input-style {:border-radius "4px 4px 4px 4px"
                        :border-color  "white"
                        :border-style  "solid"
                        :border-width  "1px"})


(defn loader-component [height color]
  [box
   :height (or height "650px")
   :align :center
   :justify :center
   :child [:div (throbber :size :large :color (or color "lime"))]])

(defn show-error [error-message showing-error-icon? showing-tooltip?]
  (when @showing-error-icon? [popover-tooltip
                              :label @error-message
                              :position :right-center
                              :showing? showing-tooltip?
                              :status :error
                              :width "150px"
                              :anchor [:i
                                       {:class         "zmdi zmdi-alert-circle"
                                        :on-mouse-over (handler-fn (reset! showing-tooltip? true))
                                        :on-mouse-out  (handler-fn (reset! showing-tooltip? false))
                                        :style         {:color     "red"
                                                        :font-size "130%"}}]]))

(defn date-input-with-popup [date-field date-value showing? title selectable-fn]
  "Displays a popup for the given date field"
  [popover-anchor-wrapper
   :showing? showing?
   :position :above-center
   :anchor [button
            :style {:border-radius "0 4px 4px 0"}
            :attr {:tab-index -1}
            :label [:i {:class "zmdi zmdi-apps"}]
            :on-click #(swap! showing? not)]
   :popover [popover-content-wrapper
             :showing? showing?
             :title title
             :position :below-center
             :backdrop-opacity 0.5
             :width "250px"
             :no-clip? true
             :arrow-length 20
             :arrow-width 20
             :close-button? true
             :on-cancel #(reset! showing? false)
             :body [datepicker
                    :selectable-fn selectable-fn
                    :model (reagent/atom (fmt/str->date date-value))
                    :show-today? true
                    :on-change #(dispatch [:datepicker-change-dates date-field %])]]])


(defn date-component
  "A generic date input box with popup and validation features"
  ([db-model field field-label place-holder error-message showing-error-icon?]
   (date-component db-model field field-label place-holder error-message showing-error-icon? (partial constantly true)))

  ([db-model field field-label place-holder error-message showing-error-icon? selectable-fn]
   (let [showing-date-popup? (reagent/atom false)
         showing-tooltip? (reagent/atom false)]
     [h-box
      :justify :start
      :children
      [
       [box :width "150px" :child [label :label field-label]]
       [h-box
        :style (if @showing-error-icon? invalid-input-style valid-input-style)
        :children
        [
         [box :child [input-text
                      :style {:border-radius "4px 0 0 4px"}
                      :placeholder place-holder
                      :model (fmt/date->str (field db-model))
                      :width "120px"
                      :change-on-blur? true
                      :on-change #(dispatch [:input-change-dates field %])]]
         (date-input-with-popup field (field db-model) showing-date-popup? place-holder selectable-fn)]]

       (show-error error-message showing-error-icon? showing-tooltip?)]])))