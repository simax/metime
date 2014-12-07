(ns metime.core
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [goog.events :as events]
            [cljs.core.async :refer [put! <! >! chan timeout]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs-http.client :as http]
            [sablono.core :as html :refer-macros [html]]
            [om-bootstrap.button :as b]
            [om-bootstrap.panel :as p]
            [cljs-hash.md5 :as hashgen]
            [cljs-hash.goog :as gh]))


(enable-console-print!)

(def app-state
  (atom {}))

(defn fetch-departments
  [url]
  (let [c (chan)]
    (go (let [deps (((<! (http/get url)) :body) :departments)]
          (>! c deps)))
    c))

;; (go
;;   (let [deps (<! (fetch-departments "http://localhost:3030/api/departments"))]
;;    (assoc @app-state :departments deps)))

;; (defn csr-departments []
;;   (om/ref-cursor (:departments (om/root-cursor app-state))))


(defn gravatar [email-address owner {:keys [size] :as opts}]
  (om/component
   (html
    [:img {:className "center-block img-rounded img-responsive"
           :src (str "http://www.gravatar.com/avatar/" (hashgen/md5 email-address) "?s=" size "&r=PG&d=mm")}])))


(defn employee-info [{:keys [lastname firstname email]}]
  (om/component
   (html
    [:li {:className "thumbnail pull-left well" :style {:width "200px" :margin-left "10px"}}
       [:div {:className "row"}
        [:div {:style {:margin-right "10px" :height "150px;"}}
         [:div {:className "row caption text-center"}
          [:input {:id "Id" :name "Id" :type "hidden" :value ""}]
          (om/build gravatar email)
          [:h4 (str firstname " " lastname)]
          [:button {:className "btn btn-primary pull-right"} "Edit"]
          [:input {:id "Id" :name "Id" :type "hidden" :value ""}]
          [:button {:className "btn btn-default pull-left"} "Login As"]]]]])))


(defn department-name [{:keys [department manager-email employees]} owner opts]
  (om/component
   (html
    [:li {:className "panel panel-default clearfix"}
     (p/panel {:header
               (dom/div #js{:className: "row" }
                        (dom/div #js {:className "pull-right"} (om/build gravatar manager-email {:opts {:size 50}}))
                        (dom/h3 nil department))})
     [:ul {:className "panel-body"} (for [component (om/build-all employee-info employees)]
                                       component)]
     ])))


(defn department-list [{:keys [departments]}]
  (om/component
   (html
    [:div {:className "clearfix"}
       (apply dom/ul nil
              (om/build-all department-name departments))])))


(defn departments-box [app owner opts]
  (reify
   om/IWillMount
    (will-mount [_]
                (go
                 (let [deps (<! (fetch-departments (:url opts)))]
                   (om/transact! app #(assoc % :departments deps))
                   )))

    om/IRender
    (render [{:keys [departments]}]
            (html
             [:div
              [:h1 "EKM Departments"]
              (om/build department-list app)]))))


(defn om-app [app owner]
  (om/component
   (html
    [:div
     (om/build departments-box app
               {:opts {:url "http://localhost:3030/api/departments"
                       :poll-interval 2000}})])))
(defn main []
  (om/root om-app app-state {:target (. js/document (getElementById "app-container"))}))

