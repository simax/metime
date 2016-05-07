(ns metime.employees.views
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require
    [metime.common.views :refer [loader-component date-component show-error valid-input-style invalid-input-style]]
    [cljs.core.async :refer [put! take! <! >! chan timeout]]
    [metime.navigation.subs]
    [metime.employees.subs]
    [metime.employees.handlers]
    [metime.routes :as routes]
    [metime.utils :as utils]
    [re-com.core :refer [h-box v-box box gap scroller
                         title single-dropdown label
                         input-text input-textarea datepicker datepicker-dropdown button
                         popover-anchor-wrapper popover-content-wrapper
                         popover-tooltip md-icon-button md-circle-icon-button row-button]
     :refer-macros [handler-fn]]
    [re-com.datepicker :refer [iso8601->date datepicker-args-desc]]
    [cljs-time.core :refer [within? before? after? date-time now days minus day-of-week first-day-of-the-month]]
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

(defn manager-gravatar [employee]
  [v-box
   :align :center
   :width "150px"
   :children
   [[box :child [:h6 "Manager"]]
    [box :child [utils/gravatar {:gravatar-email (:manager-email employee) :gravatar-size 75}]]
    [box :child [:h5 (str (:manager-firstname employee) " " (:manager-lastname employee))]]]])


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
      [:span {:class "li_calendar fs1"}]
      [:span {:class    "glyphicon glyphicon-trash fs1"
              :on-click (fn [e]
                          (dispatch [:employee-delete id])
                          (.preventDefault e))}]]


     ;; For now, just simulate the number of days remaining
     [:h2 {:class "text-center" :style {:color "red"}} (rand-int 25)]]]])



(defn empty-department []
  [v-box
   :justify :center
   :align :center
   :children
   [[:span {
            :style {:font-size "50px"}
            :class "glyphicon glyphicon-exclamation-sign fs2"
            }]
    [:h2 {:style {:font-weight :bold}} "No employees in department"]]])


(defn department-employees-list [manager-id]
  (let [employees (subscribe [:filtered-department-employees])
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
  (let [fetching-department-employees? (subscribe [:fetching-department-employees?])
        department-employees (subscribe [:department-employees])
        search-criteria (subscribe [:search-criteria])]
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
          :justify :between
          :children
          [
           (if (seq @department-employees)
             [h-box
              :gap "5px"
              :align :center
              :children
              [
               [:span {:style {:font-size "25px"}
                       :class "glyphicon glyphicon-search"
                       }]
               [input-text
                :width "300px"
                :model @search-criteria
                :placeholder "Search employees..."
                :on-change #(dispatch [:input-change-employee-search %])
                :change-on-blur? false]]]
             [:div])
           [md-circle-icon-button
            :md-icon-name "zmdi-plus"
            :emphasise? true
            :on-click #(dispatch [:employee-add-new])
            :style {:background-color "red" :border-color "red"}
            :tooltip "Add a new employee to the department"
            :tooltip-position :right-center]]]
         (if @fetching-department-employees?
           [loader-component "auto"]
           [department-employees-list manager-id])]]])))

(defn manager-name-component [edit-mode {:keys [manager-firstname manager-lastname] :as department}]
  (let [sorted-employees (subscribe [:sorted-departments-with-employees])
        id-fn #(:id %)
        group-fn #(str (:department %))
        label-fn #(str (:firstname %) " " (:lastname %))
        selected-employee-id (reagent/atom (:manager-id department))
        mgr-error-message (reagent/atom "A manager is required")
        mgr-showing-error-icon? (reagent/atom (seq (get-in department [:validation-errors :manager-id])))
        mgr-showing-tooltip? (reagent/atom false)]

    (if (utils/is-mutating-mode? edit-mode)
      [h-box
       :gap "5px"
       :width "315px"
       :children
       [
        [single-dropdown
         :style (if @mgr-showing-error-icon? invalid-input-style valid-input-style)
         :width "230px"
         :placeholder "Manager"
         :choices @sorted-employees
         :id-fn id-fn
         :label-fn label-fn
         :group-fn group-fn
         :model selected-employee-id
         :filter-box? true
         :on-change #(dispatch [:set-department-manager-id %])]
        (show-error mgr-error-message mgr-showing-error-icon? mgr-showing-tooltip?)]]
      [box :child [:h5 (str manager-firstname " " manager-lastname)]])))

(defn manager-component [edit-mode department]
  (let [manager-email (subscribe [:department-manager-email])]
    (if (utils/is-mutating-mode? edit-mode)
      [h-box
       :gap "10px"
       :width "300px"
       :align :center
       :children
       [
        [utils/gravatar {:gravatar-email @manager-email :gravatar-size 50}]
        [manager-name-component edit-mode department]]]
      [h-box
       :gap "10px"
       :width "300px"
       :align :center
       :children
       [
        [utils/gravatar {:gravatar-email (:manager-email department) :gravatar-size 50}]
        [manager-name-component edit-mode department]]])))

(defn department-name-component [edit-mode department]
  (let [department-name (:department department)]
    (if (utils/is-mutating-mode? edit-mode)
      [input-text
       :width "400px"
       :model department-name
       :placeholder "Department name"
       :on-change #(dispatch [:input-change-department-name %])
       :status (when (seq (get-in department [:validation-errors :department])) :error)
       :status-icon? (seq (get-in department [:validation-errors :department]))
       :status-tooltip (apply str (get-in department [:validation-errors :department]))
       :change-on-blur? false]
      [box
       :width "400px"
       :child [:h2 department-name]])))

(defn department-buttons-component [edit-mode {:keys [department-id employee-count]}]
  (if (utils/is-mutating-mode? edit-mode)
    [h-box
     :align :center
     :gap "10px"
     :width "100px"
     :justify :end
     :children
     [
      [md-circle-icon-button
       :emphasise? true
       :size :smaller
       :md-icon-name "zmdi-floppy"
       :tooltip "Save department"
       :on-click #(dispatch [:department-save])]
      [md-circle-icon-button
       :emphasise? false
       :size :smaller
       :md-icon-name "zmdi-close-circle-o"
       :tooltip "Cancel"
       :on-click #(dispatch [:close-all-department-drawers])]]]

    [h-box
     :align :center
     :gap "10px"
     :width "100px"
     :justify :end
     :children
     [
      [md-icon-button
       :md-icon-name "zmdi-edit"                            ;
       :style {:color "#b2c831"}
       :on-click #(dispatch [:edit-department department-id])]
      [md-icon-button
       :md-icon-name "zmdi-swap-vertical"                   ;
       :on-click #(dispatch [:ui-department-drawer-status-toggle department-id])]

      (if (zero? employee-count)
        [md-icon-button
         :md-icon-name "zmdi-delete"                        ;
         :style {:color "Red"}
         :on-click #(dispatch [:department-delete department-id])]
        [box :width "25px" :child [:div]])]]))

(defn department-component [dept]
  (let [edit-mode (subscribe [:department-edit-mode (:department-id dept)])
        department (if (utils/is-mutating-mode? @edit-mode) (deref (subscribe [:department])) dept)]
    [box
     :class (if (utils/is-mutating-mode? @edit-mode) "mutating" "panel panel-default")
     ;:style (if (utils/is-mutating-mode? @edit-mode) {:background-color "Gainsboro" :border-width "1px" :border-style "solid" :border-color "white" :margin-bottom "20px"} {})
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
         [manager-component @edit-mode department]
         [department-name-component @edit-mode department]]]

       [department-buttons-component @edit-mode department]]]]))


(defn department-list-item [department]
  (let [department-drawer-open-id (subscribe [:department-drawer-open-id])]
    [v-box
     :children
     [
      [department-component department]
      [department-employees-panel @department-drawer-open-id (:department-id department) (:manager-id department)]]]))


(defn departments-list [departments]
  (let [department (subscribe [:department])]
    [scroller
     :v-scroll :auto
     :height "780px"
     :width "1000px"
     :child
     [v-box
      :width "950px"
      :children
      [
       (when (and (seq @department) (= (:department-id @department) 0))
         [department-component @department])
       (for [department departments]
         ^{:key (:department department)}
         [department-list-item department])]]]))


(defn departments-list-layout [departments]
  [h-box
   :children
   [(departments-list departments)
    [box
     :align :end
     :child
     [md-circle-icon-button
      :md-icon-name "zmdi-plus"
      :size :larger
      :emphasise? true
      :style {:background-color "red" :border-color "red"}
      :on-click #(dispatch [:ui-new-department-drawer-status-toggle])
      :tooltip "Add a new department"
      :tooltip-position :right-center]]
    ]])


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

    [h-box
     :children
     [[box :width "150px" :child [label :label "Date of birth"]]
      (date-component
        :model-key :employee
        :model employee
        :field :dob
        :field-label "Date of birth"
        :place-holder "Date of birth"
        :popup-position :above-center
        :error-message error-message
        :showing-error-icon? showing-error-icon?
        :selectable-fn date-in-the-past?)]]))

(defn employee-start-date [employee]
  (let [error-message (subscribe [:employee-startdate-error-message])
        showing-error-icon? (subscribe [:employee-startdate-show-error])]
    [h-box
     :children
     [[box :width "150px" :child [label :label "Start date"]]
      (date-component
        :model-key :employee
        :model employee
        :field :startdate
        :field-label "Start date"
        :place-holder "Start date"
        :popup-position :above-center
        :error-message error-message
        :showing-error-icon? showing-error-icon?
        :selectable-fn after-dob?)]]))

(defn employee-end-date [employee]
  (let [error-message (subscribe [:employee-enddate-error-message])
        showing-error-icon? (subscribe [:employee-enddate-show-error])]
    (letfn [(after-startdate? [date]
              (when (not (nil? (:dob employee)))
                (after? date (parse (formatter "dd-MM-yyyy") (:dob employee)))))]
      [h-box
       :children
       [[box :width "150px" :child [label :label "End date"]]
        (date-component
          :model-key :employee
          :model employee
          :field :enddate
          :field-label "End date"
          :place-holder "End date"
          :popup-position :above-center
          :error-message error-message
          :showing-error-icon? showing-error-icon?
          :selectable-fn after-startdate?)]])))

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
                 :class "panel-body"
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
      [title :style {:color "white"} :class "panel-heading panel-title" :label "Employee" :level :level2]
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
    [title :style {:color "white"} :class "panel-heading panel-title" :label "Balances" :level :level2]
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
   :children
   [
    [employee-core-heading employee]
    [h-box
     :style {:flex-flow "row wrap"}
     ;:class "panel"
     :justify :between
     :gap "10px"
     :children
     [
      [box :child [employee-core-details employee]]
      [box :child [employee-balances employee]]

      ;[box :child [employee-errors]]
      ;[box :child [input-text
      ;             :width "100px"
      ;             :model (:startdate employee)
      ;             :disabled? true
      ;             :on-change #(dispatch [:none %])]]
      ]]
    [box :child [save-button]]]])





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