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
           [:img.center-block.img-rounded.img-responsive.gravatar
            {:src (str "http://www.gravatar.com/avatar/" (hashgen/md5 email-address) "?s=" size "&r=PG&d=mm")}
;;             (r/tooltip {:placement "right"
;;                         :position-left 150
;;                         :position-top 20}
;;                        (dom/strong "Holy guacamole!")
;;                        " Check this info.")
            ])))


(defn employee-info [{:keys [lastname firstname email id]}]
  (om/component
   (html
    [:li {:class "thumbnail pull-left well" :style {:width "200px" :margin-left "10px"}}
       [:div {:class "row"}
        [:div {:style {:margin-right "10px"}}
         [:div {:class "row" :style {:height "140px"}}
          (om/build gravatar email)
          [:h5.caption.text-center (str firstname " " lastname)]]
         [:div
          [:button.btn.btn-default.pull-right
                    {:onClick (fn [e]
                                (secretary/dispatch! (str "/employees/" id))
                                (js/alert "You clicked the edit button"))} [:span.glyphicon.glyphicon-edit]
          [:button.btn.btn-default {:style {:margin-left "10px"}} [:span.glyphicon.glyphicon-log-in]]]]
         ]]])))


(defcomponent department-name [{:keys [department managerid manager-firstname manager-lastname manager-email employees]} owner opts]
  (display-name [_]
    "department-name")

  (render [_]
          (let [department-employees (filter #(not= (:id %) managerid) employees)]
            (html
             [:li.panel.panel-default

              [:div.panel-heading.clearfix
               [:div.pull-left
                [:div (om/build gravatar manager-email {:opts {:size 50}})]
                [:h5 (str manager-firstname " " manager-lastname)]
                ]
               [:h2.center department]]

              [:ul.panel-body (for [component (om/build-all employee-info department-employees)]
                                component)]
            ]))))


(defcomponent department-list [{:keys [departments]}]
  (display-name [_]
    "department-list")

  (render [_]
   (html
    [:div.clearfix
       (dom/ul (om/build-all department-name departments))])))


(defcomponent departments-box [app owner opts]
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
                  (om/build department-list app)])))


(defcomponent om-app [app owner]
  (display-name [_]
                "app")
  (render [_]
          (html
           [:div
            (om/build departments-box app
                      {:opts {:url "http://localhost:3030/api/departments"
                              :poll-interval 2000}})])))
(defn main []
  (om/root om-app app-state {:target (. js/document (getElementById "app-container"))}))

