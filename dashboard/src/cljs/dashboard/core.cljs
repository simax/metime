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
  ;;(println "fetch-departments method:")
  (let [c (chan)]
    (go (let [deps (js->clj {(<! (http/get url)) :body} :keywordize-keys :true)]
          (println deps)
          (>! c deps)))
    c))



(defn department-name [{:keys [department]} owner opts]
  (om/component
   (dom/li nil
           (dom/span nil department))))


(defn department-list [{:keys [departments]}]
  ;;(println "department-list:" departments)
  (om/component
    (apply dom/ul nil
      (om/build-all department-name (first departments)))))


(defn departments-box [app owner opts]
  (reify
   om/IWillMount
    (will-mount [_]
                ;;(println "will-mount method")
                (go
                   (let [deps (<! (fetch-departments (:url opts)))]
                     ;;(println "Will-mount-1:" deps)
                     (om/transact! app #(assoc % :departments deps))
                     ;;(println "Will-mount-2:" (:departments @app)))
                     ;;(<! (timeout (:poll-interval opts)))
                 )))


    om/IRender
    (render [{:keys [departments]}]
            ;;(println "Render method")
            (dom/h1 nil "Departments")
            (om/build department-list app)
            )))


(defn om-app [app owner]
  (om/component
   (dom/div nil
            (om/build departments-box app
                      {:opts {:url "http://localhost:3030/api/departments"
                              :poll-interval 2000}}))))

(om/root om-app app-state {:target (. js/document (getElementById "main-container"))})


(comment (def deps
  [
   {
      "employees" []
      "department" "A DEPARTMENT"
      "id" 1
   }
   {
    "employees" [
                  {
                   "managerid" 10
                   "departments_id" 2
                   "active" true
                   "enddate" nil
                   "startdate" nil
                   "email" "scottlord@ekmsystems.co.uk"
                   "lastname" "Lord"
                   "firstname" "Scott"
                   "id" 9
                   }
                  {
                   "managerid" 10
                   "departments_id" 2
                   "active" true
                   "enddate" nil
                   "startdate" nil
                   "email" "charlotteharris@ekmsystems.co.uk"
                   "lastname" "Harris"
                   "firstname" "Charlotte"
                   "id" 24
                   }
                  ]
    }])

(defn simon [_]
  (keywordize-keys deps))

(simon)
)


