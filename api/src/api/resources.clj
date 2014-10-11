(ns api.resources
  (:require [liberator.core :refer [resource defresource]]
            [liberator.representation :refer [as-response]]
            [api.data.departments :as deps]
            [api.data.employees :as emps]
            [cheshire.core :as json]))


(defresource get-departments []
  :available-media-types ["text/plain"
                          "application/json"]

  :as-response (fn [d ctx]
                 (-> (as-response d ctx) ;; default implementation
                     (assoc-in [:headers "Access-Control-Allow-Origin"] "http://localhost:3000")
                     (assoc-in [:headers "Access-Control-Allow-Credentials"] "true")))


  :exists? (fn [ctx]
              {::departments
               ;;(deps/get-all)
               (json/encode {:departments [{:department "Design"} {:department "Development"}]})
               })


  :handle-ok ::departments)


(defresource get-employees []
  :available-media-types ["text/plain"
                          "application/json"]

  :exists? (fn [ctx]
              {::employees (emps/get-all)})
  :handle-ok ::employees)
