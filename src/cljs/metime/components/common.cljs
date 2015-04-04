(ns metime.components.common
  (:require
            [reagent.core :as reagent :refer [atom]]
            [re-frame.core :refer [register-handler
                                   path
                                   register-sub
                                   dispatch
                                   subscribe]]))

(defn input-value [component] (-> component .-target .-value))

(defn input-element [{:keys [id name type placeholder on-change default-value]}]
  "An input element which updates its value on change"
  ;;(js/console.log (str "value: " (empty? value)))
  (if (or (and (string? default-value) (seq default-value))
          (number? default-value))
    [:input
     {:id id
      :name name
      :placeholder placeholder
      :class "form-control"
      :type type
      :default-value default-value
      :on-change on-change
      }]))