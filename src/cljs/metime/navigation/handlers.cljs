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
   :employee-to-edit
   (fn hdlr-employee-edit-handler
     [db [_ id]]
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
   :process-departments-response
   (fn hdlr-process-departments-response [db [_ department-employees]]
     (let [value (js->clj department-employees)]
       (assoc db :departments-and-employees value))))

 (register-handler
   :process-departments-only-response
   (fn hdlr-process-departments-only-response [db [_ departments]]
     (let [value (js->clj departments)]
       (assoc db :departments value))))

 (register-handler
   :process-employee-response
   (fn hdlr-process-employee-response [db [_ employee]]
     (let [emp (js->clj employee)]
       (if (nil? (:id emp))
         (assoc db :employee (assoc {} :is-ready? true :not-found true))
         (assoc db :employee (assoc emp :is-ready? true :not-found false)))
       )))

 (register-handler
   :fetch-departments-only
   (fn hdlr-fetch-departments-only [db [_]]
     (utils/call-api :GET (routes/api-endpoint-for :departments-only) (:authentication-token db)
                     {:valid-token-handler   :process-departments-only-response
                      :invalid-token-handler :log-out
                      :response-keys         [:body :departments]})
     db))

 (register-handler
   :fetch-departments-and-employees
   (fn hdlr-fetch-departments-and-employees [db [_]]
     (utils/call-api :GET (routes/api-endpoint-for :departments-and-employees) (:authentication-token db)
                     {:valid-token-handler   :process-departments-response
                      :invalid-token-handler :log-out
                      :response-keys         [:body :departments]})
     db))

 (register-handler
   :fetch-employee
   (fn hdlr-fetch-employee [db [_ id]]
     (utils/call-api :GET (routes/api-endpoint-for :employee-by-id :id id) (:authentication-token db)
                     {:valid-token-handler   :process-employee-response
                      :invalid-token-handler :log-out
                      :response-keys         [:body]})
     db))

;)

