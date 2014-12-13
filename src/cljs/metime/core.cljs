(ns metime.core
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [goog.events :as events]
            [cljs.core.async :refer [put! <! >! chan timeout]]
            [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [cljs-http.client :as http]
            [sablono.core :as html :refer-macros [html]]
            [om-bootstrap.button :as b]
            [om-bootstrap.panel :as p]
            [om-bootstrap.random :as r]
            [cljs-hash.md5 :as hashgen]
            [cljs-hash.goog :as gh]
            [secretary.core :as secretary :refer-macros [defroute]]))


(enable-console-print!)

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

(defcomponent gravatar [email-address owner {:keys [size] :as opts}]
  (display-name [_]
    "gravatar")

  (render [_]
          (html
           [:img.gravatar.img-circle
            {:src (str "http://www.gravatar.com/avatar/" (hashgen/md5 email-address) "?s=" size "&r=PG&d=mm")}
;;             (r/tooltip {:placement "right"
;;                         :position-left 150
;;                         :position-top 20}
;;                        (dom/strong "Holy guacamole!")
;;                        " Check this info.")
            ])))


(defcomponent employee-info [{:keys [lastname firstname email id]}]
  (display-name [_]
    "employee-info")

  (render [_]
    (let [edit (fn [e]
                 (secretary/dispatch! (str "/employees/" id))
                 (js/alert "You clicked the edit button"))]
   (html
     [:div {:class "col-sm-3 col-lg-3"}
       [:div {:class "dash-unit"}
         [:div {:class "thumbnail" :style {:margin-top "20px"}}
          [:h1 (str firstname " " lastname)]
          [:div {:style {:margin-top "20px"} :onClick edit} (->gravatar email {:opts {:size 100}})]
          [:h5.text-center "Days remaining:"]
          [:div {:class "info-user"}
						[:span {:aria-hidden "true" :class "li_user fs1"}]
						[:span {:aria-hidden "true" :class "li_calendar fs1"}]
						[:span {:aria-hidden "true" :class "li_mail fs1"}]
					]]
        ]]))))


(defcomponent department-heading [{:keys [department managerid manager-firstname manager-lastname manager-email employees]} owner opts]
  (display-name [_]
    "department-name")

  (render [_]
          (let [department-employees (filter #(not= (:id %) managerid) employees)
                rows-of-employees (partition 4 4 nil department-employees)]
            (html
             [:div.panel.panel-default.accordian-group

              [:div.panel-heading.clearfix.accordian-heading
               [:div.pull-left
                [:div (om/build gravatar manager-email {:opts {:size 50}})]
                [:h5 (str manager-firstname " " manager-lastname)]
               ]
               [:h2 department]]

              [:div.panel-body.accordian-body.in {:style {:height "auto"}}
                 (for [employee-row rows-of-employees]
                   [:div {:class "row accordian-inner"}
                    (for [component (om/build-all employee-info employee-row)]
                      component)])]]))))


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
                 [:div
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
  (om/root om-app app-state {:target (. js/document (getElementById "app-container"))}))

