(ns metime.resources
  (:require [liberator.core :refer [resource defresource]]
            [liberator.representation :refer [as-response]]
            [metime.data.leave-types :as ltypes]
            [metime.data.departments :as deps]
            [metime.data.employees :as emps]
            [metime.data.holidays :as hols]
            [metime.routes :as routes]
            [metime.security :as sec]
            [clojure.java.io :as io]
            [clojure.walk :as walk]
            [clojure.data.json :as json]
            [clj-time.core :as t]
            [clj-time.format :as f]
            [clj-time.coerce :as c]
            [clojure.set :refer :all]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [bouncer.core :as b]
            [bouncer.validators :as v :refer [defvalidator]]
            [metime.data.database :as db]
            [buddy.hashers :as hashers]
            [metime.formatting :as fmt]
            [camel-snake-kebab.core :as csk]
            [camel-snake-kebab.extras :refer [transform-keys]]
            [taoensso.timbre :as timbre
             :refer (log trace debug info warn error fatal report
                         logf tracef debugf infof warnf errorf fatalf reportf
                         spy get-env log-env)]))

;; convert the body to a reader. Useful for testing in the repl
;; where setting the body to a string is much simpler.
(defn body-as-string [ctx]
  (if-let [body (get-in ctx [:request :body])]
    (condp instance? body
      String body
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

(defn get-posted-data [ctx]
  "Retreive posted data from request. The presence of
  wrap-params and wrap-json-params middleware plcses data under the :request :params key
  if data is posted as json or form-url-encoded."
  (get-in ctx [:request :params]))

(defn parse-number
  "Reads a number from a string. Returns nil if not a number."
  [s]
  (if (re-find #"^-?\d+\.?\d*([Ee]\+\d+|[Ee]-\d+|[Ee]\d+)?$" (.trim s))
    (read-string s)))


;;---------------------
;; Security

;; Using an auth token means we don't have to keep
;; Checking the DB on every request. Just create an auth token
;; on Login. Make it expire after a day or so? (or make that configurable?)
;; Once the auth token expires, force the user to login.
;; In a SPA and REST service set-up (like this is) and all done over SSL
;; its unlikely that the auth token will ever be compromised.
;; Timetastic uses cookies. (30 day?)



(defn secured-resource []
  {:authorized? #(authenticated? (:request %))
   :allowed?    (fn [_]
                  ;; Not currently doing any further role based authorization
                  true)})

;;---------------------
;; Validators


;; TODO: Write tests for validations ...

(def date-format (f/formatter "dd-MM-yyyy"))

(defvalidator date-before-today
              {:default-message-format "%s can't be in the future"}
              [date-to-validate]
              (<= (c/to-long (f/parse-local-date date-format date-to-validate)) (c/to-long (t/today))))

(defn validate-department [dept]
  (let [department-validation-rules [:department [[v/required] [v/max-count 30]]
                                     :manager-id [[v/required]]]
        result (apply b/validate dept department-validation-rules)
        errors (first result)]
    errors))

(defn validate-leave-type [leave-type]
  (let [leave-type-validation-rules [:leave-type [[v/required] [v/max-count 30]]]
        result (apply b/validate leave-type leave-type-validation-rules)
        errors (first result)]
    errors))

(defn validate-user-format [user]
  (let [user-validation-rules [:email [[v/required] [v/email]]
                               :password [[v/required]]]
        result (apply b/validate user user-validation-rules)
        errors (first result)]
    errors))

(defvalidator password-confirmation
              {:default-message-format "Password and confirmation must match"}
              [confirmation-value password-key subject]
              (= confirmation-value (get subject (keyword password-key) "invalid confirmation")))

(defvalidator department-exists
              {:default-message-format "Department doesn't exist"}
              [dept-id]
              (let [id (if (instance? String num)
                         (parse-number dept-id)
                         dept-id)]
                (some? (deps/get-department-by-id db/db-spec {:id id}))))

;TODO: Need to improve duplicate email checking. Duplicate if current id
;      and email don't match an existing id and email
;

(defvalidator unique-email
              {:default-message-format "Email already exists"}
              [email-value id-key subject]                  ; subject is the employee map
              (let [id (get subject (keyword id-key))]
                (when-let [emp (emps/get-employee-by-email db/db-spec {:email email-value})]
                  (= id (:id emp)))
                true))


;; TODO There is a parse-num defined in this ns and another in a CLJS ns.
;;      There should only be one in a CLJC.
(defn is-zero? [num]
  "If num is a Long, decide if it's zero or not.
   If num is a String, parse it first, then decide if it's zero or not.
   Otherwise return false."
  (cond
    (instance? Long num) (zero? num)
    (instance? String num) (zero? (parse-number num))
    :else false))

(defn is-new-employee? [emp]
  "The employee is deemed new if the :id is not supplied or it's supplied and zero"
  (if-let [emp-id (:id emp)]
    (is-zero? emp-id)
    true))

(def employee-validation-set
  [
   :firstname [[v/string] [v/min-count 1] [v/max-count 30]]
   :lastname [[v/string] [v/min-count 1] [v/max-count 30]]
   :password [[v/matches #"(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}" :message "Password must be alpha numeric with at least one number"]]
   :department-id [[department-exists]]
   :dob [[v/datetime date-format :message "Must be a valid date"] [date-before-today :message "Date of birth can't be in the future"]]
   ;:startdate [[v/datetime date-format :message "Must be a valid date"] [date-before-today :message "Start date can't be in the future"]]
   :is-approver [[v/boolean]]])


(defn make-rule-required [validation-rule]
  (reduce conj validation-rule [[v/required]]))

(defn append-required-to-rules [req-rules partitioned-validation-set]
  (into [] (map (fn [rule-set]
                  (let [rule-keyword (first rule-set)
                        rule (second rule-set)]
                    (if (contains? req-rules rule-keyword)
                      (make-rule-required rule)
                      rule)
                    ))
                partitioned-validation-set)))

(defn extract-keywords-from-validation-set [validation-set]
  (keep-indexed #(if (even? %1) %2) validation-set))

(defn build-required-validation-set [required-fields validation-set]
  "Take a set of required fields and the validation set of all rules and
   add the v/required rule to rules sets where the field keyword is included in the required-fields set.
   Useful because when creating a new record, some fields are required.
   When updating an existing one, they are often optional."
  (let [val-keywords (extract-keywords-from-validation-set validation-set)
        partitioned-validation-set (partition 2 validation-set)]
    (interleave val-keywords
                (append-required-to-rules required-fields partitioned-validation-set))))


(defn validate-employee [validation-rule-set required-rules emp]
  "Return a list of validation errors"
  (if (is-new-employee? emp)
    (let [validation-set (build-required-validation-set required-rules validation-rule-set)
          result [(first (apply b/validate emp validation-set))
                  (first (b/validate emp :confirmation [[v/required] [password-confirmation "password" emp]]))
                  (first (b/validate emp :email [[v/required] [unique-email "id" emp]]))]
          errors (doall (remove nil? result))]
      errors)
    (let [validation-set validation-rule-set
          result [(first (apply b/validate emp validation-set))]
          errors (doall (remove nil? result))]
      errors)))

(def new-employee-required-fields #{:firstname :lastname :password :confirmation :is-approver})
(def employee-validator (partial validate-employee employee-validation-set new-employee-required-fields))

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

(defn format-dates [employee]
  (let [emp (assoc employee :dob (fmt/format-date-yyyy-mm-dd (:dob employee))
                            :startdate (fmt/format-date-yyyy-mm-dd (:startdate employee))
                            :enddate (fmt/format-date-yyyy-mm-dd (:enddate employee)))]
    emp))

(defn prepare-for-insert [data]
  "Prepare the incoming data ready for inserting into the DB."
  "Generate a salted password and remove extraneous keys from the map"
  (let [hashed-password (hashers/encrypt (:password data) {:alg :pbkdf2+sha256})]
    (-> data
        (format-dates)
        (dissoc :password-confirm)
        (assoc :password hashed-password))))

(defn delete-department! [id]
  "Delete the department with the given id - providing it doesn't contain any employees"
  (let [department-with-employees (deps/get-department-by-id-with-employees db/db-spec {:id id})]
    (if (empty? department-with-employees)
      true
      (if (every? #(nil? (:lastname %)) department-with-employees)
        (= 1 (deps/delete-department db/db-spec {:id id}))
        false))))

(defn fetch-department-employees [department-id]
  (emps/get-department-employees db/db-spec {:id department-id}))

(defn fetch-departments []
  (deps/get-all-departments db/db-spec))

(defn fetch-leave-types []
  (ltypes/get-all-leave-types db/db-spec))

(defn delete-leave-type! [id]
  "Delete the leave-type with the given id - providing it isn't used on a booking"
  (let [absence-bookings {}] ; (ltypes/get-leave-type-by-id-with-employees db/db-spec {:id id})
    (if (empty? absence-bookings)
      (= 1 (deps/delete-leave-type db/db-spec {:id id}))
      false)))



;;TODO: Need a password reset resource.

(defn get-credentials [ctx]
  {:email (get-in ctx [:request :params :email]) :password (get-in ctx [:request :params :password])})

(defresource build-auth-token []
             :available-media-types ["application/edn" "application/json"]
             :allowed-methods [:get]
             :known-content-type? #(check-content-type % ["application/x-www-form-urlencoded" "application/json"])
             :malformed? (fn [ctx]
                           (let [credentials (get-credentials ctx)
                                 validation-result (validate-user-format credentials)]
                             (when (seq validation-result)
                               {::invalid-user-format "Invalid email/password"})))

             :handle-malformed ::invalid-user-format

             :handle-ok (fn [ctx]
                          {:token (sec/create-auth-token (get-credentials ctx))}))



(defresource department-employees [department-id]
             (secured-resource)
             :available-media-types ["application/edn" "application/json"]
             :allowed-methods [:get :post]
             :known-content-type? #(check-content-type % ["application/x-www-form-urlencoded" "application/json"])
             :exists? (fn [ctx]
                        (if (requested-method ctx :get)
                          [true {::department-employees
                                 {:department-employees
                                  (fetch-department-employees department-id)}}]))


             :handle-created (fn [ctx]
                               (if (::failure-message ctx)
                                 {:status 403 :body (::failure-message ctx)}
                                 {:location (::location ctx)}))

             :handle-ok ::department-employees)

(defresource departments []
             (secured-resource)
             :available-media-types ["application/edn" "application/json"]
             :allowed-methods [:get :post]
             :exists? (fn [ctx]
                        (if (requested-method ctx :get)
                          [true {::departments {:departments (fetch-departments)}}]))


             :malformed? (fn [ctx]
                           (if (requested-method ctx :post)
                             (let [form-data (make-keyword-map (get-posted-data ctx))
                                   validation-result (validate-department form-data)]
                               (if (seq validation-result)
                                 [true {::failure-message validation-result}]
                                 false))
                             false))

             :handle-malformed ::failure-message

             :post! (fn [ctx]
                      "We allow the DB to enforce its constraints here, rather than relying on the processable? descision point.
                      That way we can be sure the DB has not been changed by another thread or user between calls to processable? and post!"
                      (if (requested-method ctx :post)
                        (try
                          (when-let [new-id (deps/insert-department db/db-spec (make-keyword-map (get-posted-data ctx)))]
                            {::location (routes/api-endpoint-for :department-by-id :id new-id)})
                          (catch Exception e {::failure-message "Department already exists"}))))

             :post-redirect? false

             :handle-ok ::departments)

(defresource department [id]
             (secured-resource)
             :available-media-types ["application/edn" "application/json"]
             :allowed-methods [:get :delete :put]
             :known-content-type? #(check-content-type % ["application/x-www-form-urlencoded" "application/json"])
             :can-put-to-missing? false
             :exists? (fn [ctx]
                        (if (or (requested-method ctx :get) (requested-method ctx :put))
                          (let [department (deps/get-department-by-id db/db-spec {:id id})]
                            (if (empty? department)
                              false
                              [true {::department department}]))
                          true))

             :processable? (fn [ctx]
                             (if (requested-method ctx :delete)
                               (let [department (deps/get-department-by-id db/db-spec {:id id})]
                                 (if (empty? (:employees department))
                                   true
                                   [false {::failure-message "Unable to delete, the department contains employees"}]))
                               true))

             :handle-unprocessable-entity ::failure-message

             :malformed? (fn [ctx]
                           (if (requested-method ctx :put)
                             (let [form-data (make-keyword-map (get-posted-data ctx))
                                   validation-result (validate-department form-data)]
                               (if (seq validation-result)
                                 [true {::failure-message validation-result}]
                                 false))
                             false))

             :handle-malformed ::failure-message

             :conflict? (fn [ctx]
                          (let [new-department-name (-> ctx (get-posted-data) (make-keyword-map) (:department))
                                existing-department (->> {:department new-department-name} (deps/get-department-by-name db/db-spec))]
                            (info (str "existing department-name: " (:department existing-department)))
                            (and (seq existing-department) (not= (str id) (str (:id existing-department))))))

             :handle-conflict {:department ["Department already exists"]}

             :delete! (fn [ctx]
                        (delete-department! id))

             :put! (fn [ctx]
                     (let [new-data (make-keyword-map (get-posted-data ctx))
                           existing-data (::department ctx)
                           updated-data (merge existing-data new-data)]
                       (deps/update-department db/db-spec updated-data)
                       {::department updated-data}))

             :new? (fn [ctx] (nil? ::department))
             :respond-with-entity? (fn [ctx] (not (empty? (::department ctx))))
             :multiple-representations? false
             :handle-ok ::department
             :handle-not-found "Department not found")


(def truthy? (complement #{"false"}))

(defn parse-employee [emp]
  (assoc emp :is-approver (truthy? (:is-approver emp))))

(defresource employees []
             (secured-resource)
             :available-media-types ["application/edn" "application/json"]
             :allowed-methods [:get :post]
             :known-content-type? #(check-content-type % ["application/x-www-form-urlencoded" "application/json"])
             :exists? (fn [ctx]
                        (if (requested-method ctx :get)
                          [true {::employees (emps/get-all-departments-with-employees db/db-spec)}]))

             :malformed? (fn [ctx]
                           (if (requested-method ctx :post)
                             (let [form-data (parse-employee (make-keyword-map (get-posted-data ctx)))
                                   validation-result (employee-validator form-data)]
                               (if (seq validation-result)
                                 [true {::failure-message validation-result}]
                                 false))
                             false))

             :handle-malformed ::failure-message

             :post! (fn [ctx]
                      (if (requested-method ctx :post)
                        (try
                          (let [emp (prepare-for-insert (make-keyword-map (get-posted-data ctx)))]
                            (when-let [new-id (emps/insert-employee db/db-spec emp)]
                              {::location (routes/api-endpoint-for :employee-by-id :id new-id)}))
                          (catch Exception e {::failure-message (.getMessage e)}))))

             :post-redirect? false
             :handle-created (fn [ctx]
                               (if (::failure-message ctx)
                                 {:status 403 :body (::failure-message ctx)}
                                 {:location (::location ctx)}))

             :handle-ok ::employees)


(defn fix-up-lt [data]
  (let [fixed-data (assoc data :reduce-leave (if (= (:reduce-leave data) "true") 1 0))]
    (transform-keys csk/->snake_case_keyword fixed-data)))

(defresource leave-types []
             (secured-resource)
             :available-media-types ["application/edn" "application/json"]
             :allowed-methods [:get :post]
             :exists? (fn [ctx]
                        (if (requested-method ctx :get)
                          [true {::leave-types {:leave-types (fetch-leave-types)}}]))


             :malformed? (fn [ctx]
                           (if (requested-method ctx :post)
                             (let [form-data (make-keyword-map (get-posted-data ctx))
                                   validation-result (validate-leave-type form-data)]
                               (if (seq validation-result)
                                 [true {::failure-message validation-result}]
                                 false))
                             false))

             :handle-malformed ::failure-message

             :post! (fn [ctx]
                      "We allow the DB to enforce its constraints here, rather than relying on the processable? descision point.
                      That way we can be sure the DB has not been changed by another thread or user between calls to processable? and post!"
                      (if (requested-method ctx :post)
                        (try
                          (let [leave-type-data (make-keyword-map (get-posted-data ctx))
                                data (fix-up-lt leave-type-data)]
                            (when-let [new-id (ltypes/insert-leave-type db/db-spec data)]
                              {::location (routes/api-endpoint-for :leave-type-by-id :id new-id)}))
                          (catch Exception e {::failure-message "leave-type already exists"}))))

             :post-redirect? false

             :handle-ok ::leave-types)

(defresource leave-type [id]
             (secured-resource)
             :available-media-types ["application/edn" "application/json"]
             :allowed-methods [:get :delete :put]
             :known-content-type? #(check-content-type % ["application/x-www-form-urlencoded" "application/json"])
             :can-put-to-missing? false
             :exists? (fn [ctx]
                        (if (or (requested-method ctx :get) (requested-method ctx :put))
                          (let [leave-type (ltypes/get-leave-type-by-id db/db-spec {:id id})]
                            (if (empty? leave-type)
                              false
                              [true {::leave-type leave-type}]))
                          true))

             :processable? (fn [ctx]
                             (if (requested-method ctx :delete)
                               (let [leave-type (ltypes/get-leave-type-by-id db/db-spec {:id id})]
                                 (if true                   ; Check not used on any absense bookings
                                   true
                                   [false {::failure-message "Unable to delete, the leave-type contains employees"}]))
                               true))

             :handle-unprocessable-entity ::failure-message

             :malformed? (fn [ctx]
                           (if (requested-method ctx :put)
                             (let [form-data (make-keyword-map (get-posted-data ctx))
                                   validation-result (validate-leave-type form-data)]
                               (if (seq validation-result)
                                 [true {::failure-message validation-result}]
                                 false))
                             false))

             :handle-malformed ::failure-message

             :conflict? (fn [ctx]
                          (let [new-leave-type-name (-> ctx (get-posted-data) (make-keyword-map) (:leave-type))
                                existing-leave-type (->> {(csk/->snake_case_keyword :leave-type) new-leave-type-name} (ltypes/get-leave-type-by-name db/db-spec))]
                            (info (str "existing leave-type-name: " (:leave-type existing-leave-type)))
                            (and (seq existing-leave-type) (not= (str id) (str (:id existing-leave-type))))))

             :handle-conflict {:leave-type ["leave-type already exists"]}

             :delete! (fn [ctx]
                        (delete-leave-type! id))

             :put! (fn [ctx]
                     (let [new-data (make-keyword-map (get-posted-data ctx))
                           updated-data (fix-up-lt new-data)
                           ]
                       (ltypes/update-leave-type db/db-spec updated-data)
                       {::leave-type updated-data}))

             :new? (fn [ctx] (nil? ::leave-type))
             :respond-with-entity? (fn [ctx] (not (empty? (::leave-type ctx))))
             :multiple-representations? false
             :handle-ok ::leave-type
             :handle-not-found "Leave type not found")

(defn find-employee-by [employee-finder-fn &criteria]
  (fn [ctx]
    (if (or (requested-method ctx :get) (requested-method ctx :put))
      (let [employee (apply employee-finder-fn &criteria)]
        (if (empty? employee)
          false
          [(not (empty? employee)) {::employee employee}]))
      true)))

(defn is-employee-processable? [employee-finder-fn &criteria]
  (fn [ctx]
    (if (or (requested-method ctx :delete) (requested-method ctx :put))
      (if-let [employee (apply employee-finder-fn &criteria)]
        [true {::employee employee}]
        false)
      true)))

(defn is-employee-malformed? []
  (fn [ctx]
    (if (requested-method ctx :put)
      (let [form-data (parse-employee (make-keyword-map (get-posted-data ctx)))
            validation-result (employee-validator form-data)]
        (if (seq validation-result)
          [true {::failure-message validation-result}]
          false))
      false)))

(defn does-employee-conflict? []
  (fn [ctx]
    (let [new-employee (make-keyword-map (get-posted-data ctx))
          existing-employee (emps/get-employee-by-email db/db-spec {:email (:email new-employee)})]
      (and (not (nil? existing-employee)) (not= (:id new-employee) (str (:id existing-employee)))))))

(defn update-existing-employee []
  (fn [ctx]
    (let [new-data (parse-employee (make-keyword-map (get-posted-data ctx)))
          existing-data (::employee ctx)
          updated-data (merge existing-data new-data)]
      (emps/update-employee db/db-spec (format-dates updated-data))
      {::employee updated-data})))

(defresource employee-by-id [id]
             (secured-resource)
             :available-media-types ["application/edn" "application/json"]
             :allowed-methods [:get :delete :put]
             :known-content-type? #(check-content-type % ["application/x-www-form-urlencoded" "application/json"])
             :can-put-to-missing? false
             :exists? (find-employee-by emps/get-employee-by-id [db/db-spec {:id id}])

             :processable? (is-employee-processable? emps/get-employee-by-id [db/db-spec {:id id}])

             :malformed? (is-employee-malformed?)

             :handle-malformed ::failure-message

             :conflict? (does-employee-conflict?)

             :handle-conflict {:employee ["Employee already registered with this email address"]}

             :delete! (fn [_]
                        (emps/delete-employee db/db-spec {:id id}))

             :put! (update-existing-employee)

             :new? (fn [_] (nil? ::employee))
             :respond-with-entity? (fn [ctx] (not (empty? (::employee ctx))))
             :multiple-representations? false
             :handle-ok ::employee
             :handle-not-found "Employee not found")

(defresource employee-by-email [email]
             (secured-resource)
             :available-media-types ["application/edn" "application/json"]
             :allowed-methods [:get :put]
             :known-content-type? #(check-content-type % ["application/x-www-form-urlencoded" "application/json"])
             :can-put-to-missing? false
             :exists? (find-employee-by emps/get-employee-by-email [db/db-spec {:email email}])

             :processable? (is-employee-processable? emps/get-employee-by-email [db/db-spec {:email email}])

             :malformed? (is-employee-malformed?)

             :handle-malformed ::failure-message

             :conflict? (does-employee-conflict?)

             :handle-conflict {:employee ["Employee already registered with this email address"]}

             :put! (update-existing-employee)

             :new? (fn [_] (nil? ::employee))
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
             (secured-resource)
             :available-media-types ["application/edn" "application/json"]
             :allowed-methods [:get :post]
             :known-content-type? #(check-content-type % ["application/x-www-form-urlencoded" "application/json"])
             :exists? (fn [ctx]
                        (if (requested-method ctx :get)
                          [true {::holidays (hols/get-holidays db/db-spec)}]))


             :malformed? (fn [ctx]
                           (if (requested-method ctx :post)
                             (let [form-data (-> ctx (get-posted-data) (make-keyword-map) (format-holiday-request))
                                   validation-result (validate-holiday-request form-data)]
                               (if (seq validation-result)
                                 [true {::failure-message (str validation-result)}]
                                 false))
                             false))

             :handle-malformed ::failure-message

             :post! (fn [ctx]
                      "We allow the DB to enforce its constraints here, rather than relying on the processable? descision point.
                      That way we can be sure the DB has not been changed by another thread or user between calls to processable? and post!"
                      (if (requested-method ctx :post)
                        (try
                          (when-let [new-id (hols/insert-holiday-request db/db-spec (make-keyword-map (get-posted-data ctx)))]
                            {::location (routes/api-endpoint-for :holiday-by-id :id new-id)})
                          (catch Exception e {::failure-message "Invaliday holiday request"}))))

             :post-redirect? false
             :handle-created (fn [ctx]
                               (if (::failure-message ctx)
                                 {:status 403 :body (::failure-message ctx)}
                                 {:location (::location ctx)}))

             :handle-ok ::holidays)


