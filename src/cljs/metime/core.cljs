(ns metime.core
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
            [secretary.core :as secretary :refer-macros [defroute]])
  (:import goog.History
           goog.History.EventType))


(enable-console-print!)

(def history (History.))

(defroute "/employees/:id" [id]
  (println (str "employee id: " id )))

(def app-state
  (atom {}))

(defn fetch-departments
  [url]
  (let [c (chan)]
    (go (let [deps (((<! (http/get url)) :body) :departments)]
          (>! c deps)))
    c))

(defn employee-name [firstname lastname]
  (let [disp-name (str firstname " " lastname)
        name-length (count disp-name)]
    (if (> name-length 20) (str (subs disp-name 0 17) "...") disp-name)))

(defcomponent gravatar [email-address owner {:keys [size] :as opts}]
  (display-name [_]
    "gravatar")

  (render [_]
          (html
           [:img.gravatar.img-circle
            {:src (str "http://www.gravatar.com/avatar/" (hashgen/md5 email-address) "?s=" size "&r=PG&d=mm")}])))

(defcomponent employee-info [{:keys [lastname firstname email id]}]
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
           [:div {:style {:margin-top "20px"}} (->gravatar email {:opts {:size 100}})]
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


(defcomponent department-heading [{:keys [department managerid manager-firstname manager-lastname manager-email employees]} owner opts]
  (display-name [_]
    "department-name")

  (render [_]
          (let [department-employees (filter #(not= (:id %) managerid) employees)
                rows-of-employees (partition 4 4 nil department-employees)]
            (html
             [:div#accordian.panel.panel-default.row

              [:div.panel-heading.clearfix.panel-heading
               [:div.col-md-2.col-xs-2
                [:div.col-md-4.col-xs-4 (om/build gravatar manager-email {:opts {:size 50}})]
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
                 (for [employee-row rows-of-employees]
                   [:div {:class "row accordian-inner" }
                    (for [employee-info-component (om/build-all employee-info employee-row)]
                      employee-info-component)])]]))))


(defcomponent department-list [{:keys [departments]}]
  (display-name [_]
    "department-list")

  (render [_]
   (html
    [:div.clearfix.accordian
       (dom/ul (om/build-all department-heading departments))])))


(defcomponent departments-container [app owner opts]
  (display-name [_]
    "departments-box")

  (will-mount [_]
              (go
               (let [deps (<! (fetch-departments (:url opts)))]
                 (om/transact! app #(assoc % :departments deps))
                 )))

  (render-state [_ {:keys [departments]}]
                (html
                 [:div.row
                  (->department-list app)])))


(defcomponent om-app [app owner]
  (display-name [_]
                "app")
  (render [_]
          (html
           [:div
            (->departments-container app
                      {:opts {:url "http://localhost:3030/api/departments"
                              :poll-interval 2000}})])))

(defn main []
  (let [h (History.)]
    (events/listen h EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
    (doto h (.setEnabled true)))
  (om/root om-app app-state {:target (. js/document (getElementById "app-container"))}))

