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

(def app-state
  (atom {:top-nav-bar [
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
  (js/console.log "2. Here ")
  ;;(swap! app-state #(assoc %1 :view "employees"))
  )

(defroute employee-route "/employee/:id" [id]
  (swap! app-state #(assoc %1 :view "employee" :id id)))

(defroute tables-route "/tables" []
  (swap! app-state #(assoc %1 :view "tables")))

(defroute calendar-route "/calendar" []
  (swap! app-state #(assoc %1 :view "calendar")))

(defroute file-manager-route "/file-manager" []
  (swap! app-state #(assoc %1 :view "file-manager")))

(defroute user-route "/user" []
  (swap! app-state #(assoc %1 :view "user")))

(defroute login-route "/login" []
  (swap! app-state #(assoc %1 :view "login")))

(defroute "*" []
  (swap! app-state #(assoc %1 :view "not-found")))

(defn main-page [app]

  ;;(when (= "not-found" (:view @app)) (secretary/dispatch! (employees-route)))

  ;;(js/console.log (str "view:-->>> " (:view @app)))

  ;; Top nav bar
  [:div
   [nav/top-nav-bar @app]

   ;; Page contents
   (condp = (get-current-location);;(:view @app)

     "/employee"
     [:div [ec/employee app
            {:url "http://localhost:3030/api/employee/18"}]]

     "/calendar"
     [:div [:h1 {:style {:height "500px"}} "Calendar page"]]

     "/tables"
     [:div {:style {:height "500px"}} [:h1 "Tables page"]]

     "/file-manager"
     [:div {:style {:height "500px"}} [:h1 "File manager page"]]

     "/user"
     [:div {:style {:height "500px"}} [:h1 "User page"]]

     "/login"
     [:div {:style {:height "500px"}} [:h1 "Login page"]]

     "/employees"
     [:div
      [ec/departments-container app
       {:url "http://localhost:3030/api/departments"}]]

     ;; not-found
     [:div {:style {:height "500px"}} [:h1 {:style {:color "red"}} "404 NOT FOUND !!!!!"]])
   ])


(defn refresh-navigation [app-state token]
  (swap! app-state nav/update-top-nav-bar token))

(defn main []
  ;; History
  (let [h (History.)
        f (fn [he] ;; goog.History.Event
            (let [token (.-token he)]
              (if (seq token)
                (do
                  (js/console.log "1. Here ")
                  (secretary/dispatch! token)
                  (js/console.log "99. Here Now !!!")
                  (refresh-navigation app-state token)
                  ))))]

    (events/listen h EventType/NAVIGATE f)
    (doto h (.setEnabled true))

  ;; Main app component
    (js/console.log "Initially Here !!!")
    (reagent/render [main-page app-state] (. js/document (getElementById "app-container")))))

