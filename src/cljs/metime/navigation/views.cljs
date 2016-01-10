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
               {:id :logout :text "Log out" :path (r/url-for :login)}
               {:id :test :text "Test" :path (r/url-for :test)}
               ])


(defn loader-component []
  [box
   :height "650px"
   :size "auto"
   :align :center
   :justify :center
   :child [:div (throbber :size :large :color "lime")]])

(defn view-login []
  (let [msg (subscribe [:authentication-failed])]
    (ev/login-form @msg)))

(defn view-home []
  (dispatch [:set-active-navbar :home])
  ;(dispatch [:set-active-route [:home]])
  [:div {:style {:height "500px"}}
   [:h1 "Home page"]
   [:div
    [:p "Welcome to time off 4 me!!!"]]])

(defn view-calendar []
  (dispatch [:set-active-navbar :calendar])
  ;(dispatch [:set-active-route [:calendar]])
  [:div {:style {:height "500px"}} [:h1 "Calendar page"]])

(defn view-tables []
  (dispatch [:set-active-navbar :tables])
  [:div {:style {:height "500px"}} [:h1 "Tables page"]])

(defn view-file-manager []
  (dispatch [:set-active-navbar :file-manager])
  [:div {:style {:height "500px"}} [:h1 "File manager page"]])

(defn view-user []
  (dispatch [:set-active-navbar :user])
  [:div {:style {:height "500px"}} [:h1 "User page"]])

(defn view-test []
  (dispatch [:set-active-navbar :test])
  [:div {:style {:height "500px"}} [:h1 "Test page"]])

(defn view-test-level-2 []
  (dispatch [:set-active-navbar :test])
  [:div {:style {:height "500px"}} [:h1 "Test page - LEVEL 2"]])

(defn view-not-found []
  [:div.well [:h1.text-center {:style {:color "red"}} "404 NOT FOUND !!!!!"]])

(defn view-employees []
  (dispatch [:set-active-navbar :employees])
  (dispatch [:fetch-departments-and-employees "/departments-and-employees"])
  (let [departments-and-employees (subscribe [:departments-and-employees])]
    (fn []
      (if-not (seq @departments-and-employees)
        [loader-component]
        [ev/departments-container @departments-and-employees]))))

(defn view-employee []
  (dispatch [:fetch-departments-only "/departments-only"])
  (let [emp (subscribe [:employee])
        departments (subscribe [:departments])]
    (fn []
      (if (not (or (:is-ready? @emp) (some? @departments))) ; (not (:is-ready? @emp))
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


(defmulti set-active-view identity)
(defmethod set-active-view :home [] view-home)
(defmethod set-active-view :login [] view-login)
(defmethod set-active-view :tables [] view-tables)
(defmethod set-active-view :calendar [] view-calendar)
(defmethod set-active-view :file-manager [] view-file-manager)
(defmethod set-active-view :user [] view-user)
(defmethod set-active-view :employees [] view-employees)
(defmethod set-active-view :employee-add [] view-employee)
(defmethod set-active-view :employee-editor [] view-employee)
(defmethod set-active-view :test [] view-test)
(defmethod set-active-view :test-level-2 [] view-test-level-2)
(defmethod set-active-view :not-found [] view-not-found)

(defn set-active-navbar [current-nav-bar current-view]
  (when-not (= current-view :login) [nav-bar current-nav-bar]))

(defn main-panel []
  (let [current-view (subscribe [:current-view])
        current-nav-bar (subscribe [:current-nav-bar])]
    (fn []
      [box
       :align :center
       :justify :center
       :size "auto"
       :child [:div
               ;; Display active navbar
               (set-active-navbar @current-nav-bar @current-view)
               ;; Display active view
               [(set-active-view @current-view)]
               ]])))

(defn initial-panel []
  (let [ready? (subscribe [:initialised?])]
    (fn []
      (if-not @ready?
        [loader-component]
        [main-panel]))))
