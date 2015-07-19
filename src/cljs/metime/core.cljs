(ns ^:figwheel-always metime.core
  (:refer-clojure :exclude [run!])
  (:require-macros [cljs.core.async.macros :refer [go alt!]]
                   [reagent.ratom :refer [reaction]])
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [secretary.core :as secretary :refer-macros [defroute]]
            [metime.routes]
            [metime.navigation.views :as nv]
            [metime.navigation.subs]
            [metime.navigation.handlers]
            [reagent.core :as reagent :refer [atom]]
            [re-frame.core :refer [register-handler
                                   path
                                   dispatch
                                   dispatch-sync
                                   subscribe]]
            [metime.utils])
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

(defn mount-root []
  ;; Main app component
  (reagent/render [nv/top-panel] (js/document.getElementById "app-container")))

(defn ^:export init []
  (dispatch [:initialise-db])
  (hook-browser-navigation!)
  (mount-root))

