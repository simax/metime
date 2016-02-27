(ns metime.server
  (:require
            [metime.core :refer [app]]
            [compojure.core :refer [defroutes context ANY GET]]
            [compojure.route :refer [resources]]
            [compojure.handler :refer [api]]
            [net.cgrand.enlive-html :refer [deftemplate]]
            [environ.core :refer [env]]
            [ring.adapter.jetty :refer [run-jetty]]))


(defn main [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (run-jetty app {:port port :join? false})))
