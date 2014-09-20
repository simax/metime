(ns api.resources
  (:require [liberator.core :refer [resource defresource]]
            [api.data.departments :as departments]))


(defresource get-departments []
  :available-media-types ["text/plain"
                          "application/json"]

  :exists? (fn [ctx]
              {::departments (departments/get-all)})
  :handle-ok ::departments)
