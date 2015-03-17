(ns metime.components.common
  (:require [reagent.core :as reagent :refer [atom]]
            [re-frame.core :refer [register-handler
                                   path
                                   register-sub
                                   dispatch
                                   subscribe]]))

(defn input-value [component] (-> component .-target .-value))

(defn input-element []
  "An input element which updates its value on change"
  [{:keys [id name type property place-holder value]}]
    [:input {:id id
             :name name
             :placeholder place-holder
             :class "form-control"
             :type type
             :value value
             :on-change #(dispatch [:input-change property (input-value %)])}]
  ;;[:div "xxxx"]
  )


;; (defn email-input
;;   [email-address-atom]
;;   (input-element "email" "email" "email" email-address-atom))


;;       [:input#last-name.form-control
;;        {:type "text"
;;         :placeholder "Last name"
;;         :on-change #(dispatch [:input-change :lastname (input-value %)])
;;         :value (:lastname @employee)}]]]

;;      (com/input-element {:id first-name
;;                          :name first-name
;;                          :type "text"
;;                          :property :firstname
;;                          :place-holder "First name"
;;                          :value (:firstname @employee)})]
