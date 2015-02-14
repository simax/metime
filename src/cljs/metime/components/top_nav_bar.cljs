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
            [secretary.core :as secretary :refer-macros [defroute]]
            [reagent.core :as reagent :refer [atom]])
  (:import goog.History
           goog.History.EventType))

(enable-console-print!)

(def history (History.))

(defn toggle-active-status [item]
  (let [token (str "#" (.getToken history))]
    (if (= (:path item) token) (assoc item :active true) (assoc item :active false))))

(defn update-top-nav-bar [app-state-map]
  (update-in app-state-map [:top-nav-bar] #(map toggle-active-status %)))

(defn nav-menu-item [item]
  [:li {:class (if (= (:active item) true) "active" "")} [:a {:href (:path item)} (:text item)]])

(defn top-nav-bar [{:keys [top-nav-bar]}]
  [:ul.nav.navbar-nav
   (for [nav-bar top-nav-bar]
     (nav-menu-item nav-bar))])
