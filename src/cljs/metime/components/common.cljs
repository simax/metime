(ns metime.components.common
  (:require
    [re-frame.core :refer [register-handler
                           path
                           register-sub
                           dispatch
                           subscribe]]))

(defn input-value [component] (-> component .-target .-value))

(defn input-element [{:keys [id name type placeholder on-blur on-change default-value value]}]
  "An input element which updates its value on change"
  ^{:key id} [:input
              {:id            id
               :name          name
               :placeholder   placeholder
               :class         "form-control"
               :type          type
               :default-value default-value
               :on-blur       on-blur
               :on-change     on-change
               }])
