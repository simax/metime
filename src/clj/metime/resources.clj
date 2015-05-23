(ns metime.resources
  (:require [liberator.core :refer [resource defresource]]
            [liberator.representation :refer [as-response]]
            [metime.data.departments :as deps]
            [metime.data.employees :as emps]
            [clojure.java.io :as io]
            [clojure.walk :as walk]
            [clojure.data.json :as json]
            [metis.core :refer [defvalidator] :as v]
            [clj-time.core :as t]
            [clj-time.format :as f]
            [clojure.set :refer :all]))

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
              [:departments_id :numericality {:only-integer true :greater-than 0}]
              [:managerid :numericality {:only-integer true :greater-than 0}]
              [:password [:length {:greater-than-or-equal-to 8}
                          :formatted {:pattern #"(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}"
                                      :message "alpha numeric, at least one number"}
                          :confirmation {:confirm :password-confirm}]]
              [:dob :date])

;;---------------------


(defn employees-by-department []
  (let [data-emps (emps/get-all-employees)
        grouped-emps (group-by :departments_id data-emps)

        data-deps (deps/get-all-departments)
        set-of-deps (rename (into #{} data-deps) {:id :departments_id})
        set-of-emps (into #{} (map #(hash-map :departments_id %1 :employees %2)
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
                          (catch Exception e {::failure-message "Employee already exists"}))))

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
                        (let [employee (emps/get-employee-by-id id)]
                          [(not (empty? employee)) {::employee employee}]))

             :processable? (fn [ctx]
                             (if (or (requested-method ctx :delete) (requested-method ctx :put))
                               (if-let [employee (emps/get-employee-by-id id)]
                                 [true {::employee employee}]
                                 false)
                               true))

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
