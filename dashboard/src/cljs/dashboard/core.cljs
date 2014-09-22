(ns dashboard.core
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [goog.events :as events]
            [cljs.core.async :refer [put! <! >! chan timeout]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs-http.client :as http]))

(enable-console-print!)

(def app-state
  (atom {}))

(defn fetch-departments
  [url]
  (let [c (chan)]
    (go
       ;;(let [{departments :body} (<! (http/get url))]
       (let [departments [{:department "Department 1"} {:department "Department 2"}]]
         (.log js/console (println "fetch-departments:" departments)
         (>! c (vec departments)))))
    c))

(defn department-name [{:keys [department]} owner opts]
  (om/component
   (dom/li nil
           (dom/span nil department))))


(defn department-list [{:keys [departments]}]
 (.log js/console (println "department-list:" departments))
  (om/component
   (apply dom/ul nil
          (om/build-all department-name (first departments)))))

(defn departments-box [app owner opts]
  (reify


   om/IWillMount
    (will-mount [_]
                (go
                   (let [deps (<! (fetch-departments (:url opts)))]
                     (.log js/console (println "Will-mount-1:" deps))
                     (om/transact! app :departments #(conj % deps))
                     (.log js/console (println "Will-mount-2:" (:departments @app)))
                   ;;(<! (timeout (:poll-interval opts)))
                   )))


    om/IRenderState
    (render-state [_ {:keys [departments]}]
                  (.log js/console (println "Render-state:" departments))
                  (dom/h1 nil "Departments")
                  (om/build department-list app))))

;;    om/IRender
;;    (render [_]
;;            (dom/h1 nil "Departments"))
;;    ))
            ;;(.log js/console (println "departments in app:" (:departments app)))
            ;;(om/build department-list app)


(defn om-app [app owner]
  (om/component
   (dom/div nil
            (om/build departments-box app
                      {:opts {:url "http://localhost:3030/api/departments"
                              :poll-interval 2000}}))))

(om/root om-app app-state {:target (. js/document (getElementById "main-container"))})

