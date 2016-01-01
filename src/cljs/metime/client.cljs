(ns metime.client
  (:require
    [reagent.core :as reagent]
    [metime.navigation.views :as nv]
    [re-frame.core :refer [register-handler
                           path
                           dispatch
                           subscribe]]
    [metime.routes :as r]
    [devtools.core :as dt]))


(dt/install!)

(defn mount-root []
  (reagent/render [nv/initial-panel] (.getElementById js/document "app-container")))

(defn ^:export main []
  (dispatch [:initialise-db])
  (r/app-routes)
  (mount-root))

