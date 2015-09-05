(ns metime.employees.views
  (:refer-clojure :exclude [run!])
  (:require-macros [cljs.core.async.macros :refer [go alt!]]
                   [reagent.ratom :refer [reaction]])
  (:require [cljs.core.async :refer [put! take! <! >! chan timeout]]
            [metime.employees.subs]
            [metime.employees.handlers]
            [metime.routes :as r]
            [metime.utils :as utils]
            [reagent.core :refer [atom]]
            [re-com.core :refer [h-box v-box box gap title single-dropdown label input-text datepicker datepicker-dropdown button]]
            [re-com.datepicker :refer [iso8601->date datepicker-args-desc]]
            [cljs-time.core :refer [date-time now days minus day-of-week]]
            [cljs-time.format :refer [formatter parse unparse]]
            [cljs-time.coerce :as tc]
            [goog.date]
            [devtools.core :as dt]
            [re-frame.core :refer [register-handler
                                   path
                                   debug
                                   after
                                   enrich
                                   register-sub
                                   dispatch
                                   dispatch-sync
                                   subscribe]]
            [reagent.core :as reagent])
  (:import goog.History
           goog.History.EventType))


(enable-console-print!)

(defn employee-name [firstname lastname]
  (let [disp-name (str firstname " " lastname)
        name-length (count disp-name)]
    (if (> name-length 20) (str (subs disp-name 0 17) "...") disp-name)))

(defn employee-list-item [{:keys [lastname firstname email id]}]
  [:div {:class "col-md-3 col-lg-3"}
   [:div {:class "dash-unit"}
    [:div {:class "thumbnail" :style {:margin-top "20px"}}
     [:a {:href (r/employee-route {:id id})}
      [:h1 (employee-name firstname lastname)]
      [:div {:style {:margin-top "20px"}} [utils/gravatar {:gravatar-email email}]]
      ]
     [:div {:class "info-user"}
      [:span {:aria-hidden "true" :class "li_user fs1"}]
      [:span {:aria-hidden "true" :class "li_calendar fs1"}]
      [:span {:aria-hidden "true" :class "li_mail fs1"}]
      [:span {:aria-hidden "true" :class "glyphicon glyphicon-trash fs1"}]
      ]

     ;; For now, just simulate the number of days remaining
     [:h2 {:class "text-center" :style {:color "red"}} (rand-int 25)]
     ]]])


(defn department-list-item [{:keys [departmentid department managerid manager-firstname manager-lastname manager-email employees]}]
  (let [department-list-item (filter #(not= (:id %) managerid) employees)
        rows-of-employees (partition 4 4 nil department-list-item)
        department-name (clojure.string/replace department #"[\s]" "-")
        draw-open-class (subscribe [:department-draw-open-class departmentid])]
    [:div#accordian.panel.panel-default.row
     [:div.panel-heading.clearfix.panel-heading
      [:div.col-md-2.col-xs-2
       [:div.col-md-4.col-xs-4 [utils/gravatar {:gravatar-email manager-email :gravatar-size 50}]]
       [:div.col-md-8.col-xs-8
        [:h5 (str manager-firstname " " manager-lastname)]
        ]
       ]
      [:div.col-md-10.col-xs-10
       [:div.col-md-11.col-xs-11
        [:h2 department]
        ]
       [:div.col-md-1.col-xs-1
        [:button {:class    "btn btn-default glyphicon glyphicon-sort"
                  :on-click #(dispatch [:ui-department-drawer-status-toggle departmentid])}]]]]
     [:div {:class @draw-open-class :id department-name :style {:height "auto"}}
      (if (not-empty rows-of-employees)
        [:div
         [:button {:class    "btn btn-primary glyphicon glyphicon-plus-sign"
                   :on-click #(dispatch [:employee-add departmentid managerid])} " Add employee"]
         [:ul {:style {:margin-top "20px"}}
          (for [employee-row rows-of-employees]
            (for [employee-item employee-row]
              ^{:key (:id employee-item)} [employee-list-item employee-item])
            )]])]]))

(defn department-list [departments]
  [:div.clearfix.accordian
   [:ul
    (for [dep departments]
      ^{:key (:department dep)} [:li [department-list-item dep]])
    ]])

(defn departments-container [deps]
  [department-list deps])

(defn employee-not-found []
  [box :child [:div.well [:h1 {:style {:color "red"}} "Sorry, we couldn't find that employee."]]])

(defn employee-gravatar [employee]
  [box
   :padding "20px 0 0 0"
   :child
   [h-box
    :gap "20px"
    :height "150px"
    :children
    [
     [box :child [utils/gravatar {:gravatar-email (:email employee) :gravatar-size 150}]]
     [v-box
      :justify :center
      :children
      [[box :child [:h1 (str (:firstname employee) " " (:lastname employee))]]
       [:h2 {:style {:color "grey"}} (:department employee)]
       ]]]]]
  )

(defn manager-gravatar [employee]
  [v-box
   :align :center
   :width "150px"
   :children
   [[box :child [:h6 "Manager"]]
    [box :child [utils/gravatar {:gravatar-email (:manager-email employee) :gravatar-size 75}]]
    [box :child [:h5 (str (:manager-firstname employee) " " (:manager-lastname employee))]]
    ]])

(defn employee-core-heading [employee]
  [h-box
   :padding "20px"
   :align :center
   :class "panel panel-default"
   :children [[box :child [employee-gravatar employee]]
              [gap :size "1"]
              [box :child [manager-gravatar employee]]
              ]]
  )


(defn department-list-choices [departments]
  (into []
        (for [m @departments]
          {:id (:departments_id m) :label (:department m)})))

(defn department-drop-down-list [employee departments]
  [h-box
   :justify :start
   :children
   [
    [box :width "150px" :child [label :class "control-label" :label "Department"]]
    [box :size "auto" :child [single-dropdown
                              :model (:departments_id employee)
                              :choices (department-list-choices departments)
                              :on-change #(dispatch-sync [:department-change %])
                              ]]]
   ])

(defn employee-first-name [employee]
  [h-box
   :justify :between
   :children
   [
    [box :width "150px" :child [label :label "First name"]]
    [box :size "auto" :child [input-text
                              :width "315px"
                              :model (:firstname employee)
                              :status (when (seq (get-in employee [:validation-errors :firstname])) :error)
                              :status-icon? (seq (get-in employee [:validation-errors :firstname]))
                              :status-tooltip (apply str (get-in employee [:validation-errors :firstname]))
                              :placeholder "Employee first name"
                              :on-change #(dispatch-sync [:input-change :firstname %])
                              :change-on-blur? false]]
    ]]
  )


(defn employee-last-name [employee]
  [h-box
   :justify :start
   :children
   [
    [box :width "150px" :child [label :label "Last name"]]
    [box :size "auto" :child [input-text
                              :width "315px"
                              :model (:lastname employee)
                              :status (when (seq (get-in employee [:validation-errors :lastname])) :error)
                              :status-icon? (seq (get-in employee [:validation-errors :lastname]))
                              :status-tooltip (apply str (get-in employee [:validation-errors :lastname]))
                              :placeholder "Employee last name"
                              :on-change #(dispatch-sync [:input-change :lastname %])
                              :change-on-blur? false]]
    ]]
  )

(defn employee-email [employee]
  [h-box
   :justify :between
   :children
   [
    [box :width "150px" :child [label :label "Email"]]
    [box :size "auto" :child [input-text
                              :width "315px"
                              :model (:email employee)
                              :status (when (seq (get-in employee [:validation-errors :email])) :error)
                              :status-icon? (seq (get-in employee [:validation-errors :email]))
                              :status-tooltip (apply str (get-in employee [:validation-errors :email]))
                              :placeholder "Employee email address"
                              :on-change #(dispatch-sync [:input-change :email %])
                              :change-on-blur? false]]
    ]]
  )

(defn prep-date [date-str]
  "Default blank dates to 19000101 and remove dashes (-) from date string like 01-01-1900"
  (if (empty? date-str) "19000101" (clojure.string/replace date-str "-" "")))

(defn employee-dob [employee]
  (let [dob (reagent/atom (iso8601->date (prep-date (:dob employee))))]
    [h-box
     :justify :start
     :children
     [
      [box :width "150px" :child [label :label "Date of birth"]]
      [box :child [datepicker-dropdown
                   :model dob
                   :show-today? true
                   ;:selectable-fn selectable-pred
                   :format "dd-MM-yyyy"
                   :on-change #(dispatch [:input-change-dates :dob %])
                   ]]
      ]]))

(defn employee-start-date [employee]
  (let [start-date (reagent/atom (iso8601->date (prep-date (:startdate employee))))]
    [h-box
     :justify :start
     :children
     [
      [box :width "150px" :child [label :label "Start date"]]
      [box :child [datepicker-dropdown
                   :model start-date
                   :show-today? true
                   :format "dd-MM-yyyy"
                   :on-change #(dispatch [:input-change-dates :startdate %])
                   ]]
      ]])
  )

(defn employee-end-date [employee]
  (let [end-date (reagent/atom (iso8601->date (prep-date (:enddate employee))))]
    [h-box
     :justify :start
     :children
     [
      [box :width "150px" :child [label :label "End date"]]
      [box :child [datepicker-dropdown
                   :model end-date
                   :show-today? true
                   :format "dd-MM-yyyy"
                   :on-change #(dispatch [:input-change-dates :enddate %])
                   ]]
      ]])
  )

(defn employee-this-year-opening [employee]
  [h-box
   :justify :between
   :children
   [
    [box :child [label :label "This year opening"]]
    [box :child [input-text
                 :width "100px"
                 :model (str (:this_year_opening employee))
                 :status (when (seq (get-in employee [:validation-errors :this_year_opening])) :error)
                 :status-icon? (seq (get-in employee [:validation-errors :this_year_opening]))
                 :status-tooltip (apply str (get-in employee [:validation-errors :this_year_opening]))
                 :on-change #(dispatch-sync [:input-change-balances :this_year_opening %])
                 :validation-regex #"^(-{0,1})(\d{0,2})$"
                 :change-on-blur? true]]
    ]]
  )

(defn employee-this-year-remaining [employee]
  [h-box
   :justify :between
   :children
   [
    [box :child [label :label "This year remaining"]]
    [box :child [input-text
                 :width "100px"
                 :model (str (:this_year_remaining employee))
                 :status (when (seq (get-in employee [:validation-errors :this_year_remaining])) :error)
                 :status-icon? (seq (get-in employee [:validation-errors :this_year_remaining]))
                 :status-tooltip (apply str (get-in employee [:validation-errors :this_year_remaining]))
                 :on-change #(dispatch-sync [:input-change-balances :this_year_remaining %])
                 :validation-regex #"^(-{0,1})(\d{0,2})$"
                 :change-on-blur? false]]
    ]]
  )

(defn employee-next-year-opening [employee]
  [h-box
   :justify :between
   :children
   [
    [box :child [label :label "Next year opening"]]
    [box :child [input-text
                 :width "100px"
                 :model (str (:next_year_opening employee))
                 :status (when (seq (get-in employee [:validation-errors :next_year_opening])) :error)
                 :status-icon? (seq (get-in employee [:validation-errors :next_year_opening]))
                 :status-tooltip (apply str (get-in employee [:validation-errors :next_year_opening]))
                 :on-change #(dispatch-sync [:input-change-balances :next_year_opening %])
                 :validation-regex #"^(-{0,1})(\d{0,2})$"
                 :change-on-blur? false]]
    ]]
  )

(defn employee-next-year-remaining [employee]
  [h-box
   :justify :between
   :children
   [
    [box :child [label :label "Next year remaining"]]
    [box :child [input-text
                 :width "100px"
                 :model (str (:next_year_remaining employee))
                 :status (when (seq (get-in employee [:validation-errors :next_year_remaining])) :error)
                 :status-icon? (seq (get-in employee [:validation-errors :next_year_remaining]))
                 :status-tooltip (apply str (get-in employee [:validation-errors :next_year_remaining]))
                 :on-change #(dispatch-sync [:input-change-balances :next_year_remaining %])
                 :validation-regex #"^(-{0,1})(\d{0,2})$"
                 :change-on-blur? false]]
    ]]
  )

(defn save-button []
  [v-box
   :children [
              [h-box
               :style {:flex-flow "row wrap"}
               :class "panel"
               :justify :between
               :children
               [
                [box
                 :class "panel panel-body"
                 :child [button
                         :class "btn btn-primary"
                         :on-click #(dispatch [:employee-save])
                         :label "Save"]
                 ]
                ]]
              ]
   ])


(defn employee-core-details []
  (let [departments (subscribe [:deps])]
    (fn [employee]
      [v-box
       :width "500px"
       :class "panel panel-default"
       :children
       [
        [title :class "panel-heading panel-title" :label "Employee" :level :level3]
        [h-box
         :class "panel-body"
         :children
         [[v-box
           :size "auto"
           :gap "10px"
           :children
           [
            [department-drop-down-list employee departments]
            [employee-first-name employee]
            [employee-last-name employee]
            [employee-email employee]
            [employee-dob employee]
            [employee-start-date employee]
            [employee-end-date employee]
            ]]]]
        ]]
      )))

(defn employee-balances [employee]
  [v-box
   :size "auto"
   :width "350px"
   :class "panel panel-default"
   :children
   [
    [title :class "panel-heading panel-title" :label "Balances" :level :level3]
    [h-box
     :children
     [[v-box
       :size "auto"
       :class "panel-body"
       :gap "10px"
       :children
       [
        [employee-this-year-opening employee]
        [employee-this-year-remaining employee]
        [employee-next-year-opening employee]
        [employee-next-year-remaining employee]
        ]]]]
    ]]

  )

(defn employee-maintenance-form [employee]
  [v-box
   :children [[employee-core-heading employee]
              [h-box
               :style {:flex-flow "row wrap"}
               :class "panel"
               :justify :between
               :children
               [
                [box :class "panel panel-body" :child [employee-core-details employee]]
                [box :class "panel panel-body" :child [employee-balances employee]]
                [box :class "panel panel-body" :child [save-button]]
                ]]
              ]
   ])





