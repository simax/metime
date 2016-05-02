(ns metime.navigation.views
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [metime.navigation.handlers]
            [metime.navigation.subs]
            [metime.employees.views :as ev]
            [metime.calendar.views :as cv]
            [metime.leave-types.subs]
            [metime.leave-types.views :as lvt]
            [metime.routes :as routes]
            [metime.common.views :as common-components]
            [re-com.core :refer [box throbber]]
            [re-frame.core :refer [register-handler
                                   path
                                   dispatch
                                   subscribe]]
            [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame-tracer.core :refer [tracer]]))

;(trace-forms
;  {:tracer (tracer :color "indigo")}

(def nav-bars [
               {:id :employees :text "Employees" :path (routes/site-url-for :employees)}
               {:id :calendar :text "Calendar" :path (routes/site-url-for :calendar)}
               {:id :leave-types :text "Leave types" :path (routes/site-url-for :leave-types)}
               {:id :user :text "User" :path (routes/site-url-for :user)}
               {:id :logout :text "Log out" :path (routes/site-url-for :login)}])


(defn view-leave-types []
  (dispatch [:set-active-navbar :leave-types])
  (dispatch [:fetch-leave-types])
  (let [leave-types (subscribe [:leave-types])]
    (fn []
      (if-not (seq @leave-types)
        [common-components/loader-component]
        [lvt/leave-types-list-layout @leave-types]))))


(defn view-login []
  (let [msg (subscribe [:authentication-failed])]
    (ev/login-form @msg)))

(defn view-home []
  (dispatch [:set-active-navbar :home])
  [:div {:style {:height "500px"}}
   [:h1 "Home page"]
   [:div
    [:p "Welcome to time off 4 me!!!"]]])

(defn view-calendar []
  (dispatch [:set-active-navbar :calendar])
  (dispatch [:fetch-leave-types])
  (dispatch [:fetch-my-bookings])
  (dispatch [:fetch-departments-with-employees])
  (dispatch [:fetch-start-types])
  (dispatch [:fetch-end-types])
  (let [my-bookings (subscribe [:my-bookings])]
    (fn []
      (if-not (seq @my-bookings)
        [common-components/loader-component]
        [cv/bookings-list-layout @my-bookings]))))

(defn view-user []
  (dispatch [:set-active-navbar :user])
  [:div {:style {:height "500px"}} [:h1 "User page"]])

(defn view-not-found []
  [:div.well [:h1.text-center {:style {:color "red"}} "404 NOT FOUND !!!!!"]])

(defn view-employees []
  (dispatch [:set-active-navbar :employees])
  (dispatch [:fetch-departments])
  (dispatch [:fetch-departments-with-employees])
  (let [departments (subscribe [:departments])]
    (fn []
      (if-not (seq @departments)
        [common-components/loader-component]
        [ev/departments-list-layout @departments]))))

(defn view-employee-add []
  (dispatch [:fetch-departments])
  (let [emp (subscribe [:employee])
        departments (subscribe [:departments])]
    (fn []
      (if (not (or (:is-ready? @emp) (some? @departments)))
        (do
          (dispatch [:employee-add])
          [common-components/loader-component])
        [ev/employee-maintenance-form @emp]))))

(defn view-employee []
  (dispatch [:fetch-departments])
  (let [emp (subscribe [:employee])
        departments (subscribe [:departments])]
    (fn []
      (if (not (and (:is-ready? @emp) (some? @departments)))
        [common-components/loader-component]
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
      [:span.icon-bar]]

     [:a.navbar-brand {:href (routes/site-url-for :home)} [:img {:src "/assets/img/logo30.png" :alt "MeTime Dashboard"}]]]
    [:div#nav-bar.navbar-collapse.collapse
     [:ul.nav.navbar-nav
      (for [nav-bar-item nav-bars]
        ^{:key (:id nav-bar-item)} [nav-menu-item nav-bar-item current-nav-bar-id])]]]])



(defmulti set-active-view identity)
(defmethod set-active-view :home [] view-home)
(defmethod set-active-view :login [] view-login)
(defmethod set-active-view :leave-types [] view-leave-types)
(defmethod set-active-view :calendar [] view-calendar)
(defmethod set-active-view :user [] view-user)
(defmethod set-active-view :employees [] view-employees)
(defmethod set-active-view :employee-add [] view-employee-add)
(defmethod set-active-view :employee-editor [] view-employee)
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
               [(set-active-view @current-view)]]])))


(defn initial-panel []
  (let [ready? (subscribe [:initialised?])]
    (fn []
      (if-not @ready?
        [common-components/loader-component]
        [main-panel]))))
;)