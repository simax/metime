(ns metime.core
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [cljs-http.client :as http]
            [cljs-hash.md5 :as hashgen]
            [cljs-hash.goog :as gh]
            [metime.components.top-nav-bar :as nav]
            [secretary.core :as secretary :refer-macros [defroute]]
            [metime.components.employee :as ec]
            [reagent.core :as reagent :refer [atom]])
  (:import goog.History))

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
  [:div
   [ec/departments-container app
    {:url "http://localhost:3030/api/departments"}]])

(defn employee-component []
     [:div [ec/employee app
            {:url "http://localhost:3030/api/employee/18"}]])

;; Note: Components need to be defined before the app-state atom
;;       because they are refered to and probably evaluated
;;       when the atom is defined.

(def app-state
  (atom {:view employees-component
         :top-nav-bar [
                       {:path "#/employees"     :text "Employees"     :active true}
                       {:path "#/file-manager"  :text "File Manager"}
                       {:path "#/calendar"      :text "Calendar"}
                       {:path "#/tables"        :text "Tables"}
                       {:path "#/login"         :text "Login"}
                       {:path "#/user"          :text "User"}
                       ]}))

(secretary/set-config! :prefix "#")

(defroute root-route "/" []
  (swap! app-state #(assoc %1 :view "employees")))

(defroute employees-route "/employees" []
  (swap! app-state #(assoc %1 :view employees-component)))

(defroute employee-route "/employee/:id" [id]
  (swap! app-state #(assoc %1 :view employee-component :id id)))

(defroute tables-route "/tables" []
  (swap! app-state #(assoc %1 :view tables-component)))

(defroute calendar-route "/calendar" []
  (swap! app-state #(assoc %1 :view calendar-component)))

(defroute file-manager-route "/file-manager" []
  (swap! app-state #(assoc %1 :view file-manager-component)))

(defroute user-route "/user" []
  (swap! app-state #(assoc %1 :view user-component)))

(defroute login-route "/login" []
  (swap! app-state #(assoc %1 :view login-component)))

(defroute "*" []
  (swap! app-state #(assoc %1 :view not-found)))

(defn not-found []
  [:div {:style {:height "500px"}} [:h1 {:style {:color "red"}} "404 NOT FOUND !!!!!"]])

(defn main-page [app]

    ;; Top nav bar
    [:div
     [nav/top-nav-bar @app]
     ;; Component
     [(@app :view)]])



(defn refresh-navigation [app-state token]
  (set-hash! token)
  (swap! app-state nav/update-top-nav-bar token)
  (secretary/dispatch! token))

(defn main []

  ;; Main app component
  (reagent/render [main-page app-state] (. js/document (getElementById "app-container")))

  ;; Routing history
  (let [h (History.)
        f (fn [he] ;; goog.History.Event
            (let [token (.-token he)]
              (if (seq token)
                (refresh-navigation app-state token)
                (refresh-navigation app-state "/employees"))
              ))]

    (events/listen h EventType/NAVIGATE f)
    (doto h (.setEnabled true))))
