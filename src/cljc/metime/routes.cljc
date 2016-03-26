(ns metime.routes
  (:require
    [bidi.bidi :as bidi]
    #?(:cljs [pushy.core :refer [pushy set-token! start!]])
    #?(:cljs [re-frame.core :refer [register-handler
                                    path
                                    dispatch
                                    subscribe]])))

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
                   [true :not-found]]])



(def api-routes ["http://localhost:3000/api"
                 [
                  ["/authtoken" :authtoken]
                  ["/departments" {""                              :departments
                                   ["/" [#"\d*" :id] ""]           :department-by-id
                                   ["/" [#"\d*" :id] "/employees"] :department-employees}]
                  [["/department?name=" :name] :department-by-name]
                  ["/employees" :employees]
                  [["/employee?id=" [#"\d*" :id]] :employee-by-id]
                  [["/employee?email=" :email] :employee-by-email]]])




(defn parse-url [url]
  (bidi/match-route site-routes url))

#?(:cljs
   (defn dispatch-route [matched-route]
     (let [handler (:handler matched-route)]
       (dispatch [:set-active-view handler]))))

(def site-url-for (partial bidi/path-for site-routes))

(def api-endpoint-for (partial bidi/path-for api-routes))

#?(:cljs
   (def history
     (pushy dispatch-route parse-url)))

#?(:cljs
   (defn set-route-token!
     "Set the url from the given route params"
     [params]
     (set-token! history (apply site-url-for params))))

#?(:cljs
   (defn start-routing []
     (start! history)))

