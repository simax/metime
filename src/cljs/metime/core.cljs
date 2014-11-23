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

;; (defonce app-state (atom {:text "Hello Chestnut!"}))

;; (defn main []
;;   (om/root
;;     (fn [app owner]
;;       (reify
;;         om/IRender
;;         (render [_]
;;                 (om/build toolbar {})
;;                 )))
;;     app-state
;;     {:target (. js/document (getElementById "main-container"))}))


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
    [:li
     [:span {:class "employee-name"} (str firstname " " lastname)]])))

(defn department-name [{:keys [department employees]} owner opts]
  (om/component
   (html
    [:li
     [:div
      [:span {:class "department-name"} department]
      [:ul (for [component (om/build-all employee-info employees)]
             component)]]])))

(defn department-list [{:keys [departments]}]
  (om/component
    (dom/div nil
      (dom/h2 nil "Departments")
       (apply dom/ul nil
        (om/build-all department-name departments)))))

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
            (om/build department-list app)
            )))


(defn om-app [app owner]
  (om/component
   (html
    [:div
     (om/build departments-box app
               {:opts {:url "http://localhost:3030/api/departments"
                       :poll-interval 2000}})])))

(om/root om-app app-state {:target (. js/document (getElementById "main-container"))})

