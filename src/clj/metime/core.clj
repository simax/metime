(ns metime.core
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.json :refer [wrap-json-params]]
            [buddy.auth.backends.token :refer [jws-backend token-backend]]
            [buddy.auth.backends.httpbasic :refer [http-basic-backend]]
            [buddy.auth.middleware :refer [wrap-authentication]]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [ring.util.response :refer [response redirect content-type]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.core :refer [defroutes context GET POST ANY]]
            [metime.resources :refer :all]
            [selmer.parser :refer [render-file]]
            [environ.core :refer [env]]
            [prone.middleware :as prone]
            [liberator.dev]))

(defn my-sample-handler
  [request]
  (if (authenticated? request)
    (response (format "Hello %s\n" (:identity request)))
    (response "NO ACCESS - UNAUTHORIZED\n")))

(defroutes app-routes
           (GET "/" [] (render-file "index.html" {:dev (env :dev?)}))
           (context "/api" []
             (GET "/auth-token" [] (build-auth-token))
             (GET "/sample" [] my-sample-handler)
             (ANY "/departments" [] (departments))
             (ANY "/departments/:id" [id] (department id))
             (ANY "/department/:id" [id] (department id))
             (ANY "/employees" [] (employees))
             (ANY "/employee/:id" [id] (employee id))
             (ANY "/holidays" [] (holidays))
             ;(ANY "/holidays/:id" [id] (holiday id))
             (route/resources "/")
             (route/not-found "Not Found")))

(def auth-backend (jws-backend {:secret "secret" :options {:alg :hs512}}))

(def app
  (->
    (routes app-routes)
    (wrap-authentication auth-backend)
    (wrap-keyword-params)
    (wrap-json-params)
    (prone/wrap-exceptions)
    (handler/site)
    (wrap-cors
      :access-control-allow-credentials "true"
      :access-control-allow-origin [#".*"]
      :access-control-allow-methods [:get :put :post :delete])
    (wrap-base-url)
    #_(liberator.dev/wrap-trace :header :ui)
    ))


