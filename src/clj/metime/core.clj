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


(defn create-auth-token [req]
  (let [[ok? res] (create-auth-token-service (:auth-conf req)
                                             (:params req))]
    (if ok?
      {:status 201 :headers {"Content-Type" "application/json"} :body (str "{ token" ":" (:token res) "}")}
      {:status 401 :headers {"Content-Type" "application/json"} :body (str "{ message" ":" (:message res) "}")})))

(defn my-sample-handler
  [request]
  (if (authenticated? request)
    (response (format "Hello %s\n" (:identity request)))
    (response "NO ACCESS - UNAUTHORIZED\n")))

(defroutes app-routes
           (GET "/" [] (render-file "index.html" {:dev (env :dev?)}))
           (context "/api" []
             (POST "/create-auth-token" [] create-auth-token)
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

;(defn my-authfn
;  [request authdata]
;  (let [username (:username authdata)
;        password (:password authdata)]
;    username))

;(def tokens {:2f904e245c1f5 "Admin"
;             :45c1f5e3f05d0 "foouser"})

;(defn my-authfn
;  [request token]
;  (let [token (keyword token)]
;    (get tokens token nil)))

;(def auth-backend (token-backend {:authfn my-authfn}))
;(def auth-backend (http-basic-backend {:realm "MyApi" :authfn my-authfn}))

;(defn my-authentication-wrapper [handler]
;  (fn [request]
;    (handler (assoc request :identity true))))

(def auth-backend (jws-backend {:secret "secret" :options {:alg :hs512}}))

(def app
  (->
    (routes app-routes)
    (wrap-authentication auth-backend)
    ;(my-authentication-wrapper)
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


