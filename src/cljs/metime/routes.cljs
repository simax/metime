(ns metime.routes
  (:require
    [bidi.bidi :as bidi]
    [pushy.core :as pushy]
    [re-frame.core :refer [register-handler
                                   path
                                   dispatch
                                   subscribe]]))


(def routes ["/" {
                  ""              :home
                  "employees/"    { "" :employees
                                   [:id "/"] :edit-employee}
                  "tables"        :tables
                  "calendar"      :calendar
                  "file-manager"  :file-manager
                  "user"          :user
                  "login"         :login
                  "logout"        :logout
                  }])

(defn- parse-url [url]
  (bidi/match-route routes url))

(defn- dispatch-route [matched-route]
  (let [panel-name (:handler matched-route)]
    (if (= panel-name :home)
      (dispatch [:set-active-view nil panel-name])
      (dispatch [:set-active-view panel-name panel-name]))))

(defn app-routes []
  (pushy/start! (pushy/pushy dispatch-route parse-url)))

(def url-for (partial bidi/path-for routes))
