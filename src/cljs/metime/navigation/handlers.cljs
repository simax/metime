(ns metime.navigation.handlers
  (:refer-clojure :exclude [run!])
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
            [metime.db :as dbase]
            [metime.utils :as utils]))

(defn get-auth-cookie []
  (utils/get-cookie "auth"))

(register-handler
  :fetch-auth-code
  (fn [db [_]]
    (assoc db :authentication-token (get-auth-cookie))))

(register-handler
  :initialise-db
  (fn [db [_]]
    (dispatch [:fetch-auth-code])
    (merge db dbase/default-db)))

(defn update-active-view [db nav-bar view-component]
  (if (empty? (:authentication-token @db))
    (assoc db :nav-bar nil :view :log-in)
    (assoc db :nav-bar nav-bar :view view-component)))

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

(defn employee-edit-handler
  [db [_ id]]
  (dispatch [:fetch-employee id])
  (assoc db :nav-bar :employees :view :employee))

(register-handler
  :employee-edit
  employee-edit-handler)

(register-handler
  :set-active-view
  (fn [db [_ nav-bar view-component-id]]
    (update-active-view db nav-bar view-component-id)))

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
    (fetch-departments (utils/api db endpoint))
    (assoc db :deps nil)))

(register-handler
  :fetch-employee
  (fn [db [_ id]]
    (when (> id 0)
      (fetch-employee db (str (utils/api db "/employee/") id)))))
