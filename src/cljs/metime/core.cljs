(ns metime.core
  (:require-macros [cljs.core.async.macros :refer [go alt!]]
                   [reagent.ratom :refer [reaction]])
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [cljs-http.client :as http]
            [cljs-hash.md5 :as hashgen]
            [cljs-hash.goog :as gh]
            [metime.components.top-nav-bar :as nav]
            [secretary.core :as secretary :refer-macros [defroute]]
            [metime.components.employee :as ec]
            [reagent.core :as reagent :refer [atom]]
            [metime.utils :as utils]
            [re-frame.core :refer [register-handler
                                   path
                                   register-sub
                                   dispatch
                                   subscribe]])
  (:import goog.History))

(declare employees-route)
(declare file-manager-route)
(declare calendar-route)
(declare tables-route)
(declare login-route)
(declare user-route)

(enable-console-print!)
(secretary/set-config! :prefix "#")

(defn loader-component []
  [:div.loader-container [:img {:src "assets/img/loader.gif" }]])

(defn calendar-component []
  [:div {:style {:height "500px"}} [:h1 "Calendar page"]])

(defn tables-component []
  [:div {:style {:height "500px"}} [:h1 "Tables page"]])

(defn file-manager-component []
  [:div {:style {:height "500px"}} [:h1 "File manager page"]])

(defn user-component []
  [:div {:style {:height "500px"}} [:h1 "User page"]])

(defn login-component []
  [:div {:style {:height "500px"}} [:h1 "Login page"]])

(register-sub
  :departments
  (fn [db _]
    (reaction (:deps @db))))

(defn employees-component []
  (dispatch [:fetch-department-employees "/departments"])
  (let [deps (subscribe [:departments])]
    (fn []
      (if-not (seq @deps)
        [loader-component]
        [:div [ec/departments-container @deps]]))))

(register-sub
  :employee
  (fn [db _]
    (reaction (:employee @db))))

(defn employee-component []
  (let [emp (subscribe [:employee])]
    (fn []
      (if (nil? @emp)
        [loader-component]
        (if (:not-found @emp)
          [ec/employee-not-found]
          [ec/employee-container-form emp])))))

(defn not-found []
  [:div {:style {:height "500px"}} [:h1 {:style {:color "red"}} "404 NOT FOUND !!!!!"]])

(defroute root-route "/" []
  (dispatch [:switch-route employees-component (employees-route)]))

(defroute employees-route "/employees" []
  (dispatch [:switch-route employees-component (employees-route)]))

(defroute employee-route "/employee/:id" [id]
  (dispatch [:employee-route-switcher employee-component id]))

(defroute employee-add-route "/employees/add" []
  (dispatch [:employee-route-switcher employee-component 0]))

(defroute tables-route "/tables" []
  (dispatch [:switch-route tables-component (tables-route)]))

(defroute calendar-route "/calendar" []
  (dispatch [:switch-route calendar-component (calendar-route)]))

(defroute file-manager-route "/file-manager" []
  (dispatch [:switch-route file-manager-component (file-manager-route)]))

(defroute user-route "/user" []
  (dispatch [:switch-route user-component (user-route)]))

(defroute login-route "/login" []
  (dispatch [:switch-route login-component (login-route)]))

(defroute "*" []
  (dispatch [:switch-route not-found]))

(defn employee-route-switcher-middleware
  [handler]
  (fn employee-handler
    [db [_ view-component id]]
    (handler db [_ id])
    (nav/update-top-nav-bar db view-component (employees-route))))

(defn employee-route-switcher-handler
  [db [_ id]]
  (dispatch [:fetch-employee id])
  db)

(register-handler
 :employee-route-switcher
 employee-route-switcher-middleware
 employee-route-switcher-handler)

(register-handler
 :switch-route
 (fn [db [_ view-component top-level-menu-text]]
   (nav/update-top-nav-bar db view-component top-level-menu-text)))

(register-handler
 :initialise-db
 (fn
   [__]
   {:api-root-url "http://localhost:3030/api"
    :view employees-component
    :top-nav-bar [
                  {:path (employees-route)     :text "Employees"     :active true}
                  {:path (file-manager-route)  :text "File Manager"}
                  {:path (calendar-route)      :text "Calendar"}
                  {:path (tables-route)        :text "Tables"}
                  {:path (login-route)         :text "Login"}
                  {:path (user-route)          :text "User"}
                  ]}))

(register-handler
 :process-employees-response
 (fn [db [_ department-employees]]
   (assoc db :deps (js->clj department-employees))))

(register-handler
 :process-employee-response
 (fn [db [_ employee]]
   (if (nil? (:firstname employee))
     (assoc db :employee {:not-found true})
     (assoc db :employee (assoc (js->clj employee) :not-found false)))))

(defn fetch-employee
  [url]
  (go
   ;; The following will "park" until the http GET returns data
   (dispatch [:process-employee-response ((<! (http/get url)) :body)])))

(defn fetch-departments
  [url]
  (go
   ;; The following will "park" until the http GET returns data
   (dispatch [:process-employees-response (((<! (http/get url)) :body) :departments)])))

(register-handler
 :fetch-department-employees
 (fn [db [_ endpoint]]
   (let [_ (fetch-departments (utils/api db endpoint))]
     (assoc db :deps nil))))

(register-handler
 :fetch-employee
 (fn [db [_ id]]
   (if (> id 0)
     (fetch-employee (str (utils/api db "/employee/") id)))
     (assoc-in db [:employee] {:id 0
                               :firstname ""
                               :lastname ""
                               :email ""
                               :dob nil
                               :startdate nil
                               :endate nil
                               :this_year_opening 25
                               :this_year_remaining 25
                               :next_year_opening 25
                               :next_year_remaining 25
                               :departments_id 3
                               :managerid 10})))

(register-sub
 :db-changed?
 (fn [db _]
   (reaction @db)))

(register-sub
  :initialised?
  (fn [db _]
    (reaction (not (empty? @db)))))

(defn main-panel []
  (let [db (subscribe [:db-changed?])]
    (fn []
      [:div
       ;; Top nav bar
       [nav/top-nav-bar @db]
       ;; Components
       [(:view @db) (:params @db)]
      ])))

 (defn top-panel []
   (let [ready?  (subscribe [:initialised?])]
    (fn []
      (if-not @ready?
        [loader-component]
        [main-panel]))))

 (defn routing-history []
   "Routing history"
   (let [h (History.)
         f (fn [he] ;; goog.History.Event
             (let [token (.-token he)]
               (if (seq token)
                 (secretary/dispatch! token)
                 (do
                   ;; If we're at the root, go to #/employees
                   (utils/set-hash! "#/employees")
                   (secretary/dispatch! (employees-route))))))]

     (events/listen h EventType/NAVIGATE f)
     (doto h (.setEnabled true))))

(defn main []
  (dispatch [:initialise-db])
  ;; Main app component
  (reagent/render [top-panel] (js/document.getElementById "app-container"))
  (routing-history))
