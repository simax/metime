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
            [re-com.core :refer [h-box v-box box gap title single-dropdown label input-text datepicker-dropdown]]
            [re-com.datepicker :refer [iso8601->date datepicker-args-desc]]
            [cljs-time.coerce :as tc]
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
    :children
    [
     [box :child [utils/gravatar {:gravatar-email (:email employee)}]]
     [v-box
      :children
      [[box :child [:h1 (str (:firstname employee) " " (:lastname employee))]]
       [:h4 {:style {:color "grey"}} (:department employee)]
       ]]]]]
  )

(defn manager-gravatar [employee]
  [v-box
   :justify :start
   :align :center
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
  "Departments drop down list"
  [h-box
   :justify :start
   :children
   [
    [box :width "150px" :child [label :class "control-label" :label "Department"]]
    [box :size "auto" :child [single-dropdown
                              :model (:departments_id employee)
                              :choices (department-list-choices departments)
                              :on-change #(dispatch-sync [:department-change (utils/input-value %)])
                              ]]]
   ])

(defn employee-first-name [employee]
  [h-box
   :justify :between
   :children
   [
    [box :width "150px" :child [label :label "First name"]]
    [box :size "auto" :child [input-text
                              :width "300px"
                              :model (:firstname employee)
                              ;:status nil
                              ;:status-icon? false
                              ;:status-tooltip ""
                              :placeholder "Employees first name"
                              :on-change #(dispatch [:input-change :firstname (utils/input-value %)])
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
                              :width "300px"
                              :model (:lastname employee)
                              ;:status nil
                              ;:status-icon? false
                              ;:status-tooltip ""
                              :placeholder "Employees last name"
                              :on-change #(dispatch [:input-change :lastname (utils/input-value %)])
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
                              :width "300px"
                              :model (:email employee)
                              ;:status nil
                              ;:status-icon? false
                              ;:status-tooltip ""
                              :placeholder "Employees email address"
                              :on-change #(dispatch [:input-change :email (utils/input-value %)])
                              :change-on-blur? false]]
    ]]
  )

(defn prep-date [date-str]
  (println date-str)
  (if (empty? date-str) "19000101" (clojure.string/replace date-str "-" "")))

(defn employee-dob [employee]
  [h-box
   :justify :start
   :children
   [
    [box :width "150px" :child [label :label "Date of birth"]]
    [box :child [datepicker-dropdown
                 :model (reagent/atom (iso8601->date (prep-date (:dob employee))))
                 ;:status nil
                 ;:status-icon? false
                 ;:status-tooltip ""
                 :show-today? true
                 ;:minimum (goog.date.UtcDateTime. "1900-01-01")
                 ;:maximum (goog.date.UtcDateTime. "2010-01-01")
                 :on-change #(dispatch [:input-change :dob (utils/input-value %)])
                 ]]
    ]]
  )

(defn employee-start-date [employee]
  [h-box
   :justify :start
   :children
   [
    [box :width "150px" :child [label :label "Start date"]]
    [box :child [datepicker-dropdown
                 :model (reagent/atom (iso8601->date (prep-date (:startdate employee))))
                 :show-today? true
                 ;:status nil
                 ;:status-icon? false
                 ;:status-tooltip ""
                 :on-change #(dispatch [:input-change :startdate (utils/input-value %)])
                 ]]
    ]]
  )

(defn employee-end-date [employee]
  [h-box
   :justify :start
   :children
   [
    [box :width "150px" :child [label :label "End date"]]
    [box :child [datepicker-dropdown
                 :model (reagent/atom (iso8601->date (prep-date (:enddate employee))))
                 :show-today? true
                 ;:status nil
                 ;:status-icon? false
                 ;:status-tooltip ""
                 :on-change #(dispatch [:input-change :enddate (utils/input-value %)])
                 ]]
    ]]
  )

(defn employee-this-year-opening [employee]
  [h-box
   :justify :between
   :children
   [
    [box :child [label :label "This year opening"]]
    [box :child [input-text
                 :width "50px"
                 :model (str (:this_year_opening employee))
                 ;:status nil
                 ;:status-icon? false
                 ;:status-tooltip ""
                 :on-change #(dispatch [:input-change :this_year_opening (utils/input-value %)])
                 :change-on-blur? false]]
    ]]
  )

(defn employee-this-year-remaining [employee]
  [h-box
   :justify :between
   :children
   [
    [box :child [label :label "This year remaining"]]
    [box :child [input-text
                 :width "50px"
                 :model (str (:this_year_remaining employee))
                 ;:status nil
                 ;:status-icon? false
                 ;:status-tooltip ""
                 :on-change #(dispatch [:input-change :this_year_remaining (utils/input-value %)])
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
                 :width "50px"
                 :model (str (:next_year_opening employee))
                 ;:status nil
                 ;:status-icon? false
                 ;:status-tooltip ""
                 :on-change #(dispatch [:input-change :next_year_opening (utils/input-value %)])
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
                 :width "50px"
                 :model (str (:next_year_remaining employee))
                 ;:status nil
                 ;:status-icon? false
                 ;:status-tooltip ""
                 :on-change #(dispatch [:input-change :next_year_remaining (utils/input-value %)])
                 :change-on-blur? false]]
    ]]
  )


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
                ]]
              ]
   ])

;; Save button
;[:div.well
; [:form.form-horizontal
;  [:div.form-group
;   [:div.col-md-offset-2.col-md-4
;    [:button#save.btn.btn-primary {:type "button" :on-click #(dispatch [:employee-save])} "Save"]]]]
; ]




