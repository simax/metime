(ns metime.core
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.json :refer [wrap-json-params]]
            [ring.middleware.cookies :refer [wrap-cookies]]
            [ring.util.response :refer [response file-response redirect content-type]]
            [buddy.auth.backends.token :refer [jws-backend token-backend]]
            [buddy.auth.backends.httpbasic :refer [http-basic-backend]]
            [buddy.auth.middleware :refer [wrap-authentication]]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.core :refer [defroutes context GET POST ANY]]
            [metime.resources :refer :all]
            [selmer.parser :refer [render-file]]
            [environ.core :refer [env]]
            [prone.middleware :as prone]
            [liberator.dev]
            [metime.security :as sec]))

(defn my-sample-handler
  [request]
  (redirect "/index.html"))

(defroutes app-routes
           ;(GET "/" [] (render-file "index.html" {:dev (env :dev?)}))
           (GET "/" [] (file-response "index.html" {:root "public"}))
           (context "/api" []
             (GET "/auth-token" [] (build-auth-token))
             (ANY "/departments" [] (departments))
             (ANY "/departments/:id" [id] (department id))
             (ANY "/department/:id" [id] (department id))
             (ANY "/employees" [] (employees))
             (ANY "/employee/:id" [id] (employee id))
             (ANY "/holidays" [] (holidays))
             ;(ANY "/holidays/:id" [id] (holiday id))
             (route/resources "/")
             (ANY "*" [] (file-response "index.html" {:root "public"}))
             (route/not-found "Not Found")))

(def auth-backend (jws-backend {:secret "secret" :options {:alg :hs512}}))

(def app
  (->
    (routes app-routes)
    (wrap-authentication auth-backend)
    (prone/wrap-exceptions)
    (handler/site) ; Multiple standard middleware wrap_params, wrap_cookies etc
    (wrap-cors
      :access-control-allow-credentials "true"
      :access-control-allow-origin [#".*"]
      :access-control-allow-methods [:get :put :post :delete])
    #_(liberator.dev/wrap-trace :header :ui)
    ))


