(ns metime.components.nav-bar
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:import goog.History
           goog.History.EventType))

(enable-console-print!)

(defn toggle-active-status [token item]
  (if (= (:path item) token)
    (assoc item :active true)
    (assoc item :active false)))

(defn update-nav-bar [db view-component top-level-menu-text]
  (let [updated-view (assoc db :view view-component)]
    (update-in updated-view [:nav-bar] #(map (partial toggle-active-status top-level-menu-text) %))))

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
