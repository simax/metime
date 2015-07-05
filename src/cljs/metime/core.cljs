(ns ^:figwheel-always metime.core
  (:require-macros [cljs.core.async.macros :refer [go alt!]]
                   [reagent.ratom :refer [reaction]])
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [secretary.core :as secretary :refer-macros [defroute]]
            [metime.navigation.views :as nv]
            [metime.navigation.subs]
            [metime.navigation.handlers]
            [reagent.core :as reagent :refer [atom]]
            [re-frame.core :refer [register-handler
                                   path
                                   dispatch
                                   dispatch-sync
                                   subscribe]]
            [metime.utils :as utils])
  (:import goog.History))


(enable-console-print!)
(secretary/set-config! :prefix "#")

(defroute tables-route "/tables" []
          (dispatch [:switch-route :tables :tables]))

(defroute calendar-route "/calendar" []
          (dispatch [:switch-route :calendar :calendar]))

(defroute file-manager-route "/file-manager" []
          (dispatch [:switch-route :file-manager :file-manager]))

(defroute user-route "/user" []
          (dispatch [:switch-route :user :user]))

(defroute login-route "/login" []
          (dispatch [:switch-route :login :login]))

(defroute employees-route "/employees" []
          (dispatch [:switch-route :employees :employees]))

(defroute root-route "/" []
          (utils/set-hash! "#/employees")
          (dispatch [:switch-route :employees :employees]))

(defroute employee-route "/employee/:id" [id]
          (dispatch [:employee-route-switcher :employees :employee id]))

(defroute employee-add-route "/employees/add" []
          (dispatch [:employee-route-switcher :employees :employee 0]))

(defroute "*" []
          (dispatch [:switch-route :employees :not-found]))

(defn hook-browser-navigation! []
  "Routing history, back button etc."
  (let [h (History.)
        f (fn [he] ;; goog.History.Event
            (let [token (.-token he)]
              (if (seq token)
                (secretary/dispatch! token)
                )))]

    (events/listen h EventType/NAVIGATE f)
    (doto h (.setEnabled true))))

(defn mount-root []
  ;; Main app component
  (reagent/render [nv/top-panel] (js/document.getElementById "app-container")))

(defn ^:export init []
  (dispatch [:initialise-db])
  (hook-browser-navigation!)
  (mount-root))

