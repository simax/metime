(ns metime.routes
  (:require
    [bidi.bidi :as bidi]
    [pushy.core :as pushy]
    [re-frame.core :refer [register-handler
                           path
                           dispatch
                           subscribe]]))


(def routes ["/" [
                  ["" :home]
                  ["tables" :tables]
                  ["calendar" :calendar]
                  ["file-manager" :file-manager]
                  ["user" :user]
                  ["login" :login]
                  ["test" {""           :test
                           ["/level-2"] :test-level-2}]
                  ["employees" {""                 :employees
                                ["/" [#"\d*" :id]] :employee-editor}]
                  [true :not-found]
                  ]
             ])

(defn parse-url [url]
  (bidi/match-route routes url))

(defn dispatch-route [matched-route]
  (let [handler (:handler matched-route)]
    (dispatch [:set-active-view handler])))

(def url-for (partial bidi/path-for routes))

(def history
  (pushy/pushy dispatch-route parse-url))

(defn set-route-token!
  "Set the url from the given route params"
  [params]
  (pushy/set-token! history (apply url-for params)))

(defn start-routing []
  (pushy/start! history))

