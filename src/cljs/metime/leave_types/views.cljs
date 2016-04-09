(ns metime.leave-types.views
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [metime.formatting :as fmt]
            [metime.common.views :as common-components]
            [cljs.core.async :refer [put! take! <! >! chan timeout]]
            [metime.navigation.subs]
            [metime.employees.subs]
            [metime.employees.handlers]
            [metime.routes :as routes]
            [metime.utils :as utils]
            [re-com.core :refer [h-box v-box box gap scroller
                                 title single-dropdown label
                                 input-text input-textarea datepicker datepicker-dropdown button
                                 popover-anchor-wrapper popover-content-wrapper
                                 popover-tooltip md-icon-button md-circle-icon-button row-button]
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

(defn is-mutating-mode? [status]
  "Return true if adding or editing"
  (case status
    :add true
    :edit true
    false))


;(defn manager-name-component [edit-mode {:keys [manager-firstname manager-lastname] :as department}]
;  (let [sorted-employees (subscribe [:sorted-departments-with-employees])
;        id-fn #(:id %)
;        group-fn #(str (:department %))
;        label-fn #(str (:firstname %) " " (:lastname %))
;        selected-employee-id (reagent/atom (:manager-id department))
;        mgr-error-message (reagent/atom "A manager is required")
;        mgr-showing-error-icon? (reagent/atom (seq (get-in department [:validation-errors :manager-id])))
;        mgr-showing-tooltip? (reagent/atom false)]
;
;    (if (is-mutating-mode? edit-mode)
;      [h-box
;       :gap "5px"
;       :width "315px"
;       :children
;       [
;        [single-dropdown
;         :style (if @mgr-showing-error-icon? invalid-input-style valid-input-style)
;         :width "230px"
;         :placeholder "Manager"
;         :choices @sorted-employees
;         :id-fn id-fn
;         :label-fn label-fn
;         :group-fn group-fn
;         :model selected-employee-id
;         :filter-box? true
;         :on-change #(dispatch [:set-department-manager-id %])]
;        (show-error mgr-error-message mgr-showing-error-icon? mgr-showing-tooltip?)]]
;      [box :child [:h5 (str manager-firstname " " manager-lastname)]])))
;
;(defn manager-component [edit-mode department]
;  (let [manager-email (subscribe [:department-manager-email])]
;    (if (is-mutating-mode? edit-mode)
;      [h-box
;       :gap "10px"
;       :width "300px"
;       :align :center
;       :children
;       [
;        [utils/gravatar {:gravatar-email @manager-email :gravatar-size 50}]
;        [manager-name-component edit-mode department]]]
;      [h-box
;       :gap "10px"
;       :width "300px"
;       :align :center
;       :children
;       [
;        [utils/gravatar {:gravatar-email (:manager-email department) :gravatar-size 50}]
;        [manager-name-component edit-mode department]]])))
;

(defn leave-type-name-component [edit-mode leave-type]
  (let [leave-type-name (clojure.string/replace (:leave-type leave-type) #"[\s]" "-")]
    ;(if (is-mutating-mode? edit-mode)
    ;  [input-text
    ;   :width "400px"
    ;   :model leave-type-name
    ;   :placeholder "Leave type"
    ;   :on-change #(dispatch [:input-change-department-name %])
    ;   :status (when (seq (get-in leave-type [:validation-errors :department])) :error)
    ;   :status-icon? (seq (get-in leave-type [:validation-errors :department]))
    ;   :status-tooltip (apply str (get-in leave-type [:validation-errors :department]))
    ;   :change-on-blur? false]
    ;  [box
    ;   :width "400px"
    ;   :child [:h2 leave-type-name]])
    [box
     :width "400px"
     :child [:h2 leave-type-name]]))
;
;(defn department-buttons-component [edit-mode {:keys [department-id employee-count]}]
;  (if (is-mutating-mode? edit-mode)
;    [h-box
;     :align :center
;     :gap "10px"
;     :width "100px"
;     :justify :end
;     :children
;     [
;      [md-circle-icon-button
;       :emphasise? true
;       :size :smaller
;       :md-icon-name "zmdi-floppy"
;       :tooltip "Save department"
;       :on-click #(dispatch [:department-save])]
;      [md-circle-icon-button
;       :emphasise? true
;       :size :smaller
;       :md-icon-name "zmdi-close-circle-o"
;       :tooltip "Cancel"
;       :on-click #(dispatch [:close-new-department-drawer])]]]
;
;    [h-box
;     :align :center
;     :gap "10px"
;     :width "100px"
;     :justify :end
;     :children
;     [
;      [md-icon-button
;       :md-icon-name "zmdi-edit"                            ;
;       :style {:color "Green"}
;       :on-click #(dispatch [:edit-department department-id])]
;      [md-icon-button
;       :md-icon-name "zmdi-swap-vertical"                   ;
;       :on-click #(dispatch [:ui-department-drawer-status-toggle department-id])]
;
;      (if (zero? employee-count)
;        [md-icon-button
;         :md-icon-name "zmdi-delete"                        ;
;         :style {:color "Red"}
;         :on-click #(dispatch [:department-delete department-id])]
;        [box :width "25px" :child [:div]])]]))
;

(defn leave-type-component [ltype]
  (let [edit-mode (subscribe [:edit-mode nil])              ; (:department-id ltype)
        leave-type (if (is-mutating-mode? @edit-mode) (deref (subscribe [:department])) ltype)]
    [box
     :class (if (is-mutating-mode? @edit-mode) "" "panel panel-default")
     :style (if (is-mutating-mode? @edit-mode) {:border-style "solid" :border-color "white" :margin-bottom "20px"} {})
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
         ;[manager-component @edit-mode department]
         [leave-type-name-component @edit-mode leave-type]]]

       ;[department-buttons-component @edit-mode department]
       ]]]))



(defn leave-type-list-item [leave-type]
  (let [x 1]
    ;[department-drawer-open-id (subscribe [:department-draw-open-id])]
    [v-box
     :children
     [
      [leave-type-component leave-type]
      ;[department-employees-panel @department-drawer-open-id (:department-id department) (:manager-id department)]
      ]]))



(defn leave-types-list [leave-types]
  (let [x 1]
    ;[department (subscribe [:department])]
    [scroller
     :v-scroll :auto
     :height "780px"
     :width "1000px"
     :child
     [v-box
      ;:gap "20px"
      :width "950px"
      :children
      [
       ;(when (and (seq @department) (= (:department-id @department) 0))
       ;  [department-component @department])
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
      :on-click #(dispatch [:ui-new-department-drawer-status-toggle])
      :tooltip "Add a new leave-type"
      :tooltip-position :right-center]]
    ]])


