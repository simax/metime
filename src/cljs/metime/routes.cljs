(ns metime.routes
  (:require
    [bidi.bidi :as bidi]
    [pushy.core :as pushy]
    [re-frame.core :refer [register-handler
                           path
                           dispatch
                           subscribe]]))


(def routes ["/" [
                  [""              :home]
                  ["tables"        :tables]
                  ["calendar"      :calendar]
                  ["file-manager"  :file-manager]
                  ["user"          :user]
                  ["login"         :login]
                  ["test"          { "" :test
                                    [ "/level-2"  ] :test-level-2-component}]
                  ["employees"     { "" :employees
                                    [ "/" [#"\d*" :id] ] :edit-employee}]
                  [true            :not-found]
                  ]
             ])

(defn parse-url [url]
  (bidi/match-route routes url))

(defn dispatch-route [matched-route]
  (dispatch [:set-active-view (:handler matched-route)]))

(def url-for (partial bidi/path-for routes))

(def history
  (pushy/pushy dispatch-route parse-url))

(defn app-routes []
  (pushy/start! history))

