(ns dashboard.core
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [goog.events :as events]
            [cljs.core.async :refer [put! <! >! chan timeout]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs-http.client :as http]))

(enable-console-print!)

(println "Hello world!")

(def app-state
  (atom {}))

(defn fetch-departments
  [url]
  (let [c (chan)]
    (go
       (let [{departments :body} (<! (http/get url))]
       ;;(let [departments [{:department "Departement 1"} {:department "Departement 2"}]]
         (>! c (vec departments))))
    c))

(defn department [{:keys [department]} owner opts]
  (om/component
   (dom/li nil department)))


(defn department-list [{:keys [departments]}]
  (om/component
   (apply dom/ul nil
          (om/build-all department departments))))

(defn departments-box [app owner opts]
  (reify
    om/IInitState
    (init-state [_]
                ;;(om/transact! app [:departments] (fn [] []))
                )
    om/IWillMount
    (will-mount [_]
                (go (while true
                      (let [departments (<! (fetch-departments (:url opts)))]
                        (.log js/console (pr-str departments))
                        ;;(om/update! app #(assoc % :departments departments))
                        )
                      (<! (timeout (:poll-interval opts))))))
    om/IRender
    (render [_]
            (dom/h1 nil "Departments")
            (om/build department-list app))))


(defn om-app [app owner]
  (om/component
   (dom/div nil
            (om/build departments-box app
                      {:opts {:url "http://localhost:3030/api/departments"
                              :poll-interval 2000}}))))

(om/root om-app app-state {:target (. js/document (getElementById "main-container"))})


