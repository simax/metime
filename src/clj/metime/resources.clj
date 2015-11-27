(ns metime.resources
  (:require [liberator.core :refer [resource defresource]]
            [liberator.representation :refer [as-response]]
            [metime.data.departments :as deps]
            [metime.data.employees :as emps]
            [metime.data.holidays :as hols]
            [clojure.java.io :as io]
            [clojure.walk :as walk]
            [clojure.data.json :as json]
            [metis.core :refer [defvalidator]]
            [clj-time.core :as t]
            [clj-time.format :as f]
            [clojure.set :refer :all]
            [bouncer.core :as b]
            [bouncer.validators :as v]))

;; convert the body to a reader. Useful for testing in the repl
;; where setting the body to a string is much simpler.
(defn body-as-string [ctx]
  (if-let [body (get-in ctx [:request :body])]
    (condp instance? body
      java.lang.String body
      (slurp (io/reader body)))))

;; For PUT and POST parse the body as json and store in the context
;; under the given key.
(defn parse-json [context key]
  (when (#{:put :post} (get-in context [:request :request-method]))
    (try
      (if-let [body (body-as-string context)]
        (let [data (json/read-str body)]
          [false {key data}])
        {:message "No body"})
      (catch Exception e
        (.printStackTrace e)
        {:message (format "IOException: %s" (.getMessage e))}))))

;; For PUT and POST check if the content type is json.
(defn check-content-type [ctx content-types]
  (if (#{:put :post} (get-in ctx [:request :request-method]))
    (or
      (some #{(get-in ctx [:request :headers "content-type"])}
            content-types)
      [false {:message "Unsupported Content-Type"}])
    true))


(defn requested-method [ctx http-verb]
  "Test for the HTTP verb"
  (= (get-in ctx [:request :request-method]) http-verb))


(defn make-keyword-map [string-map]
  (walk/keywordize-keys string-map))

(defn get-form-params [ctx]
  (get-in ctx [:request :form-params]))

(defn parse-number
  "Reads a number from a string. Returns nil if not a number."
  [s]
  (if (re-find #"^-?\d+\.?\d*([Ee]\+\d+|[Ee]-\d+|[Ee]\d+)?$" (.trim s))
    (read-string s)))


;;---------------------
;; Validators

;; Metis custom formatter

(defn date [m k _]
  (if (seq (k m))
    (try
      (f/parse (f/formatters :date) (k m))
      nil
      (catch Exception e
        "Invalid date"))
    ))

(defvalidator department-validator
              [:department :length {:greater-than 0 :less-than 31}]
              [:managerid :numericality {:only-integer true :greater-than 0}])

(defvalidator employee-validator
              [:firstname :length {:greater-than 0 :less-than 31}]
              [:lastname :length {:greater-than 0 :less-than 31}]
              [:email :email {:greater-than 0 :less-than 31}]
              [:department_id :numericality {:only-integer true :greater-than 0}]
              [:managerid :numericality {:only-integer true :greater-than 0}]
              [:password [:length {:greater-than-or-equal-to 8}
                          :formatted {:pattern #"(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}"
                                      :message "alpha numeric, at least one number"}
                          :confirmation {:confirm :password-confirm}]]
              [:dob :date])

(def date-format (f/formatter "yyyy-MM-dd"))

(def holiday-request-types #{"Morning" "Afternoon" "All day"})
(def holiday-request-units #{"Days" "Hours"})

(def holiday-request-validation-rules
  [:start_date [[v/required] [v/datetime date-format :message "Must be a valid date"]]
   :start_type [[v/member holiday-request-types :message (apply (partial str "Must be one of ") (interpose ", " holiday-request-types))]]
   :end_date [[v/required] [v/datetime date-format :message "Must be a valid date"]]
   :employee_id [[v/required] [v/integer] [v/positive]]
   :employee_name [[v/required :message "Employee name is required"]]
   :leave_type_id [[v/required] [v/integer] [v/positive]]
   :leave_type [[v/required :message "Leave type is required"]]
   :duration [[v/required] [v/number] [v/positive]]
   :deduction [[v/required] [v/number] [v/positive]]
   :unit [[v/member holiday-request-units :message (apply (partial str "Must be either ") (interpose " or " holiday-request-units))]]])


(defn validate-holiday-request [holiday-request]
  (let [result (apply b/validate holiday-request holiday-request-validation-rules)
        errors (first result)]
    errors))

;;---------------------


(defn employees-by-department []
  (let [data-emps (emps/get-all-employees)
        grouped-emps (group-by :department_id data-emps)

        data-deps (deps/get-all-departments)
        set-of-deps (rename (into #{} data-deps) {:id :department_id})
        set-of-emps (into #{} (map #(hash-map :department_id %1 :employees %2)
                                   (keys grouped-emps)
                                   (sort-by :lastname (vals grouped-emps))))]
    (sort-by :department (join set-of-deps set-of-emps))))


(defresource departments []
             :available-media-types ["application/edn" "application/json"]
             :allowed-methods [:get :post]
             :known-content-type? #(check-content-type % ["application/x-www-form-urlencoded" "application/json"])
             :exists? (fn [ctx]
                        (if (requested-method ctx :get)
                          [true {::departments {:departments (employees-by-department)}}]
                          ))

             :malformed? (fn [ctx]
                           (if (requested-method ctx :post)
                             (let [form-data (make-keyword-map (get-form-params ctx))
                                   validation-result (department-validator form-data)]
                               (if (not (empty? validation-result))
                                 [true {::failure-message (str validation-result)}]
                                 false))
                             false))

             :handle-malformed ::failure-message

             :post! (fn [ctx]
                      "We allow the DB to enforce its constraints here, rather than relying on the processable? descision point.
                      That way we can be sure the DB has not been changed by another thread or user between calls to processable? and post!"
                      (if (requested-method ctx :post)
                        (try
                          (when-let [new-id (deps/insert-department! (make-keyword-map (get-form-params ctx)))]
                            {::location (str "http://localhost:3030/api/departments/" new-id)})
                          (catch Exception e {::failure-message "Department already exists"}))))

             :post-redirect? false
             :handle-created (fn [ctx]
                               (if (::failure-message ctx)
                                 {:status 403 :body (::failure-message ctx)}
                                 {:location (::location ctx)}))

             :handle-ok ::departments)


(defresource department [id]
             :available-media-types ["application/edn" "application/json"]
             :allowed-methods [:get :delete :put]
             :known-content-type? #(check-content-type % ["application/x-www-form-urlencoded" "application/json"])
             :can-put-to-missing? false
             :exists? (fn [ctx]
                        (if (or (requested-method ctx :get) (requested-method ctx :put))
                          (let [department (deps/get-department-by-id id)]
                            (if (empty? department)
                              false
                              [true {::department department}]))
                          true))

             :processable? (fn [ctx]
                             (if (requested-method ctx :delete)
                               (let [department (deps/get-department-by-id id)]
                                 (if (empty? (:employees department))
                                   true
                                   [false {::failure-message "Unable to delete, the department contains employees"}]))
                               true))

             :handle-unprocessable-entity ::failure-message

             :malformed? (fn [ctx]
                           (if (requested-method ctx :put)
                             (let [form-data (make-keyword-map (get-form-params ctx))
                                   validation-result (department-validator form-data :updating)]
                               (if (seq validation-result)
                                 [true {::failure-message (str validation-result)}]
                                 false))
                             false))

             :handle-malformed ::failure-message

             :conflict? (fn [ctx]
                          (let [new-department-name (:department (make-keyword-map (get-form-params ctx)))
                                existing-department (first (deps/get-department-by-name new-department-name))]
                            (and (not (nil? existing-department)) (not= (str id) (str (:id existing-department))))))

             :handle-conflict {:department ["Department already exists"]}

             :delete! (fn [ctx]
                        (deps/delete-department! id))

             :put! (fn [ctx]
                     (let [new-data (make-keyword-map (get-form-params ctx))
                           existing-data (::department ctx)
                           updated-data (merge existing-data new-data)]
                       (deps/update-department! updated-data)
                       {::department updated-data}))

             :new? (fn [ctx] (nil? ::department))
             :respond-with-entity? (fn [ctx] (not (empty? (::department ctx))))
             :multiple-representations? false
             :handle-ok ::department
             :handle-not-found "Department not found")


(defresource employees []
             :available-media-types ["application/edn" "application/json"]
             :allowed-methods [:get :post]
             :known-content-type? #(check-content-type % ["application/x-www-form-urlencoded" "application/json"])
             :exists? (fn [ctx]
                        (if (requested-method ctx :get)
                          [true {::employees (emps/get-all-employees)}]))

             :malformed? (fn [ctx]
                           (if (requested-method ctx :post)
                             (let [form-data (make-keyword-map (get-form-params ctx))
                                   validation-result (employee-validator form-data)]
                               (if (not (empty? validation-result))
                                 [true {::failure-message (str validation-result)}]
                                 false))
                             false))

             :handle-malformed ::failure-message

             :post! (fn [ctx]
                      "We allow the DB to enforce its constraints here, rather than relying on the processable? descision point.
                      That way we can be sure the DB has not been changed by another thread or user between calls to processable? and post!"
                      (if (requested-method ctx :post)
                        (try
                          (when-let [new-id (emps/insert-employee! (make-keyword-map (get-form-params ctx)))]
                            {::location (str "http://localhost:3030/api/employees/" new-id)})
                          (catch Exception e {::failure-message "Employee already registered with this email address"}))))

             :post-redirect? false
             :handle-created (fn [ctx]
                               (if (::failure-message ctx)
                                 {:status 403 :body (::failure-message ctx)}
                                 {:location (::location ctx)}))

             :handle-ok ::employees)



(defresource employee [id]
             :available-media-types ["application/edn" "application/json"]
             :allowed-methods [:get :delete :put]
             :known-content-type? #(check-content-type % ["application/x-www-form-urlencoded" "application/json"])
             :can-put-to-missing? false
             :exists? (fn [ctx]
                        (if (or (requested-method ctx :get) (requested-method ctx :put))
                          (let [employee (emps/get-employee-by-id id)]
                            (if (empty? employee)
                              false
                              [(not (empty? employee)) {::employee employee}]))
                          true))

             :processable? (fn [ctx]
                             (if (or (requested-method ctx :delete) (requested-method ctx :put))
                               (if-let [employee (emps/get-employee-by-id id)]
                                 [true {::employee employee}]
                                 false)
                               true))

             :malformed? (fn [ctx]
                           (if (requested-method ctx :put)
                             (let [form-data (make-keyword-map (get-form-params ctx))
                                   validation-result (employee-validator form-data :updating)]
                               (if (seq validation-result)
                                 [true {::failure-message (str validation-result)}]
                                 false))
                             false))

             :handle-malformed ::failure-message

             :conflict? (fn [ctx]
                          (let [new-employee (make-keyword-map (get-form-params ctx))
                                existing-employee (emps/get-employee-by-email (:email new-employee))]
                            (and (not (nil? existing-employee)) (not= (:id new-employee) (str (:id existing-employee))))))

             :handle-conflict {:employee ["Employee already registered with this email address"]}

             :delete! (fn [ctx]
                        (emps/delete-employee! id))

             :put! (fn [ctx]
                     (let [new-data (make-keyword-map (get-form-params ctx))
                           existing-data (::employee ctx)
                           updated-data (merge existing-data new-data)]
                       (emps/update-employee! updated-data)
                       {::employee updated-data}))

             :new? (fn [ctx] (nil? ::employee))
             :respond-with-entity? (fn [ctx] (not (empty? (::employee ctx))))
             :multiple-representations? false
             :handle-ok ::employee
             :handle-not-found "Employee not found")

(defn format-holiday-request [form-data]
  (assoc form-data
    :employee_id (parse-number (:employee_id form-data))
    :leave_type_id (parse-number (:leave_type_id form-data))
    :duration (parse-number (:duration form-data))
    :deduction (parse-number (:deduction form-data))))

(defresource holidays []
             :available-media-types ["application/edn" "application/json"]
             :allowed-methods [:get :post]
             :known-content-type? #(check-content-type % ["application/x-www-form-urlencoded" "application/json"])
             :exists? (fn [ctx]
                        (if (requested-method ctx :get)
                          [true {::holidays (hols/get-holidays)}]
                          ))

             :malformed? (fn [ctx]
                           (if (requested-method ctx :post)
                             (let [form-data (-> ctx (get-form-params) (make-keyword-map) (format-holiday-request))
                                   validation-result (validate-holiday-request form-data)]
                               (if (not (empty? validation-result))
                                 [true {::failure-message (str validation-result)}]
                                 false))
                             false))

             :handle-malformed ::failure-message

             :post! (fn [ctx]
                      "We allow the DB to enforce its constraints here, rather than relying on the processable? descision point.
                      That way we can be sure the DB has not been changed by another thread or user between calls to processable? and post!"
                      (if (requested-method ctx :post)
                        (try
                          (when-let [new-id (hols/insert-holiday-request! (make-keyword-map (get-form-params ctx)))]
                            {::location (str "http://localhost:3030/api/departments/" new-id)})
                          (catch Exception e {::failure-message "Invaliday holiday request"}))))

             :post-redirect? false
             :handle-created (fn [ctx]
                               (if (::failure-message ctx)
                                 {:status 403 :body (::failure-message ctx)}
                                 {:location (::location ctx)}))

             :handle-ok ::holidays)


