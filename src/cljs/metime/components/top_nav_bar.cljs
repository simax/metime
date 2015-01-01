(ns metime.components.top-nav-bar
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [sablono.core :as html :refer-macros [html]]
            [cljs-hash.md5 :as hashgen]
            [cljs-hash.goog :as gh]
            [secretary.core :as secretary :refer-macros [defroute]])
  (:import goog.History
           goog.History.EventType))

(enable-console-print!)

(declare app-state)

(def history (History.))

(defn toggle-active-status [item]
  (let [token (str "#" (.getToken history))]
    (if (= (:path item) token) (assoc item :active true) (assoc item :active false))))

(defn update-top-nav-bar [app-state-map]
  (update-in app-state-map [:top-nav-bar] #(map toggle-active-status %)))


(defcomponent nav-menu-item [item]
  (display-name [_]
                "nav-menu-item")

  (render [_]
          (html
           [:li {:class (if (= (:active item) true) "active" "")} [:a {:href (:path item)} (:text item)]])))

(defcomponent top-nav-bar [{:keys [top-nav-bar]}]
  (display-name [_]
                "top-nav-bar")

  (render [_]
          (html
           [:ul.nav.navbar-nav
            (om/build-all nav-menu-item top-nav-bar {:key :path})])))

