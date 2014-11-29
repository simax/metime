(ns metime.core
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [goog.events :as events]
            [cljs.core.async :refer [put! <! >! chan timeout]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs-http.client :as http]
            [sablono.core :as html :refer-macros [html]]
            [om-bootstrap.button :as b]))

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

(defn toolbar [_]
  (om/component
   (html
    [:div
     (b/toolbar {}
                (b/button {} "Default")
                (b/button {:bs-style "primary"} "Primary")
                (b/button {:bs-style "success"} "Success")
                (b/button {:bs-style "info"} "Info")
                (b/button {:bs-style "warning"} "Warning")
                (b/button {:bs-style "danger"} "Danger")
                (b/button {:bs-style "link"} "Link"))]
    )))

(defn employee-info [{:keys [lastname firstname]}]
  (om/component
   (html
    [:li {:class "thumbnail pull-left well" :style {:width "200px" :margin-left "10px"}}
       [:div {:class "row"}
        [:div {:style {:margin-right "10px" :height "200px;"}}
         [:div {:class "row caption text-center"}
          [:form {:action "/admin/users/edit" :class "user-form" :method "get" :novalidate "novalidate"}
           [:input {:id "Id" :name "Id" :type "hidden" :value ""}
            [:img {:class "center-block img-rounded img-responsive"
                   :width "100"
                   :height "100"
                   :src "http://www.gravatar.com/avatar/5fb2b8e434bc5f208ecd0e50cad4605b?s 100&amp;r PG"}
             [:h4 (str firstname " " lastname)]
             [:button {:class "btn btn-primary pull-right"} "Edit"]]]]
             [:form {:action "/Admin/Impersonate" :class "impersonate-user-form" :method "post" :novalidate "novalidate"}
              [:input {:id "Id" :name "Id" :type "hidden" :value ""}]
              [:button {:class "btn btn-default pull-left"} "Login As"]]]]]])))

(defn department-name [{:keys [department employees]} owner opts]
  (om/component
   (html
    [:li
     [:div
      [:ul (for [component (om/build-all employee-info employees)]
             component)]
      ]])))


(defn department-list [{:keys [departments]}]
  (om/component
   (html
    [:div {:class "panel body"}
     [:h2 "EKM Departments"]
     (apply dom/ul #js {:className "clearfix"}
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
             [:div {:class "panel panel-default"}
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

