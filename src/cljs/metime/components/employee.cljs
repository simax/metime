(ns metime.components.employee
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [cljs.core.async :refer [put! <! >! chan timeout]]
            [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [cljs-http.client :as http]
            [sablono.core :as html :refer-macros [html]]
            [cljs-hash.md5 :as hashgen]
            [cljs-hash.goog :as gh]
            [metime.components.utils :as utils]
            [secretary.core :as secretary :refer-macros [defroute]])
  (:import goog.History
           goog.History.EventType))

(enable-console-print!)

(defn fetch-departments
  [url]
  (let [c (chan)]
    (go (let [deps (((<! (http/get url)) :body) :departments)]
          (>! c deps)))
    c))

(defn fetch-employee [url-with-id]
  (let [c (chan)]
    (go (let [emp (first ((<! (http/get url-with-id)) :body))]
         (if-not (nil? emp)
           (>! c emp)
           (>! c "not found"))))
    c))


(defn employee-name [firstname lastname]
  (let [disp-name (str firstname " " lastname)
        name-length (count disp-name)]
    (if (> name-length 20) (str (subs disp-name 0 17) "...") disp-name)))


(defcomponent employee-list-item [{:keys [lastname firstname email id]}]
  (display-name [_]
                "employee-list-item")

  (render [_]
          (let [edit (fn [e]
                       (secretary/dispatch! (str "/employees/" id))
                       (println "You clicked the button"))]
            (html
              [:div {:class "col-sm-3 col-lg-3"}
               [:div {:class "dash-unit"}
                [:div {:class "thumbnail" :style {:margin-top "20px"}}
                 [:a {:href (str "/#/employees/" id)}
                  [:h1 (employee-name firstname lastname)]
                  [:div {:style {:margin-top "20px"}} (utils/->gravatar {:gravatar-email email})]
                 ]
                 [:div {:class "info-user"}
                  [:span {:aria-hidden "true" :class "li_user fs1"}]
                  [:span {:aria-hidden "true" :class "li_calendar fs1"}]
                  [:span {:aria-hidden "true" :class "li_mail fs1"}]
                  [:span {:aria-hidden "true" :class "glyphicon glyphicon-trash fs1"}]
                 ]

                 ;; For now, just simulate the number of days remaining
                 [:h2 {:class "text-center" :style {:color "red"}} (rand-int 25)]
                 ]]]))))


(defcomponent department-list-item [{:keys [department managerid manager-firstname manager-lastname manager-email employees]} owner opts]
  (display-name [_]
                "department-name")

  (render [_]
          (let [department-list-item (filter #(not= (:id %) managerid) employees)
                rows-of-employees (partition 4 4 nil department-list-item)]
            (html
             [:div#accordian.panel.panel-default.row

              [:div.panel-heading.clearfix.panel-heading
               [:div.col-md-2.col-xs-2
                [:div.col-md-4.col-xs-4 (utils/->gravatar {:gravatar-email manager-email :gravatar-size 50})]
                [:div.col-md-8.col-xs-8
                 [:h5 (str manager-firstname " " manager-lastname)]
                 ]
                ]
               [:div.col-md-10.col-xs-10
                [:div.col-md-11.col-xs-11
                 [:h2 department]
                 ]
                [:div.col-md-1.col-xs-1
                 [:button {:class "btn btn-default glyphicon glyphicon-sort"
                           :data-toggle "collapse"
                           :data-parent "accordian"
                           :data-target (str "#" (clojure.string/replace department #"[\s]" "-"))}]
                 ]
                ]
               ]

              [:div.panel-body.panel-collapse.collapse {:id (clojure.string/replace department #"[\s]" "-") :style {:height "auto"}}
               (if (not-empty rows-of-employees)
                 (for [employee-row rows-of-employees]
                   (om/build-all employee-list-item employee-row {:key :id})))]]))))


(defcomponent department-list [{:keys [departments]}]
  (display-name [_]
                "department-list")

  (render [_]
          (html
           [:div.clearfix.accordian
            (dom/ul (om/build-all department-list-item departments {:key :id}))
            ])))


(defcomponent departments-container [app _ opts]
  (display-name [_]
                "departments-box")

  (will-mount [_]
              (go
               (let [deps (<! (fetch-departments (:url opts)))]
                 (om/transact! app #(assoc % :departments deps))
                 )))

  (render [_]
                (html
                 [:div.row
                  (->department-list app)])))


(defn employee-not-found []
  [:h1 {:style {:color "red"}} "Sorry, we couldn't find that employee."])

(defn handle-change [e data edit-key owner]
  (om/transact! data edit-key (fn [_] (.. e -target -value))))

(defn handle-save [e data]
  (println data)
  )


(defn employee-container-form [employee owner]
  [:div {:style {:padding "20" :background-color "white" :height "500"}}
   [:form.form-horizontal
    ;; First name
    [:div.form-group
     [:label.col-sm-2.control-label {:for "first-name"} "First name"]
     [:div.col-sm-4
      [:input#first-name.form-control
       {:type "text"
        :placeholder "First name"
        :on-change #(handle-change % employee :firstname owner)
        :value (:firstname employee)}]]]

    ;; Last name
    [:div.form-group
     [:label.col-sm-2.control-label {:for "last-name"} "Last name"]
     [:div.col-sm-4
      [:input#last-name.form-control
       {:type "text"
        :placeholder "Last name"
        :on-change #(handle-change % employee :lastname owner)
        :value (:lastname employee)}]]]

    ;; Email
    [:div.form-group
     [:label.col-sm-2.control-label {:for "email"} "Email"]
     [:div.col-sm-4
      [:input#last-name.form-control
       {:type "email"
        :placeholder "Email address"
        :on-change #(handle-change % employee :email owner)
        :value (:email employee)}]]]

    ;; Start date
    [:div.form-group
     [:label.col-sm-2.control-label {:for "start-date"} "Start date"]
     [:div.col-sm-3
      [:input#start-date.form-control
       {:type "date"
        :placeholder "Start date"
        :on-change #(handle-change % employee :startdate owner)
        :value (:startdate employee)}]]]

    ;; Save button
    [:div.form-group
     [:div.col-sm-offset-2.col-sm-4
      [:button#save.btn.btn-primary {:type "button" :on-click #(handle-save % employee)} "Save"]]]]])



(defcomponent employee [app _ opts]
  (display-name [_]
                "employee")

  (will-mount [_]
              (let [url-with-id (str (:url opts) (:id app))]
                (go
                 (let [emp (<! (fetch-employee url-with-id))]
                   (if (= emp "not found")
                     (om/transact! app #(dissoc % :employee))
                     (om/transact! app #(assoc % :employee emp)))))))

  (render [_]
          (html
           (if (contains? app :employee)
             (employee-container-form (:employee app))
             (employee-not-found)
           ))))


