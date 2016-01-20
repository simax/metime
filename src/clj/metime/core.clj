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
            [ring.util.response :as resp]                   ;;  file-response redirect content-type
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

(defn test-handler [id email]
  (resp/content-type (resp/resource-response "/index.html" {:root "public"}) "text/html"))

(defroutes app-routes
           (GET "/" [] root-handler)
           (GET "/fake" [] (str "<h1>" "Meaningless URL just to test we get a response" "</h1>"))

           (context "/api" []

             (GET "/simon" [] (fn [_] {:status 200 :body "Some text from Simon"}))
             (GET "/authtoken" [] (build-auth-token))       ; (fn [_] {:status 200 :body "Some text from Simon"})
             (ANY "/departments/employees" [] (departments))
             (ANY "/departments" [] (departments-only))
             (ANY "/departments/:id" [id] (department id))
             ;(ANY "/department/:id" [id] (department id))
             (ANY "/employees" [] (employees))
             (ANY "/employees/:id{[0-9]+}" [id] (employee id))
             (ANY "/test" [id email] (test-handler id email))
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
    (wrap-reload)                                           ; This still doesn't seem to work as expected
    (wrap-authentication auth-backend)
    (prone/wrap-exceptions)
    ; wrap-defaults adds lots of standard middleware
    ; such as wrap_params, wrap_cookies etc
    ; It also adds wrap-anti-forgery which will cause
    ; issues if the api endpoints are NOT called from the
    ; browser. Because the anti-forgery token will not exist.
    ; Although it could be passed in the header?
    ; Need to understand this better.
    ; Need to read https://github.com/ring-clojure/ring-anti-forgery.

    ; NOT having wrap-defaults seems to break the front end.
    (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))
    (wrap-params)
    (wrap-json-params)
    (wrap-cors
      :access-control-allow-credentials "true"
      :access-control-allow-origin [#".*"]
      :access-control-allow-methods [:get :put :post :delete])
    #_(liberator.dev/wrap-trace :header :ui)
    ))


