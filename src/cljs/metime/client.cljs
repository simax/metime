(ns metime.client
  (:require
    [reagent.core :as reagent]
    [pushy.core :as pushy]
    [metime.navigation.views :as nv]
    [re-frame.core :refer [register-handler
                           path
                           dispatch
                           subscribe]]
    [metime.routes :as r]
    [devtools.core :as dt]))

(enable-console-print!)

(dt/install!)

(defn mount-root []
  (reagent/render [nv/initial-panel] (.getElementById js/document "app-container")))

(defn ^:export main []
  (r/app-routes)
  (dispatch [:initialise-db])

  (println (str "current url: " (pushy/get-token r/history)))
  (println (r/parse-url (pushy/get-token r/history)))

  (mount-root))

