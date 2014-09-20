(ns api.resources
  (:require [liberator.core :refer [resource defresource]]
            [api.data.departments :as deps]
            [api.data.employees :as emps]))


(defresource get-departments []
  :available-media-types ["text/plain"
                          "application/json"]

  :exists? (fn [ctx]
              {::departments (deps/get-all)})
  :handle-ok ::departments)


(defresource get-employees []
  :available-media-types ["text/plain"
                          "application/json"]

  :exists? (fn [ctx]
              {::employees (emps/get-all)})
  :handle-ok ::employees)
