(ns metime.client
  (:refer-clojure :exclude [run!])
  (:require
    [reagent.core :as reagent]
    [metime.navigation.handlers]
    [metime.navigation.views :as nv]
    [reagent.core :as reagent :refer [atom]]
    [re-frame.core :refer [register-handler
                           path
                           dispatch
                           subscribe]]
    [metime.routes :as r]
    [devtools.core :as dt]))


(dt/install!)

(defn mount-root []
  (reagent/render [nv/initial-panel] (js/document.getElementById "app-container")))

(defn ^:export main []
  (dispatch [:initialise-db])
  (r/app-routes)
  (mount-root))

