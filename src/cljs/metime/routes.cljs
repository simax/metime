(ns metime.routes
  (:require
    [bidi.bidi :as bidi]
    [pushy.core :as pushy]
    [re-frame.core :refer [register-handler
                           path
                           dispatch
                           subscribe]]))

;TODO: Add routes here for api endpoints so we can use url-for and can avoid them
; being hardcoded throughout the codebase

(def site-routes ["/"
                  [
                   ["" :home]
                   ["tables" :tables]
                   ["calendar" :calendar]
                   ["file-manager" :file-manager]
                   ["user" :user]
                   ["login" :login]
                   ["test" {""           :test
                            ["/level-2"] :test-level-2}]
                   ["employee" {"/add" :employee-add}]
                   ["employees" {""                 :employees
                                 ["/" [#"\d*" :id]] :employee-editor}]
                   [true :not-found]
                   ]
                  ])

(def api-routes ["http://localhost:3000/api"
                 [
                  ["/authtoken" :authtoken]
                  ["/departments" {""             :departments-only
                                   ["/employees"] :departments-and-employees}]
                  ["/employees" :employees]
                  [["/employee?id=" [#"\d*" :id]] :employee-by-id]
                  [["/employee?email=" :email] :employee-by-email]
                  ]
                 ])


(defn parse-url [url]
  (bidi/match-route site-routes url))

(defn dispatch-route [matched-route]
  (let [handler (:handler matched-route)]
    (dispatch [:set-active-view handler])))

(def site-url-for (partial bidi/path-for site-routes))

(def api-endpoint-for (partial bidi/path-for api-routes))

(def history
  (pushy/pushy dispatch-route parse-url))

(defn set-route-token!
  "Set the url from the given route params"
  [params]
  (pushy/set-token! history (apply site-url-for params)))

(defn start-routing []
  (pushy/start! history))

