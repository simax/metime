(ns metime.navigation.handlers
  (:require-macros [cljs.core.async.macros :refer [go alt!]]
                   [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [debug
                                   register-handler
                                   path
                                   register-sub
                                   dispatch
                                   subscribe]]
            [secretary.core :refer-macros [defroute]]
            [cljs.core.async :refer [put! take! <! >! chan timeout]]
            [cljs-http.client :as http]
            [metime.utils :as utils]
            [metime.employees.views]
            [metime.db :as dbase]))

(defn toggle-active-status [nav-bar-id item]
  (if (= (:id item) nav-bar-id)
    (assoc item :active true)
    (assoc item :active false)))

(defn update-nav-bar [db view-component nav-bar-id]
  (let [updated-view (assoc db :view view-component)]
    (update-in updated-view [:nav-bar] #(map (partial toggle-active-status nav-bar-id) %))))

(defn employee-route-switcher-middleware [handler]
  (fn employee-handler
    [db [_ view-component id]]
    (handler db [_ id])
    (update-nav-bar db view-component :employees)))

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
  ;;[debug employee-route-switcher-middleware]
  employee-route-switcher-middleware
  employee-route-switcher-handler)

(register-handler
  :switch-route
  (fn [db [_ nav-bar-id view-component]]
    (update-nav-bar db nav-bar-id view-component)))

(register-handler
  :initialise-db
  (fn [db [_ initial-view]]
    (assoc (merge db dbase/default-db) :view initial-view)))

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

