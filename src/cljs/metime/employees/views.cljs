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
            [re-frame.core :refer [register-handler
                                   path
                                   debug
                                   after
                                   enrich
                                   register-sub
                                   dispatch
                                   dispatch-sync
                                   subscribe]])
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
        [:button {:class       "btn btn-default glyphicon glyphicon-sort"
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
  [:div.well [:h1.text-center {:style {:color "red"}} "Sorry, we couldn't find that employee."]])

(defn employee-core-heading [employee man-id]
  [:div.panel.panel-default
   [:div.panel-body
    ;; Employee gravatar
    [:div.container-fluid
     [:div.row
      [:div.col-md-2 [utils/gravatar {:gravatar-email (:email employee)}]]
      [:h1.col-md-8 (str (:firstname employee) " " (:lastname employee))]

      [:div.col-md-2
       [:h6.col-md-offset-4 "Manager"]
       [:div [utils/gravatar {:gravatar-email (:manager-email employee) :gravatar-size 75}]]
       [:h5.col-md-offset-2 (str (:manager-firstname employee) " " (:manager-lastname employee))]
       ]]
     ]]]
  )

(defn employee-core-details []
  (let [departments (subscribe [:deps])]
    (fn [employee]
      [:div.panel.panel-default
       [:div.panel-heading [:h3.panel-title "Employee"]]
       [:div.panel-body
        [:form.form-horizontal

         ;; Departments drop down list
         [:div.form-group
          [:label.col-md-4.control-label {:for "department"} "Department"]
          [:div.col-md-7
           [:select.form-control {:id        "department"
                                  :name      "department"
                                  :value     (:departments_id employee)
                                  :on-change #(dispatch-sync [:department-change (utils/input-value %)])}
            (for [m @departments]
              ^{:key (:departments_id m)} [:option {:value (:departments_id m)} (:department m)])
            ]]]

         ;; First name
         [:div.form-group
          [:label.col-md-4.control-label {:for "firstname"} "First name"]
          [:div.col-md-7
           [utils/input-element
            {:id            "firstname"
             :name          "firstname"
             :type          "text"
             :placeholder   "First name"
             :default-value (:firstname employee)
             :on-blur       #(dispatch [:input-change :firstname (utils/input-value %)])
             }]
           ]]

         ;; Last name
         [:div.form-group
          [:label.col-md-4.control-label {:for "lastname"} "Last name"]
          [:div.col-md-7
           [utils/input-element
            {:id            "lastname"
             :name          "lastname"
             :type          "text"
             :placeholder   "Last name"
             :default-value (:lastname employee)
             :on-blur       #(dispatch [:input-change :lastname (utils/input-value %)])
             }]]
          ]

         ;; Email
         [:div.form-group
          [:label.col-md-4.control-label {:for "email"} "Email"]
          [:div.col-md-7
           [utils/input-element
            {:id            "email"
             :name          "email"
             :type          "email"
             :placeholder   "Email address"
             :default-value (:email employee)
             :on-blur       #(dispatch [:input-change :email (utils/input-value %)])
             }]]
          ]

         ;; DOB
         [:div.form-group
          [:label.col-md-4.control-label {:for "dob"} "Date of birth"]
          [:div.col-md-4
           [utils/input-element
            {:id            "dob"
             :name          "dob"
             :type          "date"
             :placeholder   "Dob"
             :default-value (:dob employee)
             :on-blur       #(dispatch [:input-change :dob (utils/input-value %)])
             }]]
          ]

         ;; Start date
         [:div.form-group
          [:label.col-md-4.control-label {:for "startdate"} "Start date"]
          [:div.col-md-4
           [utils/input-element
            {:id            "startdate"
             :name          "startdate"
             :type          "date"
             :placeholder   "Start date"
             :default-value (:startdate employee)
             :on-blur       #(dispatch [:input-change :startdate (utils/input-value %)])
             }]]
          ]

         ;; End date
         [:div.form-group
          [:label.col-md-4.control-label {:for "enddate"} "End date"]
          [:div.col-md-4
           [utils/input-element
            {:id            "enddate"
             :name          "enddate"
             :type          "date"
             :placeholder   "End date"
             :default-value (:enddate employee)
             :on-blur       #(dispatch [:input-change :enddate (utils/input-value %)])
             }]]
          ]
         ]]]
      )))

(defn employee-balances [employee]
  [:div.panel.panel-default
   [:div.panel-heading [:h3.panel-title "Balances"]]
   [:div.panel-body

    [:form.form-horizontal

     ;; this_year_opening
     [:div.form-group
      [:label.col-md-6.control-label {:for "this_year_opening"} "This year opening"]
      [:div.col-md-4
       [utils/input-element
        {:id            "this_year_opening"
         :name          "this_year_opening"
         :type          "number"
         :placeholder   ""
         :default-value (utils/parse-int (:this_year_opening employee))
         :on-blur       #(dispatch [:input-change :this_year_opening (utils/input-value %)])
         }]]
      ]

     ;; this_year_remaining
     [:div.form-group
      [:label.col-md-6.control-label {:for "this_year_remaining"} "This year remaining"]
      [:div.col-md-4
       [utils/input-element
        {:id            "this_year_remaining"
         :name          "this_year_remaining"
         :type          "number"
         :placeholder   ""
         :default-value (utils/parse-int (:this_year_remaining employee))
         :on-blur       #(dispatch [:input-change :this_year_remaining (utils/input-value %)])
         }]]
      ]

     ;; next_year_opening
     [:div.form-group
      [:label.col-md-6.control-label {:for "next_year_opening"} "Next year opening"]
      [:div.col-md-4
       [utils/input-element
        {:id            "next_year_opening"
         :name          "next_year_opening"
         :type          "number"
         :placeholder   ""
         :default-value (utils/parse-int (:next_year_opening employee))
         :on-blur       #(dispatch [:input-change :next_year_opening (utils/input-value %)])
         }]]
      ]

     ;; next_year_remaining
     [:div.form-group
      [:label.col-md-6.control-label {:for "next_year_remaining"} "Next year remaining"]
      [:div.col-md-4
       [utils/input-element
        {:id            "next_year_remaining"
         :name          "next_year_remaining"
         :type          "number"
         :placeholder   ""
         :default-value (utils/parse-int (:next_year_remaining employee))
         :on-blur       #(dispatch [:input-change :next_year_remaining (utils/input-value %)])
         }]]
      ]
     ]
    ]]
  )

(defn employee-maintenance-form [employee dep-id man-id]
  [:div.well

   [employee-core-heading employee man-id]
   [:div.well
    [:div.row
     [:div.col-md-8
      [employee-core-details employee dep-id man-id]
      ]
     [:div.col-md-4
      [employee-balances employee]
      ]
     ]
    ]

   ;; Save button
   [:div.well
    [:form.form-horizontal
     [:div.form-group
      [:div.col-md-offset-2.col-md-4
       [:button#save.btn.btn-primary {:type "button" :on-click #(dispatch [:employee-save])} "Save"]]]]
    ]
   ])




