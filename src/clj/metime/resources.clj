(ns metime.resources
  (:require [liberator.core :refer [resource defresource]]
            [liberator.representation :refer [as-response]]
            [metime.data.departments :as deps]
            [metime.data.employees :as emps]
            [cheshire.core :as json]))


(defresource get-departments []
  :available-media-types ["application/json"]

  :as-response (fn [d ctx]
                 (-> (as-response d ctx) ;; default implementation
                     (assoc-in [:headers "Access-Control-Allow-Origin"] "http://localhost:10555")
                     (assoc-in [:headers "Access-Control-Allow-Credentials"] "true")))

  :exists? (fn [ctx]
              [true
               {::departments {:departments (deps/get-all-with-employees)}}])

  :handle-ok ::departments)


(defresource get-employees []
  :available-media-types ["application/json"]

  :as-response (fn [d ctx]
                 (-> (as-response d ctx) ;; default implementation
                     (assoc-in [:headers "Access-Control-Allow-Origin"] "http://localhost:10555")
                     (assoc-in [:headers "Access-Control-Allow-Credentials"] "true")))

  :exists? (fn [ctx]
              {::employees (emps/get-all)})
  :handle-ok ::employees)

(defresource get-employee-by-id [id]
  :available-media-types ["application/json"]

  :as-response (fn [d ctx]
                 (-> (as-response d ctx) ;; default implementation
                     (assoc-in [:headers "Access-Control-Allow-Origin"] "http://localhost:10555")
                     (assoc-in [:headers "Access-Control-Allow-Credentials"] "true")))

  :exists? (fn [ctx]
              {::employee (emps/get-employee-by-id id)})

  :handle-ok ::employee)
