(ns metime.navigation.handlers
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
  (fn [_ [_]]
    (let [token (get-auth-cookie)]
      (-> dbase/default-db
          (assoc :authentication-token token
                 :view :home)))))


(register-handler
  :employee-to-edit
  (fn employee-edit-handler
    [db [_ id]]
    (dispatch [:fetch-employee id])
    (assoc db :nav-bar :employees :view :employee-editor)))

(register-handler
  :set-active-view
  (fn [db [_ view-component-id]]
    (assoc db :view view-component-id)))

(register-handler
  :set-active-navbar
  (fn [db [_ nav-bar-id]]
    (assoc db :nav-bar nav-bar-id)))

(register-handler
  :process-departments-response
  (fn [db [_ department-employees]]
    (let [value (js->clj department-employees)]
      (assoc db :departments-and-employees value))))

(register-handler
  :process-departments-only-response
  (fn [db [_ departments]]
    (let [value (js->clj departments)]
      (assoc db :departments value))))

(register-handler
  :process-employee-response
  (fn [db [_ employee]]
    (let [emp (js->clj employee)]
      (if (nil? (:id emp))
        (assoc db :employee (assoc {} :is-ready? true :not-found true))
        (assoc db :employee (assoc emp :is-ready? true :not-found false)))
      )))


(register-handler
  :fetch-departments-only
  (fn [db [_ endpoint]]
    (utils/call-api :GET (utils/api db endpoint) (:authentication-token db)
                    {:valid-token-handler   :process-departments-only-response
                     :invalid-token-handler :log-out
                     :response-keys         [:body :departments]})
    db))

(register-handler
  :fetch-departments-and-employees
  (fn [db [_ endpoint]]
    (utils/call-api :GET (utils/api db endpoint) (:authentication-token db)
                    {:valid-token-handler   :process-departments-response
                     :invalid-token-handler :log-out
                     :response-keys         [:body :departments]})
    db))

(register-handler
  :fetch-employee
  (fn [db [_ id]]
    (utils/call-api :GET (str (utils/api db "/employee/") id) (:authentication-token db)
                    {:valid-token-handler   :process-employee-response
                     :invalid-token-handler :log-out
                     :response-keys         [:body]})
    db))
