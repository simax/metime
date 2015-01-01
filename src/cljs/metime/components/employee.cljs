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

(declare app-state)

(def history (History.))

(defn fetch-departments
  [url]
  (let [c (chan)]
    (go (let [deps (((<! (http/get url)) :body) :departments)]
          (>! c deps)))
    c))

(defn fetch-employee [url-with-id]
  (println "About to fetch emloyee")
  (let [c (chan)]
    (go (let [emp (first ((<! (http/get url-with-id)) :body))]
         (if-not (nil? emp)
           (do
             (println (str "Employee: " emp))
             (>! c emp))

            (do
              (println "--->not found")
              (>! c "not found")))))
    c))


(defn employee-name [firstname lastname]
  (let [disp-name (str firstname " " lastname)
        name-length (count disp-name)]
    (if (> name-length 20) (str (subs disp-name 0 17) "...") disp-name)))


(defcomponent employee-info [{:keys [lastname firstname email id]} owner opts]
  (display-name [_]
                "employee-info")

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


(defcomponent department-employees [{:keys [department managerid manager-firstname manager-lastname manager-email employees]} owner opts]
  (display-name [_]
                "department-name")

  (render [_]
          (let [department-employees (filter #(not= (:id %) managerid) employees)
                rows-of-employees (partition 4 4 nil department-employees)]
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
                   (om/build-all employee-info employee-row {:key :id})))]]))))


(defcomponent department-list [{:keys [departments]}]
  (display-name [_]
                "department-list")

  (render [_]
          (html
           [:div.clearfix.accordian
            (dom/ul (om/build-all department-employees departments {:key :id}))
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


(defcomponent employee [app _ opts]
  (display-name [_]
                "employee")

  (will-mount [_]
              (let [url-with-id (str (:url opts) (:id app))]
                (println "will-mount")
                (go
                 (let [emp (<! (fetch-employee url-with-id))]
                  (if (= emp "not found")
                     (do
                       (println "NOT FOUND!!!")
                       (om/transact! app #(dissoc % :employee)))

                     (do
                       (println "Found OK!")
                       (om/transact! app #(assoc % :employee emp))))))))

  (render [_]
          (println "Rendering")
          (html
                 (if (contains? app :employee)
                   [:h1 "Stuff about the employee goes here"
                     [:div (str "Email: " (:email (:employee app)))]]
                   [:h1 "Sorry, we couldn't find that employee."])
                 )))


