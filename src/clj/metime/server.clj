(ns metime.server
  (:require [clojure.java.io :as io]
    ;;[metime.dev :refer [is-dev? inject-devmode-html browser-repl start-figwheel]]
            [metime.core :refer [app]]
            [compojure.core :refer [defroutes context ANY GET]]
            [compojure.route :refer [resources]]
            [compojure.handler :refer [api]]
            [net.cgrand.enlive-html :refer [deftemplate]]
            [ring.middleware.reload :as reload]
            [environ.core :refer [env]]
            [prone.middleware :as prone]
            [ring.adapter.jetty :refer [run-jetty]]))

;(deftemplate page
;  (io/resource "index.html") [] [:body] (if is-dev? inject-devmode-html identity))
;
;(defroutes routes
;  (resources "/")
;  (resources "/react" {:root "react"})
;  (GET "/*" req (page)))
;
;(def http-handler
;  (if is-dev?
;    (reload/wrap-reload (api #'routes))
;    (api routes)))
;
;(defn run [& [port]]
;  (defonce ^:private server
;    (do
;      (if is-dev? (start-figwheel))
;      (let [port (Integer. (or port (env :port) 10555))]
;        (print "Starting web server on port" port ".\n")
;        (run-jetty http-handler {:port port :join? false}))))
;  server)
;
;(defn -main [& [port]]
;  (run port))

(defn -main [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3030"))]
    (run-jetty app {:port port :join? false})))
