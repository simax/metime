(ns metime.components.common)

(defn input-element
  "An input element which updates its value on change"
  [id name type value]
  [:input {:id id
           :name name
           :class "form-control"
           :type type
           :required ""
           :value @value
           :on-change #(reset! value (-> % .-target .-value))}])

(defn email-input
  [email-address-atom]
  (input-element "email" "email" "email" email-address-atom))
