(ns metime.navigation.views
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [metime.navigation.handlers]
            [metime.navigation.subs]
            [re-frame.core :refer [register-handler
                                   path
                                   dispatch
                                   subscribe]])
  (:import goog.History
           goog.History.EventType))

(enable-console-print!)


(defn nav-menu-item [item]
  (let [route (:path item)]
    [:li {:class (if (= (:active item) true) "active" "")} [:a {:href (:path item)} (:text item)]]))

(defn nav-bar [{:keys [nav-bar]}]
  [:div.navbar-nav.navbar-inverse.navbar-fixed-top
   [:div.container
    [:div.navbar-header
     [:button.navbar-toggle {:data-toggle "collapse" :data-target ".navbar-collapse"}
      [:span.icon-bar]
      [:span.icon-bar]
      [:span.icon-bar]
      ]
     [:a.navbar-brand {:href="/#employees"} [:img {:src "assets/img/logo30.png" :alt "MeTime Dashboard"}]]]
    [:div#nav-bar.navbar-collapse.collapse
     [:ul.nav.navbar-nav
      (for [nav-bar-item nav-bar]
        ^{:key (:path nav-bar-item)} [nav-menu-item nav-bar-item])]]]])

(defn loader-component []
  [:div.loader-container [:img {:src "assets/img/loader.gif"}]])

(defn calendar-component []
  [:div {:style {:height "500px"}} [:h1 "Calendar page"]])

(defn tables-component []
  [:div {:style {:height "500px"}} [:h1 "Tables page"]])

(defn file-manager-component []
  [:div {:style {:height "500px"}} [:h1 "File manager page"]])

(defn user-component []
  [:div {:style {:height "500px"}} [:h1 "User page"]])

(defn login-component []
  [:div {:style {:height "500px"}} [:h1 "Login page"]])

(defn not-found []
  [:div.well [:h1.text-center {:style {:color "red"}} "404 NOT FOUND !!!!!"]])

(defn main-panel []
  (let [db (subscribe [:db-changed?])]
    (fn []
      [:div
       ;; Top nav bar
       [nav-bar @db]
       ;; Components
       [(:view @db)]
       ])))

(defn top-panel []
  (let [ready? (subscribe [:initialised?])]
    (fn []
      (if-not @ready?
        [loader-component]
        [main-panel]))))
