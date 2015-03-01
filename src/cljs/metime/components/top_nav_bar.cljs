(ns metime.components.top-nav-bar
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [cljs-hash.md5 :as hashgen]
            [cljs-hash.goog :as gh]
            [secretary.core :as secretary :refer-macros [defroute]]
            [reagent.core :as reagent :refer [atom]])
  (:import goog.History
           goog.History.EventType))

(enable-console-print!)

(defn toggle-active-status [token item]
  (if (= (:path item) token)
    (assoc item :active true)
    (assoc item :active false)))

(defn update-top-nav-bar [app-db token]
  (let [prefixed-token (str "#" token)
        updated-view (assoc app-db :view prefixed-token)]
    (update-in updated-view [:top-nav-bar] #(map (partial toggle-active-status prefixed-token) %))))

(defn nav-menu-item [item]
  (let [route (:path item)]
    ;;(js/console.log (:path item))
    [:li {:class (if (= (:active item) true) "active" "")} [:a {:href (:path item)} (:text item)]]))

(defn top-nav-bar [{:keys [top-nav-bar]}]
  [:div.navbar-nav.navbar-inverse.navbar-fixed-top
   [:div.container
    [:div.navbar-header
     [:button.navbar-toggle {:data-toggle "collapse" :data-target ".navbar-collapse"}
      [:span.icon-bar]
      [:span.icon-bar]
      [:span.icon-bar]
      ]
     [:a.navbar-brand {:href="/#employees"} [:img {:src "assets/img/logo30.png" :alt "MeTime Dashboard"}]]]
    [:div#top-nav-bar.navbar-collapse.collapse
     [:ul.nav.navbar-nav
      (for [nav-bar top-nav-bar]
        ^{:key (:path nav-bar)} [nav-menu-item nav-bar])]]]])
