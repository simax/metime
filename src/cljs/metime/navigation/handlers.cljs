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
    (let [token (get-auth-cookie)]
      (if token
        (dispatch [:authenticated token])
        (dispatch [:log-out])))
    db))


(register-handler
  :initialise-db
  (fn [db [_]]
    (let [token (get-auth-cookie)]
      (-> (merge db dbase/default-db)
          (assoc :authentication-token token
                 :nav-bar :employees
                 :view :employees)))))

(defn update-active-view [db nav-bar view-component]
  (if (empty? (:authentication-token db))
    (assoc db :nav-bar nil :view :login)
    (assoc db :nav-bar nav-bar :view view-component)))

(defn add-authorization-header [token]
  {:headers {"authorization" (str "Token " token)}})

(defn secure-url [url token]
  (http/get url (add-authorization-header token)))

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

(defn api-call [url token dispatch-handler response-keys]
  ;; Make a secure url call with authorization header.
  ;; Dispatch redirect to login if unauthorized.
  (go
    ;; The following go block will "park" until the http request returns data
    (let [response (<! (secure-url url token))
          status (:status response)]
      (if (= status 401)
        (dispatch [:log-out])
        (dispatch [dispatch-handler (get-in response response-keys)])))))


(register-handler
  :fetch-department-employees
  (fn [db [_ endpoint]]
    (let [url (utils/api db endpoint)
          token (:authentication-token db)
          dispatch-handler :process-departments-response
          response-keys [:body :departments]]
      (api-call url token dispatch-handler response-keys))
    db))

(register-handler
  :fetch-employee
  (fn [db [_ id]]
    (let [url (str (utils/api db "/employee/") id)
          token (:authentication-token db)
          dispatch-handler :process-employee-response
          response-keys [:body]]
      (api-call url token dispatch-handler response-keys))
    db))
