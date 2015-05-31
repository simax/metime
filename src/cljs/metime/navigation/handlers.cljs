(ns metime.navigation.handlers
  (:require-macros [cljs.core.async.macros :refer [go alt!]]
                   [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [register-handler
                                   path
                                   register-sub
                                   dispatch
                                   subscribe]]
            [secretary.core :refer-macros [defroute]]
            [cljs.core.async :refer [put! take! <! >! chan timeout]]
            [cljs-http.client :as http]
            [metime.utils :as utils]
            [metime.employees.views :as ev]))

(declare root-route)
(declare employee-add-route)
(declare employees-route)
(declare employee-route)
(declare file-manager-route)
(declare calendar-route)
(declare tables-route)
(declare login-route)
(declare user-route)

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

(defn employees-component []
  (dispatch [:fetch-department-employees "/departments"])
  (let [deps (subscribe [:departments])]
    (fn []
      (if-not (seq @deps)
        [loader-component]
        [:div [ev/departments-container @deps]]))))

(defn employee-component []
  (let [emp (subscribe [:employee])]
    (fn []
      (if (not (:is-ready? @emp))
        [loader-component]
        (if (:not-found @emp)
          [ev/employee-not-found]
          [ev/employee-maintenance-form @emp]
          )))))

(defroute employees-route "/employees" []
          (dispatch [:switch-route employees-component (employees-route)]))

(defroute root-route "/" []
          (utils/set-hash! "#/employees")
          (dispatch [:switch-route employees-component (employees-route)]))

(defroute employee-route "/employee/:id" [id]
          (dispatch [:employee-route-switcher employee-component id]))

(defroute employee-add-route "/employees/add" []
          (dispatch [:employee-route-switcher employee-component 0]))


(defn toggle-active-status [token item]
  (if (= (:path item) token)
    (assoc item :active true)
    (assoc item :active false)))

(defn update-nav-bar [db view-component top-level-menu-text]
  (let [updated-view (assoc db :view view-component)]
    (update-in updated-view [:nav-bar] #(map (partial toggle-active-status top-level-menu-text) %))))

(defn employee-route-switcher-middleware [handler]
  (fn employee-handler
    [db [_ view-component id]]
    (handler db [_ id])
    (update-nav-bar db view-component (employees-route))))

(defn employee-route-switcher-handler
  [db [_ id]]
  (dispatch [:fetch-employee id])
  db)

(defn fetch-employee
  [db url]
  ;; The following go block will "park" until the http request returns data
  (go (dispatch [:process-employee-response ((<! (http/get url)) :body)]))
  (assoc db :employee {:is-ready? false}))

(defn fetch-departments
  [url]
  (go
    ;; The following go block will "park" until the http request returns data
    (dispatch [:process-departments-response (((<! (http/get url)) :body) :departments)])))

(register-handler
  :employee-route-switcher
  employee-route-switcher-middleware
  employee-route-switcher-handler)

(register-handler
  :switch-route
  (fn [db [_ view-component top-level-menu-text]]
    (update-nav-bar db view-component top-level-menu-text)))

(register-handler
  :initialise-db
  (fn
    [__]
    {:api-root-url "http://localhost:3030/api"
     :view         employees-component
     :employee     {:is-ready? false}
     :nav-bar      [
                    {:path (employees-route) :text "Employees" :active true}
                    {:path (file-manager-route) :text "File Manager"}
                    {:path (calendar-route) :text "Calendar"}
                    {:path (tables-route) :text "Tables"}
                    {:path (login-route) :text "Login"}
                    {:path (user-route) :text "User"}
                    ]}))

(register-handler
  :process-departments-response
  (fn [db [_ department-employees]]
    (assoc db :deps (js->clj department-employees))))

(register-handler
  :process-employee-response
  (fn [db [_ employee]]
    (let [emp (js->clj employee)]
      (if (nil? (:id emp))
        (assoc db :employee (assoc {} :is-ready? true :not-found true))
        (assoc db :employee (assoc emp :is-ready? true :not-found false)))
      )))

(register-handler
  :fetch-department-employees
  (fn [db [_ endpoint]]
    (let [_ (fetch-departments (utils/api db endpoint))]
      (assoc db :deps nil))))

(register-handler
  :fetch-employee
  (fn [db [_ id]]
    (if (> id 0)
      (fetch-employee db (str (utils/api db "/employee/") id))
      (assoc-in db [:employee] {:is-ready?           true
                                :id                  0
                                :firstname           ""
                                :lastname            ""
                                :email               ""
                                :dob                 nil
                                :startdate           nil
                                :enddate             nil
                                :this_year_opening   25
                                :this_year_remaining 25
                                :next_year_opening   25
                                :next_year_remaining 25
                                :departments_id      0
                                :managerid           0}))))

