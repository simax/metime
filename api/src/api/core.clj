(ns api.core
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.core :refer [defroutes context ANY]]
            [api.resources :refer :all]))


(defroutes app-routes
  (context "/api" []
    (ANY "/departments"  [] (get-departments))
    (ANY "/employees"  [] (get-employees))
    ;;(ANY "/employee/:id" [id] (employees/get-by-id id))
    (route/resources "/")
    (route/not-found "Not Found")))

(def app
  (-> (routes app-routes)
      (handler/site)
      (wrap-base-url)))


