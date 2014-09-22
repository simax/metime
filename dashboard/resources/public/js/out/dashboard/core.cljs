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
       ;;(let [{departments :body} (<! (http/get url))]
       (let [departments [{:department "Department 1"} {:department "Department 2"}]]
         (>! c (vec departments))))
    c))

(defn department-name [{:keys [department]} owner opts]
  (om/component
   (dom/li nil
           (dom/span nil "hello !!!!!"))))


(defn department-list [{:keys [departments]}]
 (.log js/console (println "list of departments:" departments))
  (om/component
   (apply dom/ul nil
          (om/build-all department-name departments))))

(defn departments-box [app owner opts]
  (reify

    om/IInitState
    (init-state [_]
                (let [departments (<! (fetch-departments (:url opts)))]
                        (.log js/console (println "xx:" departments))
                        (om/transact! app :departments #(conj % departments))
                  {:departments [{:department "Department 1"} {:department "Department 2"}]}))


;;    om/IWillMount
;;   (will-mount [_]
;;                (go (while true
;;                      (let [departments (<! (fetch-departments (:url opts)))]
;;                        (.log js/console (println "xx:" departments))
;;                        (om/transact! app :departments #(conj % departments))
;;                        )
;;                      (<! (timeout (:poll-interval opts)))))
;;                 )

    om/IRenderState
    (render-state [_ {:keys [departments]}]
                  (.log js/console (println "departments in app:" departments))
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

