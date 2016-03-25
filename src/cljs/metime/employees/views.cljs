(ns metime.employees.views
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [metime.formatting :as fmt]
            [metime.common.views :as common-components]
            [cljs.core.async :refer [put! take! <! >! chan timeout]]
            [metime.navigation.subs]
            [metime.employees.subs]
            [metime.employees.handlers]
            [metime.routes :as routes]
            [metime.utils :as utils]
            [re-com.core :refer [h-box v-box box gap
                                 title single-dropdown label
                                 input-text input-textarea datepicker datepicker-dropdown button
                                 popover-anchor-wrapper popover-content-wrapper
                                 popover-tooltip md-icon-button md-circle-icon-button row-button]
             :refer-macros [handler-fn]]
            [re-com.datepicker :refer [iso8601->date datepicker-args-desc]]
            [cljs-time.core :refer [within? before? after? date-time now days minus day-of-week]]
            [cljs-time.format :refer [formatter parse unparse]]
            [cljs-time.coerce]
            [goog.date]
            [re-frame.core :refer [dispatch subscribe]]
            [reagent.core :as reagent]
            [reagent.ratom :refer [make-reaction]]
            [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame-tracer.core :refer [tracer]]
            [cljs.pprint :refer [pprint]]))


;(trace-forms
;  {:tracer (tracer :color "indigo")}


(defn login-email [credentials]
  [h-box
   :justify :between
   :children
   [
    [box :width "150px" :child [label :label "Email"]]
    [box :size "auto" :child [input-text
                              :width "300px"
                              :model (:email credentials)
                              :status (when (seq (get-in credentials [:validation-errors :email])) :error)
                              :status-icon? (seq (get-in credentials [:validation-errors :email]))
                              :status-tooltip (apply str (get-in credentials [:validation-errors :email]))
                              :placeholder "Email"
                              :on-change #(dispatch [:input-change-no-validate :email %])
                              :change-on-blur? false]]]])



(defn login-password [credentials]
  [h-box
   :justify :between
   :children
   [
    [box :width "150px" :child [label :label "Password"]]
    [box :size "auto" :child [input-text
                              :width "300px"
                              :model (:password credentials)
                              :status (when (seq (get-in credentials [:validation-errors :password])) :error)
                              :status-icon? (seq (get-in credentials [:validation-errors :password]))
                              :status-tooltip (apply str (get-in credentials [:validation-errors :password]))
                              :placeholder "Password"
                              :on-change #(dispatch [:input-change-no-validate :password %])
                              :change-on-blur? false
                              :attr {:type "password"}]]]])


(defn login-button [msg]
  [h-box
   :class "panel"
   :justify :between
   :align-self :stretch
   :children
   [
    [box :width "150px" :child [label :style {:color "red" :font-weight :bold} :label msg]]
    [box
     :child [button
             :class "btn btn-primary"
             :on-click #(dispatch [:log-in])
             :label "Login"]]]])

(defn employee-name [firstname lastname]
  (let [disp-name (str firstname " " lastname)
        name-length (count disp-name)]
    (if (> name-length 20) (str (subs disp-name 0 17) "...") disp-name)))

; TODO: Refactor to re-com components
(defn employee-list-item [{:keys [lastname firstname email id]}]
  [:div {:class "col-md-3 col-lg-3"}
   [:div {:class "dash-unit"}
    [:div {:class "thumbnail" :style {:margin-top "20px"}}
     [:a {:href     (routes/site-url-for :employee-editor :id id)
          :on-click (fn [e] (.preventDefault e) (dispatch [:employee-to-edit id]))}

      [:h1 (employee-name firstname lastname)]
      [:div {:style {:margin-top "20px"}} [utils/gravatar {:gravatar-email email}]]]

     [:div {:class "info-user"}
      ;[:span {:aria-hidden "true" :class "li_user fs1"}]
      [:span {:aria-hidden "true" :class "li_calendar fs1"}]
      ;[:span {:aria-hidden "true" :class "li_mail fs1"}]
      [:span {:aria-hidden "true"
              :class       "glyphicon glyphicon-trash fs1"
              :on-click    (fn [e]
                             (dispatch [:employee-delete id])
                             (.preventDefault e))}]]


     ;; For now, just simulate the number of days remaining
     [:h2 {:class "text-center" :style {:color "red"}} (rand-int 25)]]]])


(defn new-department-container []
  (let [dep (subscribe [:department])
        sorted-employees (subscribe [:sorted-departments-with-employees])
        id-fn #(:id %)
        group-fn #(str (:department %))
        label-fn #(str (:firstname %) " " (:lastname %))
        selected-employee-id (reagent/atom nil)]
    [h-box
     :gap "20px"
     :children
     [
      [v-box
       :gap "10px"
       :children
       [[label :class "control-label" :label "Department"]
        [input-text
         :width "315px"
         :model (:department @dep)
         :placeholder "Department name"
         :on-change #(dispatch [:input-change-department :department %])
         :change-on-blur? false]]]
      [v-box
       :gap "10px"
       :children
       [[label :class "control-label" :label "Manager"]
        [single-dropdown
         :width "315px"
         :placeholder "Manager"
         :choices @sorted-employees
         :id-fn id-fn
         :label-fn label-fn
         :group-fn group-fn
         :model selected-employee-id
         :filter-box? true
         :on-change #(dispatch [:set-department-manager-id %])]]]

      [v-box
       :justify :end
       :children
       [
        [md-icon-button
         :emphasise? true
         :md-icon-name "zmdi-floppy"
         :tooltip "Save department"
         :on-click #(dispatch [:department-save])]]]]]))


(defn empty-department []
  [h-box
   :justify :center
   :children
   [[:h2 {:style {:font-weight :bold}} "No employees in department"]]])


(defn department-employees-list [manager-id]
  (let [employees (subscribe [:department-employees])
        department-list-item (filter #(not= (:id %) manager-id) @employees)
        rows-of-employees (partition 4 4 nil department-list-item)]
    (if (zero? (count @employees))
      [empty-department]
      [box
       :child
       [:ul {:style {:margin-top "20px"}}
        (for [employee-row rows-of-employees]
          (for [employee-item employee-row]
            ^{:key (:id employee-item)} [employee-list-item employee-item]))]])))


(defn department-employees-panel [department-drawer-open-id department-id manager-id]
  (let [fetching-department-employees? (subscribe [:fetching-department-employees?])]
    (when (= department-drawer-open-id department-id)
      [box
       ;:style {:height "auto" :border-width "1" :border-style "solid" :border-color "white"}
       :child
       [v-box
        :gap "20px"
        :min-height "220px"
        :children
        [
         [h-box
          :gap "10px"
          :justify :start
          :align :center
          :children
          [
           [md-circle-icon-button
            :md-icon-name "zmdi-plus"
            :emphasise? true
            :on-click #(dispatch [:employee-add-new])
            :tooltip "Add a new employee to the department"]]]
         (if @fetching-department-employees?
           [common-components/loader-component "200px"]
           [department-employees-list manager-id])]]])))


(defn department-list-item [{:keys [department-id department manager-id manager-firstname manager-lastname manager-email employee-count]}]
  (let [department-drawer-open-id (subscribe [:department-draw-open-id])
        department-name (clojure.string/replace department #"[\s]" "-")]

    [v-box
     :children
     [
      [box
       :class "panel panel-default"
       ;:style {:border-width "1" :border-style "solid" :border-color "white"}
       :child
       [h-box
        :class "panel-body row"
        :height "65px"
        :justify :between
        :children
        [
         [h-box
          :gap "20px"
          :align :center
          :children
          [
           [box
            ;:style {:border-width "1" :border-style "solid" :border-color "white"}
            :child
            [h-box
             :gap "10px"
             :width "350px"
             :align :center
             :children
             [
              [utils/gravatar {:gravatar-email manager-email :gravatar-size 50}]
              [box :child [:h5 (str manager-firstname " " manager-lastname)]]]]]
           [box
            ;:style {:border-width "1" :border-style "solid" :border-color "white"}
            :width "500px"
            :child [:h2 department-name]]]]

         [h-box
          :align :center
          :gap "10px"
          :width "100px"
          :justify :end
          :children
          [
           (when (zero? employee-count)
             [box
              ;:style {:border-width "1" :border-style "solid" :border-color "white"}
              :child
              [md-icon-button
               :md-icon-name "zmdi-delete"                  ;
               :size :regular
               :on-click #(dispatch [:ui-department-drawer-status-toggle department-id])]])

           [box
            ;:style {:border-width "1" :border-style "solid" :border-color "white"}
            :child
            [md-icon-button
             :md-icon-name "zmdi-swap-vertical"             ;
             :size :larger
             :on-click #(dispatch [:ui-department-drawer-status-toggle department-id])]]]]]]]

      [department-employees-panel @department-drawer-open-id department-id manager-id]]]))



(defn department-list [departments]
  (let [new-department-draw-open-class (subscribe [:new-department-draw-open-class])]
    [v-box
     :class "panel-body row"
     :height "65px"
     :justify :start
     :children
     [
      [h-box
       :align :center
       :justify :center
       :children
       [
        [box
         :class "panel panel-default"
         :child
         [h-box
          :style {:padding-left "30px"}
          :align :center
          :gap "140px"
          :children
          [
           [md-circle-icon-button
            :md-icon-name "zmdi-plus"
            :emphasise? true
            :on-click #(dispatch [:ui-new-department-drawer-status-toggle])
            :tooltip "Add a new department"]
           [box :child [:h2 "Add a new department"]]]]]

        [box
         :child
         [:div {:class @new-department-draw-open-class :id "new-department" :style {:height "auto"}}
          [new-department-container]]]]]


      [v-box
       :gap "20px"
       :class "panel-default"
       :width "1100px"
       :children
       [
        (for [department departments]
          ^{:key (:department department)}
          [department-list-item department])]]]]))


(defn departments-container [departments]
  [v-box
   :children
   [
    [box
     :child
     [department-list departments]]]])


(defn employee-not-found []
  [box :child [:div.well [:h1 {:style {:color "red"}} "Sorry, we couldn't find that employee."]]])

(defn employee-gravatar [employee]
  [box
   :padding "20px 0 0 0"
   :child
   [h-box
    :gap "20px"
    :height "150px"
    :children
    [
     [box :child [utils/gravatar {:gravatar-email (:email employee) :gravatar-size 150}]]
     [v-box
      :justify :center
      :children
      [[box :child [:h1 (str (:firstname employee) " " (:lastname employee))]]
       [:h2 {:style {:color "grey"}} (:department employee)]]]]]])


(defn manager-gravatar [employee]
  [v-box
   :align :center
   :width "150px"
   :children
   [[box :child [:h6 "Manager"]]
    [box :child [utils/gravatar {:gravatar-email (:manager-email employee) :gravatar-size 75}]]
    [box :child [:h5 (str (:manager-firstname employee) " " (:manager-lastname employee))]]]])


(defn employee-core-heading [employee]
  [h-box
   :padding "20px"
   :align :center
   :class "panel panel-default"
   :children [[box :child [employee-gravatar employee]]
              [gap :size "1"]
              [box :child [manager-gravatar employee]]]])


(defn department-list-choices [departments]
  (into []
        (for [m departments]
          {:id (:department-id m) :label (:department m)})))

(defn department-drop-down-list [employee departments]
  [h-box
   :justify :start
   :children
   [
    [box :width "150px" :child [label :class "control-label" :label "Department"]]
    [box :size "auto" :child [single-dropdown
                              :model (:department-id employee)
                              :choices (department-list-choices departments)
                              :on-change #(dispatch [:department-change %])]]]])



(defn employee-first-name [employee]
  [h-box
   :justify :between
   :children
   [
    [box :width "150px" :child [label :label "First name"]]
    [box :size "auto" :child [input-text
                              :width "315px"
                              :model (:firstname employee)
                              :status (when (seq (get-in employee [:validation-errors :firstname])) :error)
                              :status-icon? (seq (get-in employee [:validation-errors :firstname]))
                              :status-tooltip (apply str (get-in employee [:validation-errors :firstname]))
                              :placeholder "Employee first name"
                              :on-change #(dispatch [:input-change :firstname %])
                              :change-on-blur? false]]]])



(defn employee-last-name [employee]
  [h-box
   :justify :start
   :children
   [
    [box :width "150px" :child [label :label "Last name"]]
    [box :size "auto" :child [input-text
                              :width "315px"
                              :model (:lastname employee)
                              :status (when (seq (get-in employee [:validation-errors :lastname])) :error)
                              :status-icon? (seq (get-in employee [:validation-errors :lastname]))
                              :status-tooltip (apply str (get-in employee [:validation-errors :lastname]))
                              :placeholder "Employee last name"
                              :on-change #(dispatch [:input-change :lastname %])
                              :change-on-blur? false]]]])


(defn employee-email [employee]
  [h-box
   :justify :between
   :children
   [
    [box :width "150px" :child [label :label "Email"]]
    [box :size "auto" :child [input-text
                              :width "315px"
                              :model (:email employee)
                              :status (when (seq (get-in employee [:validation-errors :email])) :error)
                              :status-icon? (seq (get-in employee [:validation-errors :email]))
                              :status-tooltip (apply str (get-in employee [:validation-errors :email]))
                              :placeholder "Employee email address"
                              :on-change #(dispatch [:input-change :email %])
                              :change-on-blur? true]]]])



(defn date-input-with-popup [date-field date-value showing? title selectable-fn]
  "Displays a popup for the given date field"
  [popover-anchor-wrapper
   :showing? showing?
   :position :above-center
   :anchor [button
            :style {:border-radius "0 4px 4px 0"}
            :attr {:tab-index -1}
            :label [:i {:class "zmdi zmdi-apps"}]
            :on-click #(swap! showing? not)]
   :popover [popover-content-wrapper
             :showing? showing?
             :title title
             :position :above-right
             :backdrop-opacity 0.5
             :width "250px"
             :no-clip? true
             :arrow-length 20
             :arrow-width 20
             :close-button? true
             :on-cancel #(reset! showing? false)
             :body [datepicker
                    :selectable-fn selectable-fn
                    :model (reagent/atom (fmt/str->date date-value))
                    :show-today? true
                    :on-change #(dispatch [:datepicker-change-dates date-field %])]]])

(def inavlid-date-style {:border-radius "4px 4px 4px 4px"
                         :border-color  "red"
                         :border-style  "solid"
                         :border-width  "1px"})

(def valid-date-style {:border-radius "4px 4px 4px 4px"
                       :border-color  "white"
                       :border-style  "solid"
                       :border-width  "1px"})


(defn show-date-error [error-message showing-error-icon? showing-tooltip?]
  (when @showing-error-icon? [popover-tooltip
                              :label @error-message
                              :position :right-center
                              :showing? showing-tooltip?
                              :status :error
                              :width "150px"
                              :anchor [:i
                                       {:class         "zmdi zmdi-alert-circle"
                                        :on-mouse-over (handler-fn (reset! showing-tooltip? true))
                                        :on-mouse-out  (handler-fn (reset! showing-tooltip? false))
                                        :style         {:color     "red"
                                                        :font-size "130%"}}]]))




(defn date-component
  "A generic date input box with popup and validation features"
  ([db-model field field-label place-holder error-message showing-error-icon?]
   (date-component db-model field field-label place-holder error-message showing-error-icon? (partial constantly true)))

  ([db-model field field-label place-holder error-message showing-error-icon? selectable-fn]
   (let [showing-date-popup? (reagent/atom false)
         showing-tooltip? (reagent/atom false)]
     [h-box
      :justify :start
      :children
      [
       [box :width "150px" :child [label :label field-label]]
       [h-box
        :style (if @showing-error-icon? inavlid-date-style valid-date-style)
        :children
        [
         [box :child [input-text
                      :style {:border-radius "4px 0 0 4px"}
                      :placeholder place-holder
                      :model (fmt/date->str (field db-model))
                      :width "120px"
                      :change-on-blur? true
                      :on-change #(dispatch [:input-change-dates field %])]]
         (date-input-with-popup field (field db-model) showing-date-popup? place-holder selectable-fn)]]

       (show-date-error error-message showing-error-icon? showing-tooltip?)]])))


(defn date-in-the-past? [date]
  (before? date (now)))

(defn after-dob? [date]
  (let [employee (:employee @re-frame.db/app-db)
        dob (:dob employee)]
    (when (not (nil? dob))
      (after? date (parse (formatter "dd-MM-yyyy") dob)))))


(defn employee-dob [employee]
  (let [error-message (subscribe [:employee-dob-error-message])
        showing-error-icon? (subscribe [:employee-dob-show-error])]
    (date-component employee :dob "Date of birth" "Date of birth" error-message showing-error-icon? date-in-the-past?)))


(defn employee-start-date [employee]
  (let [error-message (subscribe [:employee-startdate-error-message])
        showing-error-icon? (subscribe [:employee-startdate-show-error])]
    (date-component employee :startdate "Start date" "Start date" error-message showing-error-icon? after-dob?)))


(defn employee-end-date [employee]
  (let [error-message (subscribe [:employee-enddate-error-message])
        showing-error-icon? (subscribe [:employee-enddate-show-error])]
    (letfn [(after-startdate? [date]
              (when (not (nil? (:dob employee)))
                (after? date (parse (formatter "dd-MM-yyyy") (:dob employee)))))]
      (date-component employee :enddate "End date" "End date" error-message showing-error-icon? after-startdate?))))

(defn employee-prev-year-allowance [employee]
  [h-box
   :justify :between
   :children
   [
    [box :child [label :label "Previous year allowance"]]
    [box :child [input-text
                 :width "100px"
                 :model (str (:prev-year-allowance employee))
                 :status (when (seq (get-in employee [:validation-errors :prev-year-allowance])) :error)
                 :status-icon? (seq (get-in employee [:validation-errors :prev-year-allowance]))
                 :status-tooltip (apply str (get-in employee [:validation-errors :prev-year-allowance]))
                 :on-change #(dispatch [:input-change-balances :prev-year-allowance %])
                 :validation-regex #"^(-{0,1})(\d{0,2})$"
                 :change-on-blur? true]]]])



(defn employee-current-year-allowance [employee]
  [h-box
   :justify :between
   :children
   [
    [box :child [label :label "Current year allowance"]]
    [box :child [input-text
                 :width "100px"
                 :model (str (:current-year-allowance employee))
                 :status (when (seq (get-in employee [:validation-errors :current-year-allowance])) :error)
                 :status-icon? (seq (get-in employee [:validation-errors :current-year-allowance]))
                 :status-tooltip (apply str (get-in employee [:validation-errors :current-year-allowance]))
                 :on-change #(dispatch [:input-change-balances :current-year-allowance %])
                 :validation-regex #"^(-{0,1})(\d{0,2})$"
                 :change-on-blur? false]]]])



(defn employee-next-year-allowance [employee]
  [h-box
   :justify :between
   :children
   [
    [box :child [label :label "Next year allowance"]]
    [box :child [input-text
                 :width "100px"
                 :model (str (:next-year-allowance employee))
                 :status (when (seq (get-in employee [:validation-errors :next-year-allowance])) :error)
                 :status-icon? (seq (get-in employee [:validation-errors :next-year-allowance]))
                 :status-tooltip (apply str (get-in employee [:validation-errors :next-year-allowance]))
                 :on-change #(dispatch [:input-change-balances :next-year-allowance %])
                 :validation-regex #"^(-{0,1})(\d{0,2})$"
                 :change-on-blur? false]]]])




(defn save-button []
  [v-box
   :children [
              [h-box
               :style {:flex-flow "row wrap"}
               :class "panel"
               :justify :between
               :children
               [
                [box
                 :class "panel panel-body"
                 :child [button
                         :class "btn btn-primary"
                         :on-click #(dispatch [:employee-save])
                         :label "Save"]]]]]])




(defn employee-core-details [employee]
  (let [departments (subscribe [:departments])]
    [v-box
     :width "500px"
     :class "panel panel-default"
     :children
     [
      [title :class "panel-heading panel-title" :label "Employee" :level :level3]
      [h-box
       :class "panel-body"
       :children
       [[v-box
         :size "auto"
         :gap "10px"
         :children
         [
          [department-drop-down-list employee @departments]
          [employee-first-name employee]
          [employee-last-name employee]
          [employee-email employee]
          [employee-dob employee]
          [employee-start-date employee]
          [employee-end-date employee]]]]]]]))





(defn employee-balances [employee]
  [v-box
   :size "auto"
   :width "350px"
   :class "panel panel-default"
   :children
   [
    [title :class "panel-heading panel-title" :label "Balances" :level :level3]
    [h-box
     :children
     [[v-box
       :size "auto"
       :class "panel-body"
       :gap "10px"
       :children
       [
        [employee-prev-year-allowance employee]
        [employee-current-year-allowance employee]
        [employee-next-year-allowance employee]]]]]]])





(defn employee-errors []
  (let [employee-errors (subscribe [:employee-errors])]
    (fn []
      [input-textarea
       :style {:border-radius "4px 0 0 4px"}
       :model (with-out-str (pprint @employee-errors))
       :width "600px"
       :rows 10
       :disabled? true
       :on-change (fn [] identity)])))


(defn employee-maintenance-form [employee]
  [v-box
   :children [
              [employee-core-heading employee]
              [h-box
               :style {:flex-flow "row wrap"}
               :class "panel"
               :justify :between
               :children
               [
                [box :class "panel panel-body" :child [employee-core-details employee]]
                [box :class "panel panel-body" :child [employee-balances employee]]

                ;[box :child [employee-errors]]
                ;[box :child [input-text
                ;             :width "100px"
                ;             :model (:startdate employee)
                ;             :disabled? true
                ;             :on-change #(dispatch [:none %])]]

                [box :class "panel panel-body" :child [save-button]]]]]])





(defn login-form [msg]
  (let [credentials {:email "" :password ""}]
    [v-box
     :width "480px"
     :class "panel panel-body"
     :gap "20px"
     :children
     [
      [login-email credentials]
      [login-password credentials]
      [login-button msg]]]))



; )