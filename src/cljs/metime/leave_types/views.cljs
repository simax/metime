(ns metime.leave-types.views
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [metime.formatting :as fmt]
            [metime.common.views :as common-components]
            [cljs.core.async :refer [put! take! <! >! chan timeout]]
            [metime.navigation.subs]
            [metime.leave-types.subs]
            [metime.leave-types.handlers]
            [metime.routes :as routes]
            [metime.utils :as utils]
            [re-com.core :refer [h-box v-box box gap scroller
                                 title single-dropdown label
                                 input-text input-textarea datepicker datepicker-dropdown button
                                 popover-anchor-wrapper popover-content-wrapper
                                 popover-tooltip md-icon-button md-circle-icon-button row-button
                                 checkbox]
             :refer-macros [handler-fn]]
            [re-com.datepicker :refer [iso8601->date datepicker-args-desc]]
            [cljs-time.core :refer [within? before? after? date-time now days minus day-of-week]]
            [cljs-time.format :refer [formatter parse unparse]]
            [cljs-time.coerce]
            [goog.date]
            [re-frame.core :refer [dispatch subscribe]]
            [reagent.core :as reagent]
            [reagent.ratom :refer [make-reaction]]
            [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame-tracer.core :refer [tracer]]
            [cljs.pprint :refer [pprint]]))

;(trace-forms
;  {:tracer (tracer :color "indigo")}

(defn leave-type-name-component [edit-mode leave-type]
  (let [leave-type-name (:leave-type leave-type)]
    (if (utils/is-mutating-mode? edit-mode)
      [input-text
       :width "255px"
       :model leave-type-name
       :placeholder "Leave type"
       :on-change #(dispatch [:input-change-leave-type-name %])
       :status (when (seq (get-in leave-type [:validation-errors :leave-type])) :error)
       :status-icon? (seq (get-in leave-type [:validation-errors :leave-type]))
       :status-tooltip (apply str (get-in leave-type [:validation-errors :leave-type]))
       :change-on-blur? false]
      [box
       :width "300px"
       :child [:h2 leave-type-name]])))

(defn leave-type-reduce-leave-component [edit-mode leave-type]
  (let [reduce-leave? (reagent/atom (:reduce-leave leave-type))]
    [checkbox
     :disabled? (not (utils/is-mutating-mode? edit-mode))
     :model reduce-leave?
     :label "Reduce leave"
     :on-change #(dispatch [:checkbox-change-leave-type-reduce-leave %])]))

(defn leave-type-buttons-component [edit-mode {:keys [leave-type-id]}]
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
       :tooltip "Save leave-type"
       :on-click #(dispatch [:leave-type-save])]
      [md-circle-icon-button
       :emphasise? true
       :size :smaller
       :md-icon-name "zmdi-close-circle-o"
       :tooltip "Cancel"
       :on-click #(dispatch [:close-leave-type-drawer])]]]

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
       :on-click #(dispatch [:edit-leave-type leave-type-id])]

      [md-icon-button
       :md-icon-name "zmdi-delete"                          ;
       :style {:color "Red"}
       :on-click #(dispatch [:leave-type-delete leave-type-id])]
      [box :child [:div]]]]))

(defn leave-type-component [ltype]
  (let [edit-mode (subscribe [:leave-type-edit-mode (:leave-type-id ltype)])
        leave-type (if (utils/is-mutating-mode? @edit-mode) (deref (subscribe [:leave-type])) ltype)]
    [box
     :class (if (utils/is-mutating-mode? @edit-mode) "mutating" "panel panel-default")
     :child
     [h-box
      :class "panel-body row"
      :height "60px"
      :align :center
      :justify :between
      :children
      [
       [leave-type-name-component @edit-mode leave-type]
       [leave-type-reduce-leave-component @edit-mode leave-type]
       [leave-type-buttons-component @edit-mode leave-type]
       ]]]))


(defn leave-type-list-item [leave-type]
  [v-box
   :children
   [
    [leave-type-component leave-type]
    ]])


(defn leave-types-list [leave-types]
  (let [leave-type (subscribe [:leave-type])]
    [scroller
     :v-scroll :auto
     :height "780px"
     :width "800px"
     :child
     [v-box
      :width "800px"
      :children
      [
       (when (and (seq @leave-type) (= (:leave-type-id @leave-type) 0))
         [leave-type-component @leave-type])
       (for [leave-type leave-types]
         ^{:key (:leave-type leave-type)}
         [leave-type-list-item leave-type])]]]))


(defn leave-types-list-layout [leave-types]
  [h-box
   :children
   [
    (leave-types-list leave-types)
    [box
     :align :end
     :child
     [md-circle-icon-button
      :md-icon-name "zmdi-plus"
      :size :larger
      :emphasise? true
      :style {:background-color "red" :border-color "red"}
      :on-click #(dispatch [:ui-new-leave-type-drawer-status-toggle])
      :tooltip "Add a new leave type"
      :tooltip-position :right-center]]
    ]])

; )
