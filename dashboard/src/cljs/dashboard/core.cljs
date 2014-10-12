(ns dashboard.core
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [goog.events :as events]
            [cljs.core.async :refer [put! <! >! chan timeout]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs-http.client :as http]
            ))

(enable-console-print!)

(def app-state
  (atom {}))

(defn fetch-departments
  [url]
  (let [c (chan)]
    (go (let [deps (((<! (http/get url)) :body) :departments)]
          (>! c deps)))
    c))


(defn department-name [{:keys [department]} owner opts]
  (om/component
   (dom/li nil
           (dom/span #js {:className "department"} department))))


(defn department-list [{:keys [departments]}]
  (om/component
    (apply dom/ul nil
      (om/build-all department-name departments))))


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
            (dom/h1 nil "Departments")
            (om/build department-list app))))


(defn om-app [app owner]
  (om/component
   (dom/div nil
            (om/build departments-box app
                      {:opts {:url "http://localhost:3030/api/departments"
                              :poll-interval 2000}}))))

(om/root om-app app-state {:target (. js/document (getElementById "main-container"))})

