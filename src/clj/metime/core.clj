(ns metime.core
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.core :refer [defroutes context ANY]]
            [metime.resources :refer :all]
            [prone.middleware :as prone]
            ))


(defroutes app-routes
  (context "/api" []
    (ANY "/departments"  [] (get-departments))
    (ANY "/employees"  [] (get-employees))
    ;;(ANY "/employee/:id" [id] (employees/get-by-id id))
    (route/resources "/")
    (route/not-found "Not Found")))

(def app
  (->
     (routes app-routes)
     (prone/wrap-exceptions)
     (handler/site)
     (wrap-base-url)))


