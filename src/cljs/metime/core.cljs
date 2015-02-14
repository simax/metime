(ns metime.core
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [cljs-http.client :as http]
            [sablono.core :as html :refer-macros [html]]
            [cljs-hash.md5 :as hashgen]
            [cljs-hash.goog :as gh]
            [metime.components.top-nav-bar :as nav]
            [secretary.core :as secretary :refer-macros [defroute]]
            [metime.components.employee :as ec]
            [reagent.core :as reagent :refer [atom]])
  (:import goog.History
           goog.History.EventType))

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

(defroute "/" []
  (.setToken (History.) "employees"))

(defroute "/employees" []
  (swap! app-state #(assoc %1 :view "#employees")))

(defroute "/employees/:id" [id]
  (println "Got here!")
  (swap! app-state #(assoc %1 :view "#employee" :id id)))

(defroute "/tables" []
  (swap! app-state #(assoc %1 :view "#tables")))

(defroute "/calendar" []
  (swap! app-state #(assoc %1 :view "#calendar")))

(defroute "/file-manager" []
  (swap! app-state #(assoc %1 :view "#file-manager")))

(defroute "/user" []
  (swap! app-state #(assoc %1 :view "#user")))

(defroute "/login" []
  (swap! app-state #(assoc %1 :view "#login")))

(defroute "*" []
  (swap! app-state #(assoc %1 :view "#not-found")))


(defcomponent om-app [app owner]
  (display-name [_]
                "app")
  (render [_]
          (html
           (condp = (:view app)

             "#employees"
               [:div
                (ec/->departments-container app
                                         {:opts {:url "http://localhost:3030/api/departments"
                                                 :poll-interval 2000}})]
             "#employee"
               [:div (ec/->employee app
                                      {:opts {:url "http://localhost:3030/api/employee/"
                                              :poll-interval 2000}})]

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

             "#not-found"
               [:div {:style {:height "500px"}} [:h1 {:style {:color "red"}} "404 NOT FOUND !!!!!"]]))))


(defn refresh-navigation []
  (println (str "path: " (:top-nav-bar @app-state)))
  (swap! app-state nav/update-top-nav-bar))

(defn on-navigate [event]
  (refresh-navigation)
  (secretary/dispatch! (.-token event)))

(defn main []
  (doto history
    (goog.events/listen EventType/NAVIGATE on-navigate)
    (.setEnabled true))

  ;;(secretary/set-config! :prefix "#")

  ;; Top nav bar
  (reagent/render [nav/top-nav-bar @app-state] (. js/document (getElementById "top-nav-bar"))))

  ;; Root component
  ;;(om/root om-app app-state {:target (. js/document (getElementById "app-container"))}))


