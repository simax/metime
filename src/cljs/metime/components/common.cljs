(ns metime.components.common
  (:require
            ;;[sablono.core :as html :refer-macros [html]]
            [reagent.core :as reagent :refer [atom]]
            [re-frame.core :refer [register-handler
                                   path
                                   register-sub
                                   dispatch
                                   subscribe]]))

(defn input-value [component] (-> component .-target .-value))

(defn input-element [{:keys [id name type placeholder on-change value]}]
  "An input element which updates its value on change"
   [:input
         {:id id
          :name name
          :placeholder placeholder
          :class "form-control"
          :type type
          :value value
          :on-change on-change
          }])


