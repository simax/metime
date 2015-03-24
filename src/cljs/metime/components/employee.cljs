(ns metime.components.employee
  (:require-macros [cljs.core.async.macros :refer [go alt!]]
                   [reagent.ratom :refer [reaction]])
  (:require [cljs.core.async :refer [put! take! <! >! chan timeout]]
            [cljs-http.client :as http]
            [cljs-hash.md5 :as hashgen]
            [cljs-hash.goog :as gh]
            [metime.utils :as utils]
            [secretary.core :as secretary :refer-macros [defroute]]
            [metime.components.common :as com]
            [reagent.core :as reagent :refer [atom]]
            [re-frame.core :refer [register-handler
                                   path
                                   register-sub
                                   dispatch
                                   subscribe]])
  (:import goog.History
           goog.History.EventType))

(enable-console-print!)

(defn handle-input-change [db [_ property-name new-value ]]
  (assoc-in db [:employee property-name] new-value))

(register-handler
  :input-change
  handle-input-change)

(defn handle-employee-add [db _]
  ;; Need to make this better
  (let [url "#/employees/add"]
    (utils/set-hash! url)
    (secretary/dispatch! url)))

(register-handler
 :employee-add
 handle-employee-add)

(defn is-new-employee? [id]
  (zero? id))

(defn handle-employee-save [db _]
  (let [employee-id (get-in db [:employee :id])]
    (if (not (is-new-employee? employee-id))
      (let [endpoint (utils/api db (str "/employee/" employee-id))]
        (http/put endpoint {:form-params (:employee db)}))
      (let [endpoint (utils/api db "/employees")
            data (assoc (:employee db) :password "password1" :password-confirm "password1")]
        (http/post endpoint {:form-params data})))
    db))

(register-handler
 :employee-save
 handle-employee-save)

(defn employee-name [firstname lastname]
  (let [disp-name (str firstname " " lastname)
        name-length (count disp-name)]
    (if (> name-length 20) (str (subs disp-name 0 17) "...") disp-name)))

(defn employee-list-item [{:keys [lastname firstname email id]}]
  [:div {:class "col-md-3 col-lg-3"}
   [:div {:class "dash-unit"}
    [:div {:class "thumbnail" :style {:margin-top "20px"}}
     [:a {:href (str "#/employee/" id)}
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

(defn department-list-item [{:keys [department managerid manager-firstname manager-lastname manager-email employees]}]
  (let [department-list-item (filter #(not= (:id %) managerid) employees)
        rows-of-employees (partition 4 4 nil department-list-item)]
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
         [:button {:class "btn btn-default glyphicon glyphicon-sort"
                   :data-toggle "collapse"
                   :data-parent "accordian"
                   :data-target (str "#" (clojure.string/replace department #"[\s]" "-"))}]]]]

       [:div.panel-body.panel-collapse.collapse {:id (clojure.string/replace department #"[\s]" "-") :style {:height "auto"}}
        (if (not-empty rows-of-employees)
          [:div
           [:button {:class "btn btn-primary glyphicon glyphicon-plus-sign"
                     :on-click #(dispatch [:employee-add])} " Add employee"]
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

(defn departments-query
  [db _]
  (reaction (get-in @db [:deps])))

(register-sub
 :deps
 departments-query)


(defn employee-container-form [employee]
  (let [departments  (subscribe [:deps])]
    (fn []
      [:div.well
      [:div.well
       ;; Employee gravatar
       [:div.container-fluid
        [:div.row
         [:div.col-md-2  [utils/gravatar {:gravatar-email (:email @employee)}]]
         [:h1.col-md-8 (str (:firstname @employee) " " (:lastname @employee))]

         [:div.col-md-2
          [:h6.col-md-offset-4 "Manager"]
          [:div [utils/gravatar {:gravatar-email (:manager-email @employee) :gravatar-size 75}]]
          [:h5.col-md-offset-2 (str (:manager-firstname @employee) " " (:manager-lastname @employee))]
          ]]
        ]]

      [:form.form-horizontal
       ;; First name
       [:div.form-group
        [:label.col-md-2.control-label {:for "firstname"} "First name"]
        [:div.col-md-4

         [com/input-element
          {:id "firstname"
           :name "firstname"
           :type "text"
           :placeholder "First name"
           :value (:firstname @employee)
           :on-change #(dispatch [:input-change :firstname (com/input-value %)])
           }]
         ]]

       ;; Last name
       [:div.form-group
        [:label.col-md-2.control-label {:for "lastname"} "Last name"]
        [:div.col-md-4
         [com/input-element
          {:id "lastname"
           :name "lastname"
           :type "text"
           :placeholder "Last name"
           :value (:lastname @employee)
           :on-change #(dispatch [:input-change :lastname (com/input-value %)])
           }]]
        ]

       ;; Email
       [:div.form-group
        [:label.col-md-2.control-label {:for "email"} "Email"]
        [:div.col-md-4
         [com/input-element
          {:id "email"
           :name "email"
           :type "email"
           :placeholder "Email address"
           :value (:email @employee)
           :on-change #(dispatch [:input-change :email (com/input-value %)])
           }]]
        ]

       ;; Dob
       [:div.form-group
        [:label.col-md-2.control-label {:for "dob"} "Date of birth"]
        [:div.col-md-3
         [com/input-element
          {:id "dob"
           :name "dob"
           :type "date"
           :placeholder "Dob"
           :value (:dob @employee)
           :on-change #(dispatch [:input-change :dob (com/input-value %)])
           }]]
        ]

       ;; Start date
       [:div.form-group
        [:label.col-md-2.control-label {:for "startdate"} "Start date"]
        [:div.col-md-3
         [com/input-element
          {:id "startdate"
           :name "startdate"
           :type "date"
           :placeholder "Start date"
           :value (:startdate @employee)
           :on-change #(dispatch [:input-change :startdate (com/input-value %)])
           }]]
        ]

       ;; End date
       [:div.form-group
        [:label.col-md-2.control-label {:for "enddate"} "End date"]
        [:div.col-md-3
         [com/input-element
          {:id "enddate"
           :name "enddate"
           :type "date"
           :placeholder "End date"
           :value (:enddate @employee)
           :on-change #(dispatch [:input-change :enddate (com/input-value %)])
           }]]
        ]

       ;; Departments drop down list
       [:div.form-group
        [:label.col-md-2.control-label {:for "department"} "Department"]
        [:div.col-md-3
         [:select.form-control {:name "department"}
          ;;[:option {:value 0} ""]
          ;; Need to add department id into the mix here.
          (for [m @departments]
            [:option (:department m)])]
          ]]

       ;; this_year_opening
       [:div.form-group
        [:label.col-md-2.control-label {:for "this_year_opening"} "This year opening"]
        [:div.col-md-3
         [com/input-element
          {:id "this_year_opening"
           :name "this_year_opening"
           :type "number"
           :placeholder ""
           :value (:this_year_opening @employee)
           :on-change #(dispatch [:input-change :this_year_opening (com/input-value %)])
           }]]
        ]

       ;; this_year_remaining
       [:div.form-group
        [:label.col-md-2.control-label {:for "this_year_remaining"} "This year remaining"]
        [:div.col-md-3
         [com/input-element
          {:id "this_year_remaining"
           :name "this_year_remaining"
           :type "number"
           :placeholder ""
           :value (:this_year_remaining @employee)
           :on-change #(dispatch [:input-change :this_year_remaining (com/input-value %)])
           }]]
        ]

       ;; next_year_opening
       [:div.form-group
        [:label.col-md-2.control-label {:for "next_year_opening"} "Next year opening"]
        [:div.col-md-3
         [com/input-element
          {:id "next_year_opening"
           :name "next_year_opening"
           :type "number"
           :placeholder ""
           :value (:next_year_opening @employee)
           :on-change #(dispatch [:input-change :next_year_opening (com/input-value %)])
           }]]
        ]

       ;; next_year_remaining
       [:div.form-group
        [:label.col-md-2.control-label {:for "next_year_remaining"} "Next year remaining"]
        [:div.col-md-3
         [com/input-element
          {:id "next_year_remaining"
           :name "next_year_remaining"
           :type "number"
           :placeholder ""
           :value (:next_year_remaining @employee)
           :on-change #(dispatch [:input-change :next_year_remaining (com/input-value %)])
           }]]
        ]

       ;; Save button
       [:div.form-group
        [:div.col-md-offset-2.col-md-4
         [:button#save.btn.btn-primary {:type "button" :on-click #(dispatch [:employee-save])} "Save"]]]]])))

(defn fetch-employee [url-with-id]
  (let [c (chan)]
    (go
     (let [emp ((<! (http/get url-with-id)) :body)]
       (if-not (nil? emp)
         (>! c emp)
         (>! c "not found"))
       (println (str "emp: " (:email emp)))))
    c))

(defn employee [app opts]
  (let [url-with-id (str (:url opts) (:id opts))]
    (go
     (let [emp (<! (fetch-employee url-with-id))]
       (if (= emp "not found")
         (swap! app #(dissoc % :employee))
         (swap! app #(assoc % :employee emp)))))
    (fn [app opts]
      (if (contains? @app :employee)
        [employee-container-form (:employee @app)]
        [employee-not-found]))))
