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
            [re-frame.core :refer [register-handler
                                   path
                                   register-sub
                                   dispatch
                                   subscribe]])
  (:import goog.History))

(declare app-db)
(enable-console-print!)

(defn set-hash! [loc]
  "Set the hash portion of the url in the address bar.
  e.g. (set-hash! '/dip') => http://localhost:3000/#/dip"
  (set! (.-hash js/window.location) loc))

(defn get-current-location []
  "Get the hash portion of the url in the address bar."
  (subs (.-hash js/window.location) 1))

(defn calendar-component []
  [:div [:h1 {:style {:height "500px"}} "Calendar page"]])

(defn tables-component []
  [:div {:style {:height "500px"}} [:h1 "Tables page"]])

(defn file-manager-component []
  [:div {:style {:height "500px"}} [:h1 "File manager page"]])

(defn user-component []
  [:div {:style {:height "500px"}} [:h1 "User page"]])

(defn login-component []
  [:div {:style {:height "500px"}} [:h1 "Login page"]])

(defn employees-component []
  [:div ;; Put the url inside the component subscribe function
   [ec/departments-container {:url "http://localhost:3030/api/departments"}]])

(defn employee-component []
    [:div
     [ec/employee app-db {:url (:url @app-db)}]])

(defn not-found []
  [:div {:style {:height "500px"}} [:h1 {:style {:color "red"}} "404 NOT FOUND !!!!!"]])


(secretary/set-config! :prefix "#")

(defroute root-route "/" []
  (dispatch [:switch-route employees-component]))

(defroute employees-route "/employees" []
  (dispatch [:switch-route employees-component]))

(defroute employee-route "/employee/:id" [id]
  (let [url (str "http://localhost:3030/api/employee/" id)]
    (js/console.log (str "Reached employee-route with id: " id))
    (go
     (let [emp (<! (ec/fetch-employee url))]
       (if (= emp "not found")
         (swap! app-db #(dissoc % :employee))
         (swap! app-db #(assoc % :view employee-component :employee emp)))))))

(defroute tables-route "/tables" []
  (dispatch [:switch-route tables-component]))

(defroute calendar-route "/calendar" []
  (js/console.log "Calendar route!!!")
  (dispatch [:switch-route calendar-component]))

(defroute file-manager-route "/file-manager" []
  (dispatch [:switch-route file-manager-component]))

(defroute user-route "/user" []
  (dispatch [:switch-route user-component]))

(defroute login-route "/login" []
  (dispatch [:switch-route login-component]))

(defroute "*" []
  (dispatch [:switch-route not-found]))

(register-handler
 :switch-route
 (fn [db [_ view-component]]
   ;; call nav/update-top-nav-bar
   (assoc db :view view-component)))

(register-handler
 :initialize-db
 (fn
   [__]
   {:view employees-component
    :top-nav-bar [
                  {:path (employees-route)     :text "Employees"     :active true}
                  {:path (file-manager-route)  :text "File Manager"}
                  {:path (calendar-route)      :text "Calendar"}
                  {:path (tables-route)        :text "Tables"}
                  {:path (login-route)         :text "Login"}
                  {:path (user-route)          :text "User"}
                  ]}))

(register-sub
 :db
 (fn [db _]
   (reaction @db)))

(register-sub       ;; we can check if there is data
  :initialised?     ;; usage (subscribe [:initialised?])
  (fn  [db _]
    (reaction (not (empty? @db)))))   ;; do we have data


(defn main-panel []
  (let [db (subscribe [:db])]
    (fn []
      ;; Top nav bar
      [:div
       [nav/top-nav-bar @db]
       ;; Component
       [(:view @db) (:params @db)]
      ])))

 (defn top-panel []
   (let [ready?  (subscribe [:initialised?])]
    (fn []
      (if-not @ready?               ;; do we have good data?
        [:h1 {:style {:text-align "center" :color "red"}} "Initialising ..."]   ;; tell them we are working on it
        [main-panel]))))            ;; all good, render this component

(defn main []
  (dispatch [:initialize-db])
  ;; Main app component
  (reagent/render [top-panel] (js/document.getElementById "app-container"))

  ;; Routing history
  (let [h (History.)
        f (fn [he] ;; goog.History.Event
            (let [token (.-token he)]
              (if (seq token)
                (secretary/dispatch! token)
                (secretary/dispatch! (employees-component))
              )))]

    (events/listen h EventType/NAVIGATE f)
    (doto h (.setEnabled true))))
