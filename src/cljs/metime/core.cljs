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

(def history (History.))

(def app-state
  (atom {:view "#employees"
         :top-nav-bar [
                       {:path "#employees"     :text "Employees"     :active true}
                       {:path "#file-manager"  :text "File Manager"}
                       {:path "#calendar"      :text "Calendar"}
                       {:path "#tables"        :text "Tables"}
                       {:path "#login"         :text "Login"}
                       {:path "#user"          :text "User"}
                      ]}))

(secretary/set-config! :prefix "#")

(defroute "/" []
  (.setToken history "employees"))

(defroute "/employees" []
  (swap! app-state #(assoc %1 :view "#employees")))

(defroute "/employee/:id" [id]
  (swap! app-state #(assoc %1 :view "#employee" :id id)))

(defroute "/tables" []
  (swap! app-state #(assoc %1 :view "#tables")))

(defroute "/calendar" []
  (println "Switched to calendar")
  (swap! app-state #(assoc %1 :view "#calendar")))

(defroute "/file-manager" []
  (swap! app-state #(assoc %1 :view "#file-manager")))

(defroute "/user" []
  (swap! app-state #(assoc %1 :view "#user")))

(defroute "/login" []
  (swap! app-state #(assoc %1 :view "#login")))

(defroute "*" []
  (swap! app-state #(assoc %1 :view "#not-found")))

(defn main-page [app]

  ;; Top nav bar
  [:div
   [nav/top-nav-bar @app]

  ;; Page contents
 (condp = (:view @app)

   "#employee"
   [:div [ec/employee app
          {:url "http://localhost:3030/api/employee/18"}]]

   "#calendar"
   [:div [:h1 {:style {:height "500px"}} "Calendar page"]]

   "#tables"
   [:div {:style {:height "500px"}} [:h1 "Tables page"]]

   "#file-manager"
   [:div {:style {:height "500px"}} [:h1 "File manager page"]]

   "#user"
   [:div {:style {:height "500px"}} [:h1 "User page"]]

   "#login"
   [:div {:style {:height "500px"}} [:h1 "Login page"]]

   "#employees"
   [:div
    [ec/departments-container app
     {:url "http://localhost:3030/api/departments"}]]

   "not-found"
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
                  ;;(secretary/dispatch! token)
                  (refresh-navigation app-state token)
                  ))))]
    (events/listen h EventType/NAVIGATE f)
    (doto h (.setEnabled true)))

  ;; Main app component
  (reagent/render [main-page app-state] (. js/document (getElementById "app-container"))))

