(ns metime.core
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.json :refer [wrap-json-params]]
            [ring.middleware.cookies :refer [wrap-cookies]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.util.response :as resp] ;;  file-response redirect content-type
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
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
            [clojure.java.io :as io]))


;{:status 200
; :headers {"Content-Type" "text/html; charset=utf-8"}
; :body (slurp (-> "public/index.html" io/resource))}

(def auth-backend (jws-backend {:secret "secret" :options {:alg :hs512}}))


(defn root-handler [_]
  (resp/content-type (resp/resource-response "/index.html" {:root "public"}) "text/html"))

(defroutes app-routes
           (GET "/" [] root-handler)
           (GET "/fake" [] (str "<h1>" "Meaningless URL just to test we get a response"  "</h1>"))

           (context "/api" []
             (GET "/auth-token" [] (build-auth-token))
             (ANY "/departments" [] (departments))
             (ANY "/departments/:id" [id] (department id))
             (ANY "/department/:id" [id] (department id))
             (ANY "/employees" [] (employees))
             (ANY "/employee/:id" [id] (employee id))
             (ANY "/holidays" [] (holidays))
             ;(ANY "/holidays/:id" [id] (holiday id))
             (route/not-found "Not Found"))
           ;; All other routes failed. Just serve the app again
           ;; and let the client take over.
           (ANY "*" [] root-handler)
           )

(def app
  (->
    app-routes
    (wrap-reload)
    (wrap-authentication auth-backend)
    (prone/wrap-exceptions)
    ; wrap-defaults adds lots of standard middleware such as wrap_params, wrap_cookies etc
    (wrap-defaults site-defaults)
    ;(wrap-keyword-params)
    ;(wrap-json-params)
    (wrap-cors
      :access-control-allow-credentials "true"
      :access-control-allow-origin [#".*"]
      :access-control-allow-methods [:get :put :post :delete])
    #_(liberator.dev/wrap-trace :header :ui)
    ))


