(ns metime.client
  (:require
    [reagent.core :as reagent]
    [pushy.core :as pushy]
    [metime.navigation.views :as nv]
    [re-frame.core :refer [register-handler
                           path
                           dispatch
                           subscribe]]
    [metime.routes :as routes]
    [devtools.core :as dt]))

(enable-console-print!)

(dt/install!)

(defmulti  route-switcher (fn [route-info] (:handler route-info)))
(defmethod route-switcher :home [_] (dispatch [:set-active-view :home]))
(defmethod route-switcher :login [_] (dispatch [:set-active-view :login]))
(defmethod route-switcher :tables [_] (dispatch [:set-active-view :tables]))
(defmethod route-switcher :calendar [_] (dispatch [:set-active-view :calendar]))
(defmethod route-switcher :file-manager [_] (dispatch [:set-active-view :file-manager]))
(defmethod route-switcher :user [_] (dispatch [:set-active-view :user]))
(defmethod route-switcher :employees [_] (dispatch [:set-active-view :employees]))
(defmethod route-switcher :employee-add [] (dispatch [:set-active-view :employee-add]))
(defmethod route-switcher :employee-editor [route-info] (dispatch [:employee-to-edit (get-in route-info [:route-params :id])]))
(defmethod route-switcher :not-found [_] (dispatch [:set-active-view :not-found]))

(defn mount-root []
  (reagent/render [nv/initial-panel] (.getElementById js/document "app-container")))

(defn ^:export main []
  (routes/start-routing)
  (dispatch [:initialise-db])
  (route-switcher (routes/parse-url (pushy/get-token routes/history)))
  (mount-root))

