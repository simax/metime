(ns metime.core
  (:require-macros [cljs.core.async.macros :refer [go alt!]]
                   [reagent.ratom :refer [reaction]])
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [secretary.core :as secretary :refer-macros [defroute]]
            [metime.navigation.views :as nav-view]
            [metime.navigation.subs]
            [metime.navigation.handlers]
            [reagent.core :as reagent :refer [atom]]
            [re-frame.core :refer [register-handler
                                   path
                                   dispatch
                                   subscribe]])
  (:import goog.History))




(enable-console-print!)
(secretary/set-config! :prefix "#")

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

(defn main []
  (dispatch [:initialise-db])
  ;; Main app component
  (reagent/render [top-panel] (js/document.getElementById "app-container"))
  (hook-browser-navigation!))