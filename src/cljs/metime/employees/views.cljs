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
            [re-com.core :refer [h-box v-box box gap
                                 title single-dropdown label
                                 input-text datepicker datepicker-dropdown button
                                 popover-anchor-wrapper popover-content-wrapper
                                 popover-tooltip md-circle-icon-button]
             :refer-macros [handler-fn]]
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
              ]])


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

(defn formatted-date [date-str]
  "If date-str is a valid date, returns a correctly formatted date string like 01-01-2015.
   Otherwise returns date-str."
  (if (empty? date-str)
    ""
    (let [formatted-date-str (try (unparse (formatter "yyyy-MM-dd") (iso8601->date (clojure.string/replace date-str "-" "")))
                                  (catch :default e date-str))]
      formatted-date-str)
    ))

(defn str->date [date-str]
  "Returns a date object from date-str. Returns todays date if date-str is empty"
  (if (empty? date-str)
    (now)
    (try (iso8601->date (clojure.string/replace date-str "-" ""))
         (catch :default e (now)))))


(defn date-input-with-popup [date-field date showing? title]
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
             :position :above-center
             :backdrop-opacity 0.5
             :width "250px"
             :no-clip? true
             :arrow-length 0
             :arrow-width 0
             :close-button? true
             :on-cancel #(reset! showing? false)
             :body [datepicker
                    :model (reagent/atom (str->date date))
                    :show-today? true
                    :on-change #(dispatch [:input-change-dates date-field %])]]])

(defn employee-dob [employee]
  (let [showing-date-popup? (reagent/atom false)
        showing-error-icon? (subscribe [:error-employee-dob])
        showing-tooltip? (reagent/atom false)]
    [h-box
     :justify :start
     :children
     [
      [box :width "150px" :child [label :label "Date of birth"]]
      [box :child [input-text
                   :validation-regex #"^(\d{0,2}\-{0,1}\d{0,2}-{0,1}\d{0,4})$" ;#"^(-{0,1})(\d{0,2})$"
                   :style {:border-radius "4px 0 0 4px"}
                   :placeholder "Date of Birth"
                   :model (formatted-date (:dob employee))
                   :width "120px"
                   :status (when (seq (get-in employee [:validation-errors :dob])) :error)
                   ;:status-icon? (seq (get-in employee [:validation-errors :dob]))
                   :status-tooltip (apply str (get-in employee [:validation-errors :dob]))
                   :on-change #(dispatch [:input-change-dates :dob %])]]
      (println (str "showing-error-icon? " @showing-error-icon?))
      (date-input-with-popup :dob (:dob employee) showing-date-popup? "Date of birth")
      (when @showing-error-icon? [popover-tooltip
                      :label "This is a tooltip"
                      :position :right-center
                      :showing? showing-tooltip?
                      :status :error
                      :width "150px"
                      :anchor [:i
                               {:class         "zmdi zmdi-alert-circle"
                                :on-mouse-over (handler-fn (reset! showing-tooltip? true))
                                :on-mouse-out  (handler-fn (reset! showing-tooltip? false))
                                :style         {:color     "red"
                                                :font-size "130%"}}]
                      ])]]))




(defn employee-start-date [employee]
  (let [showing? (reagent/atom false)]
    [h-box
     :justify :start
     :children
     [
      [box :width "150px" :child [label :label "Start date"]]
      [box :child [input-text
                   :placeholder "Start date"
                   :model (formatted-date (:startdate employee))
                   :width "120px"
                   :status (when (seq (get-in employee [:validation-errors :startdate])) :error)
                   :status-icon? (seq (get-in employee [:validation-errors :startdate]))
                   :status-tooltip (apply str (get-in employee [:validation-errors :startdate]))
                   :on-change #(dispatch [:input-change-dates :startdate %])]]
      (date-input-with-popup :startdate (:startdate employee) showing? "Start date")]]))

(defn employee-end-date [employee]
  (let [showing? (reagent/atom false)]
    [h-box
     :justify :start
     :children
     [
      [box :width "150px" :child [label :label "End date"]]
      [box :child [input-text
                   :placeholder "End date"
                   :model (formatted-date (:enddate employee))
                   :width "120px"
                   :status (when (seq (get-in employee [:validation-errors :enddate])) :error)
                   :status-icon? (seq (get-in employee [:validation-errors :enddate]))
                   :status-tooltip (apply str (get-in employee [:validation-errors :enddate]))
                   :on-change #(dispatch [:input-change-dates :enddate %])]]

      (date-input-with-popup :enddate (:enddate employee) showing? "End date")]]))

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





