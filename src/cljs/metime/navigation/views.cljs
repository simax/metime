(ns metime.navigation.views
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [metime.navigation.handlers]
            [metime.navigation.subs]
            [metime.employees.views :as ev]
            [metime.routes :as r]
            [re-com.core :refer [box throbber]]
            [re-frame.core :refer [register-handler
                                   path
                                   dispatch
                                   subscribe]]))

(def nav-bars [
               {:id :employees :text "Employees" :path (r/url-for :employees)}
               {:id :file-manager :text "File Manager" :path (r/url-for :file-manager)}
               {:id :calendar :text "Calendar" :path (r/url-for :calendar)}
               {:id :tables :text "Tables" :path (r/url-for :tables)}
               {:id :user :text "User" :path (r/url-for :user)}
               {:id :logout :text "Log out" :path (r/url-for :login)}])


(defn loader-component []
  [box
   :height "650px"
   :size "auto"
   :align :center
   :justify :center
   :child [:div (throbber :size :large :color "lime")]])

(defn login-component []
  (let [msg (subscribe [:authentication-failed])]
    (ev/login-form @msg)))

(defn home-component []
  [:div {:style {:height "500px"}}
   [:h1 "Home page"]
   [:div
    [:p "Welcome to time off 4 me!!!"]]])

(defn calendar-component []
  [:div {:style {:height "500px"}} [:h1 "Calendar page"]])

(defn tables-component []
  [:div {:style {:height "500px"}} [:h1 "Tables page"]])

(defn file-manager-component []
  [:div {:style {:height "500px"}} [:h1 "File manager page"]])

(defn user-component []
  [:div {:style {:height "500px"}} [:h1 "User page"]])

(defn not-found []
  [:div.well [:h1.text-center {:style {:color "red"}} "404 NOT FOUND !!!!!"]])

(defn employees-component []
  (dispatch [:fetch-department-employees "/departments"])
  (let [deps (subscribe [:departments])]
    (fn []
      (if-not (seq @deps)
        [loader-component]
        [ev/departments-container @deps]))))

(defn employee-component []
  (let [emp (subscribe [:employee])]
    (fn []
      (if (not (:is-ready? @emp))
        [loader-component]
        (if (:not-found @emp)
          [ev/employee-not-found]
          [ev/employee-maintenance-form @emp])))))

(defn nav-menu-item [item current-nav-bar-id]
  (let [route (:path item)
        display-text (:text item)]
    [:li {:class (if (= (:id item) current-nav-bar-id) "active" "")} [:a {:href route} display-text]]))

(defn nav-bar [current-nav-bar-id]
  [:div.navbar-nav.navbar-inverse.navbar-fixed-top
   [:div.container
    [:div.navbar-header
     [:button.navbar-toggle {:data-toggle "collapse" :data-target ".navbar-collapse"}
      [:span.icon-bar]
      [:span.icon-bar]
      [:span.icon-bar]
      ]
     [:a.navbar-brand {:href (r/url-for :home)} [:img {:src "/assets/img/logo30.png" :alt "MeTime Dashboard"}]]]
    [:div#nav-bar.navbar-collapse.collapse
     [:ul.nav.navbar-nav
      (for [nav-bar-item nav-bars]
        ^{:key (:id nav-bar-item)} [nav-menu-item nav-bar-item current-nav-bar-id])]
     ]]])

;; Use multimethods instead?
(defn switch-view [view-component]
  (case view-component
    :home home-component
    :login login-component
    :tables tables-component
    :calendar calendar-component
    :file-manager file-manager-component
    :user user-component
    :employees employees-component
    :edit-employee employee-component
    :not-found not-found))

(defn main-panel []
  (let [view-component-id (subscribe [:view-component])
        current-nav-bar (subscribe [:current-nav-bar])]
    (fn []
      [box
       :align :center
       :justify :center
       :size "auto"
       :child [:div
               ;; Top nav bar
               (when-not (= @view-component-id :login) [nav-bar @current-nav-bar])
               ;; Switch view
               [(switch-view @view-component-id)]
               ]])))

(defn initial-panel []
  (let [ready? (subscribe [:initialised?])]
    (fn []
      (if-not @ready?
        [loader-component]
        [main-panel]))))
