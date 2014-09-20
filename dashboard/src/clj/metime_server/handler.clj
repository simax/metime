(ns metime-server.handler
  (:require
   [hiccup.middleware :refer [wrap-base-url]]
   [ring.util.response :as resp]
   [ring.middleware.params :refer [wrap-params]]
   [ring.middleware.resource :refer [wrap-resource]]
   [ring.middleware.file-info :refer [wrap-file-info]]
   [compojure.core :refer [defroutes ANY GET POST PUT DELETE]]
   [compojure.route :refer [not-found]]
   [compojure.handler :as handler]
   [ring.middleware.json :as middleware]))


(defroutes routes
  (GET "/" [] (resp/resource-response "index.html" {:root "public"}))
  (GET  "/departments" [] (resp/response [{:department "Departement 1"}
                                          {:department "Departement 2"}]))
  (compojure.route/resources "/")
  (not-found "Page not found."))


(def sim-methods {"PUT"     :put
                  "DELETE"  :delete})

(defn wrap-simulated-methods [hndlr]
  (fn [req]
    (if-let [method (and (= :post (:request-method req))
                         (sim-methods (get-in req [:params "_method"])))]
      (hndlr (assoc req :request-method method))
      (hndlr req))))


(def app-routes
  (wrap-base-url
    (wrap-file-info
     (wrap-resource
       (wrap-params
        (wrap-simulated-methods routes)) "static"))))


(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))
