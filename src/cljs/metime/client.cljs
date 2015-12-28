(ns metime.client
  (:refer-clojure :exclude [run!])
  (:require-macros [cljs.core.async.macros :refer [go alt!]]
                   [reagent.ratom :refer [reaction]])
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [secretary.core :as secretary :refer-macros [defroute]]
            [metime.navigation.views :as nv]
            [reagent.core :as reagent :refer [atom]]
            [re-frame.core :refer [register-handler
                                   path
                                   dispatch
                                   dispatch-sync
                                   subscribe]]
            [devtools.core :as dt])
  (:import goog.History))


(dt/install!)

(secretary/set-config! :prefix "#")

(defn hook-browser-navigation! []
  "Routing history, back button etc."
  (let [h (History.)
        f (fn [he] ;; goog.History.Event
            (let [token (.-token he)]
              (when (seq token)
                (secretary/dispatch! token))))]

    (events/listen h EventType/NAVIGATE f)
    (doto h (.setEnabled true))))

(defn mount-root []
  ;; Main app component
  (reagent/render [nv/initial-panel] (js/document.getElementById "app-container")))

(defn ^:export main []
  (dispatch [:initialise-db])
  (hook-browser-navigation!)
  (mount-root))

