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
            [cljs.core.async :refer [put! take! <! >! chan timeout]]
            [metime.utils :as utils]
            [metime.employees.views]
            [metime.db :as dbase]
            [metime.utils :as utils]))

(defn get-auth-cookie []
  (utils/get-cookie "auth"))


(register-handler
  :fetch-auth-code
  (fn [db [_]]
    (let [token (get-auth-cookie)]
      (if token
        (dispatch [:authenticated token])
        (dispatch [:log-out])))
    db))


(register-handler
  :initialise-db
  (fn [db [_]]
    (if (empty? db)
      (let [token (get-auth-cookie)]
        (-> dbase/default-db
            (assoc :authentication-token token
                   :nav-bar :employees
                   :view :employees)))
      db)))

(defn update-active-view [db nav-bar view-component]
  (if (empty? (:authentication-token db))
    (assoc db :nav-bar nil :view :login)
    (assoc db :nav-bar nav-bar :view view-component)))


(defn employee-edit-handler
  [db [_ id]]
  (dispatch [:fetch-employee id])
  (assoc db :nav-bar :employees :view :edit-employee))

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
    (let [url (utils/api db endpoint)
          token (:authentication-token db)
          valid-token-handler :process-departments-response
          invalid-token-handler :log-out
          response-keys [:body :departments]]
      (utils/api-call url token valid-token-handler invalid-token-handler response-keys))
    db))

(register-handler
  :fetch-employee
  (fn [db [_ id]]
    (let [url (str (utils/api db "/employee/") id)
          token (:authentication-token db)
          valid-token-handler :process-employee-response
          invalid-token-handler :log-out
          response-keys [:body]]
      (utils/api-call url token valid-token-handler invalid-token-handler response-keys))
    db))
