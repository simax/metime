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


(defn -main [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3030"))]
    (run-jetty app {:port port :join? false})))
