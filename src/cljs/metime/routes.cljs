(ns metime.routes
  (:require
    [bidi.bidi :as bidi]
    [pushy.core :as pushy]
    [metime.navigation.handlers]
    [re-frame.core :refer [register-handler
                           path
                           dispatch
                           subscribe]]))


(def routes ["/" [
                  [""              :home]
                  ["employees"     { "" :employees
                                   [ "/" [#"\d*" :id] ] :edit-employee}]
                  ["tables"        :tables]
                  ["calendar"      :calendar]
                  ["file-manager"  :file-manager]
                  ["user"          :user]
                  ["login"         :login]
                  [true            :not-found]
                  ]
             ])

(defn- parse-url [url]
  (bidi/match-route routes url))

(defn- dispatch-route [matched-route]
  (let [panel-name (:handler matched-route)]
    (if (= panel-name :home)
      (dispatch [:set-active-view nil panel-name])
      (dispatch [:set-active-view panel-name panel-name]))))

(def url-for (partial bidi/path-for routes))

(def history
  (pushy/pushy dispatch-route parse-url))

(defn app-routes []
  (pushy/start! history))

