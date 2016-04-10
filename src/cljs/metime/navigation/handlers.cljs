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
            [metime.routes :as routes]
            [metime.employees.views]
            [metime.db :as dbase]
            [metime.utils :as utils]
            [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame-tracer.core :refer [tracer]]))


;(trace-forms
; {:tracer (tracer :color "green")}

(defn get-auth-cookie []
  (utils/get-cookie "auth"))

(register-handler
  :fetch-auth-code
  (fn hdlr-fetch-auth-code [db [_]]
    (let [token (get-auth-cookie)]
      (if token
        (dispatch [:authenticated token])
        (dispatch [:log-out])))
    db))

(register-handler
  :initialise-db
  (fn hdlr-initialise-db [_ [_]]
    (let [token (get-auth-cookie)]
      (-> dbase/default-db
          (assoc :authentication-token token
                 :view :home)))))

(register-handler
  :api-resposne->leave-types
  (fn hdlr-api-reposne->leave-types [db [_ leave-types]]
    (let [value (js->clj leave-types)]
      (assoc db :leave-types value))))

(register-handler
  :fetch-leave-types
  (fn hdlr-fetch-leave-types [db [_]]
    (utils/call-api :GET (routes/api-endpoint-for :leave-types) db
                    {:success-handler-key :api-resposne->leave-types
                     :response-keys       [:body :leave-types]})
    db))

(register-handler
  :fetching-department-employees-complete
  (fn hdlr-fetching [db [_]]
    (assoc db :fetching-department-employees? false)))

(register-handler
  :employee-add-new
  (fn hdlr-employee-add-new [db [_]]
    (dispatch [:clear-employee])
    (dispatch [:employee-add])
    (routes/set-route-token! [:employee-add])
    (assoc db :nav-bar :employees
              :view :employee-editor)))

(register-handler
  :clear-employee
  (fn hdlr-clear-employee [db [_]]
    (assoc db :employee nil)))

(register-handler
  :employee-to-edit
  (fn hdlr-employee-to-edit
    [db [_ id]]
    (dispatch [:clear-employee])
    (dispatch [:fetch-employee id])
    (assoc db :nav-bar :employees :view :employee-editor)))

(register-handler
  :set-active-view
  (fn hdlr-set-active-view [db [_ view-component-id]]
    (assoc db :view view-component-id)))

(register-handler
  :set-active-navbar
  (fn hdlr-set-active-navbar [db [_ nav-bar-id]]
    (assoc db :nav-bar nav-bar-id)))

(register-handler
  :api-response->department-employees
  (fn hdlr-api-response->department-employees [db [_ department-employees]]
    (let [value (js->clj department-employees)]
      (dispatch [:fetching-department-employees-complete])
      (assoc db :department-employees value))))

(register-handler
  :api-response->employees
  (fn hdlr-api-response->employees [db [_ employees]]
    (let [value (js->clj employees)]
      (assoc db :departments-with-employees value))))

(register-handler
  :api-response->departments
  (fn hdlr-api-response->departments [db [_ departments]]
    (let [value (js->clj departments)]
      (assoc db :departments value))))

(register-handler
  :api-response->department
  (fn hdlr-api-response->department [db [_ department]]
    (let [value (js->clj department)]
      (assoc db :department (clojure.set/rename-keys value {:id :department-id})))))

(register-handler
  :api-response->employee
  (fn hdlr-api-response->employee [db [_ employee]]
    (let [emp (js->clj employee)]
      (assoc db :employee (assoc emp :is-ready? true :not-found false)))))

(register-handler
  :employee-not-found
  (fn hndle-employee-not-found [db [_]]
    (assoc db :employee (assoc {} :is-ready? true :not-found true))))


(register-handler
  :fetch-department
  (fn hdlr-fetch-department [db [_ id]]
    (utils/call-api :GET (routes/api-endpoint-for :department-by-id :id id) db
                    {:success-handler-key :api-response->department
                     :response-keys       [:body]})
    db))

(register-handler
  :fetch-departments
  (fn hdlr-fetch-departments [db [_]]
    (utils/call-api :GET (routes/api-endpoint-for :departments) db
                    {:success-handler-key :api-response->departments
                     :response-keys       [:body :departments]})
    db))


(register-handler
  :fetch-department-employees
  (fn hdlr-fetch-department-employees [db [_ department-id]]
    (utils/call-api :GET (routes/api-endpoint-for :department-employees :id department-id) db
                    {:success-handler-key :api-response->department-employees
                     :response-keys       [:body :department-employees]})
    (assoc db :fetching-department-employees? true)))

(register-handler
  :fetch-departments-with-employees
  (fn hdlr-fetch-employees [db [_]]
    (utils/call-api :GET (routes/api-endpoint-for :employees) db
                    {:success-handler-key :api-response->employees
                     :response-keys       [:body]})
    db))

(register-handler
  :fetch-employee
  (fn hdlr-fetch-employee [db [_ id]]
    (utils/call-api :GET (routes/api-endpoint-for :employee-by-id :id id) db
                    {:success-handler-key :api-response->employee
                     :failure-handler-key :employee-not-found
                     :response-keys       [:body]})
    db))

;)

