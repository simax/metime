(ns metime.components.employee
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [cljs.core.async :refer [put! <! >! chan timeout]]
            [cljs-http.client :as http]
            [cljs-hash.md5 :as hashgen]
            [cljs-hash.goog :as gh]
            [metime.components.utils :as utils]
            [secretary.core :as secretary :refer-macros [defroute]]
            [reagent.core :as reagent :refer [atom]])
  (:import goog.History
           goog.History.EventType))

(enable-console-print!)

(defn fetch-employee [url-with-id]
  (let [c (chan)]
    (go
     (let [emp ((<! (http/get url-with-id)) :body)]
       (if-not (nil? emp)
         (>! c emp)
         (>! c "not found"))
       (println (str "emp: " (:email emp)))))
    c))


(defn employee-name [firstname lastname]
  (let [disp-name (str firstname " " lastname)
        name-length (count disp-name)]
    (if (> name-length 20) (str (subs disp-name 0 17) "...") disp-name)))


(defn employee-list-item [{:keys [lastname firstname email id]}]
  (fn [{:keys [lastname firstname email id]}]
    [:div {:class "col-md-3 col-lg-3"}
     [:div {:class "dash-unit"}
      [:div {:class "thumbnail" :style {:margin-top "20px"}}
       [:a {:href (str "/#employees/" id)}
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
       ]]]))


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
         (for [employee-row rows-of-employees]
           [employee-list-item employee-row {:key :id}]))]
      ]))


(defn department-list [departments]
  [:div.clearfix.accordian
   [:dom/ul
    (for [dep departments]
      [department-list-item dep])]])

(defn fetch-departments
  [url deps]
    (go (let [data (((<! (http/get url)) :body) :departments)]
          (js/console.log (str "Data: " data))
          )))

(defn departments-container [app opts]
   (let [deps (atom {})]
     (fn []
       (fetch-departments (:url opts) deps)
       [:div.row [department-list @deps]])))

(defn employee-not-found []
  [:h1 {:style {:color "red"}} "Sorry, we couldn't find that employee."])

(defn handle-change [e data edit-key]
  (swap! data edit-key (fn [_] (.. e -target -value))))

(defn handle-save [e data]
  (println data))

(defn employee-container-form [employee]
  [:div {:style {:padding "20" :background-color "white" :height "500"}}

   [:div.well
    ;; Employee gravatar
    [:div.container-fluid
     [:div.row
      [:div.col-md-2  [utils/gravatar {:gravatar-email (:email employee)}]]
      [:h1.col-md-8 (str (:firstname employee) " " (:lastname employee))]

      [:div.col-md-2
       [:h6.col-md-offset-4 "Manager"]
       [:div [utils/gravatar {:gravatar-email (:manager-email employee) :gravatar-size 75}]]
       [:h5.col-md-offset-2 (str (:manager-firstname employee) " " (:manager-lastname employee))]
      ]]
    ]]

   [:form.form-horizontal
    ;; First name
    [:div.form-group
     [:label.col-md-2.control-label {:for "first-name"} "First name"]
     [:div.col-md-4
      [:input#first-name.form-control
       {:type "text"
        :placeholder "First name"
        :on-change #(handle-change % employee :firstname)
        :value (:firstname employee)}]]]

    ;; Last name
    [:div.form-group
     [:label.col-md-2.control-label {:for "last-name"} "Last name"]
     [:div.col-md-4
      [:input#last-name.form-control
       {:type "text"
        :placeholder "Last name"
        :on-change #(handle-change % employee :lastname)
        :value (:lastname employee)}]]]

    ;; Email
    [:div.form-group
     [:label.col-md-2.control-label {:for "email"} "Email"]
     [:div.col-md-4
      [:input#last-name.form-control
       {:type "email"
        :placeholder "Email address"
        :on-change #(handle-change % employee :email)
        :value (:email employee)}]]]

    ;; Start date
    [:div.form-group
     [:label.col-md-2.control-label {:for "start-date"} "Start date"]
     [:div.col-md-3
      [:input#start-date.form-control
       {:type "date"
        :placeholder "Start date"
        :on-change #(handle-change % employee :startdate)
        :value (:startdate employee)}]]]

    ;; Save button
    [:div.form-group
     [:div.col-md-offset-2.col-md-4
      [:button#save.btn.btn-primary {:type "button" :on-click #(handle-save % employee)} "Save"]]]]])


(defn employee [app _ opts]
  (reagent/create-class
   {
    :display-name "employee"

    :component-will-mount
    (fn [app _ opts]
      (let [url-with-id (str (:url opts) (:id app))]
       (go
        (println (str "url-with-id: " url-with-id))
        (let [emp (<! (fetch-employee url-with-id))]
          (if (= emp "not found")
            (swap! app #(dissoc % :employee))
            (swap! app #(assoc % :employee emp)))))))

    :reagent-render
    (fn [app _ opts]
      (if (contains? app :employee)
        (employee-container-form (:employee app))
        (employee-not-found)))}))

