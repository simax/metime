(ns metime.core
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.cors :refer [wrap-cors]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.core :refer [defroutes context ANY]]
            [metime.resources :refer :all]
            [prone.middleware :as prone]
            [liberator.dev]))


(defroutes app-routes
  (context "/api" []
    (ANY "/departments"  [] (departments))
    (ANY "/departments"  [] (departments))
    (ANY "/employees" [] (employees nil))
    (ANY "/employees/:id" [id] (employees id))
    (route/resources "/")
    (route/not-found "Not Found")))

(def app
  (->
     (routes app-routes)
     (prone/wrap-exceptions)
     (handler/site)
     (wrap-cors
                :access-control-allow-origin [#".*"]
                :access-control-allow-methods [:get :put :post :delete])
     (wrap-base-url)
     #_(liberator.dev/wrap-trace :header :ui)
   ))


