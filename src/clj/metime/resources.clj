  (ns metime.resources
  (:require [liberator.core :refer [resource defresource]]
            [liberator.representation :refer [as-response]]
            [metime.data.departments :as deps]
            [metime.data.employees :as emps]
            [clojure.java.io :as io]
            [clojure.walk :as walk]
            [clojure.data.json :as json]
            [metis.core :refer [defvalidator] :as v]))

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

;; (defn select-values
;;   "Expects a map and a vector of keys. Returns a list of the values of those keys"
;;   (comp vals select-keys))


(defn make-keyword-map [string-map]
  (walk/keywordize-keys string-map))

(defn get-form-params [ctx]
  (get-in ctx [:request :form-params]))


;;---------------------
;; Validators

(defvalidator department-validator
  [:id :numericality {:only-integer true :greater-than 0 :only :updating :except :adding}]
  [:department :length {:greater-than 0 :less-than 31}]
  [:managerid :numericality {:only-integer true :greater-than 0}])

;;---------------------


(defresource departments []
  :available-media-types ["application/edn" "application/json"]
  :allowed-methods [:get :post]
  :known-content-type? #(check-content-type % ["application/x-www-form-urlencoded" "application/json"])
  :exists? (fn [ctx]
             (if (requested-method ctx :get)
              [true {::departments {:departments (deps/get-all-with-employees)}}]))

  :malformed? (fn [ctx]
                (if (requested-method ctx :post)
                  (let [post-data (make-keyword-map (get-form-params ctx))
                        validation-result (department-validator post-data :adding)]
                    (if (not (empty? validation-result))
                      true
                      false))
                  true))

  :processable? (fn [ctx]
                  "Check here if duplication or allow DB uniqueness to enforce ??"
                  )

  :post! (fn [ctx]
           (if (requested-method ctx :post)
             (try
               (when-let [new-id (deps/insert-department (get-in ctx [:request :form-params]))]
                   {::location (str "http://localhost:3030/api/departments/" new-id)})
               (catch Exception e {::failure true}))))

  :post-redirect? false
  :handle-created (fn [ctx]
                    (if (::failure ctx)
                      {:status 403 :body "Department already exists"}
                      {:location (::location ctx)}))

  :handle-ok ::departments)

(defresource department [id]
  :available-media-types ["application/edn" "application/json"]
  :allowed-methods [:get :delete :put]
  :can-put-to-missing? false
  :known-content-type? #(check-content-type % ["text/html" "application/x-www-form-urlencoded" "application/json"])
;;   :malformed? (fn [ctx]
;;                 (if (requested-method ctx :put)
;;                   (let [ks          ["department" "managerid" "id"]
;;                         form-params (get-in ctx [:request :form-params])]
;;                     (or (not-every? form-params ks)
;;                         (some empty? (select-values form-params ks))))))

  :exists? (fn [ctx]
             (if (or (requested-method ctx :get) (requested-method ctx :put))
              (when-let [department (deps/get-department-by-id id)]
                  [true {::department department}])
             true))

  :conflict? (fn [ctx]
               (let [new-department-name (get (get-in ctx [:request :form-params]) "department")
                     existing-department (deps/get-department-by-name new-department-name)]
                 (spit "log.txt" (str "id: " id " (:id existing-department): " (:id existing-department)))
                 (and (not (nil? existing-department)) (not= (str id) (str (:id existing-department))))))


;;   :processable? (fn [ctx]
;;                   (let [method (get (get-in ctx [:request :form-params]))]
;;                     (case method
;;                       :delete (if-let [department (deps/get-department-by-id id)]
;;                                 [(empty? (:employees department)) {::department department}]
;;                                 false)
;;                       :put (let values))))


  :delete! (fn [ctx]
             (deps/delete-department id))

  :put! (fn [ctx]
          (deps/update-department (walk/keywordize-keys (get-in ctx [:request :form-params]))))

  :new? (fn [ctx]
          (requested-method ctx :post))

  :respond-with-entity? (fn [ctx] (empty? (:employees (::department ctx))))
  :handle-ok ::department
  :handle-not-found "Department not found")


(defresource employees []
  :available-media-types ["application/edn" "application/json"]
  :allowed-methods [:get]
  :exists? (fn [ctx]
             (if (requested-method ctx :get)
               {::employees (emps/get-all)}
               true))

  :handle-ok ::employees)


(defresource employee [id]
  :available-media-types ["application/edn" "application/json"]
  :allowed-methods [:get :delete :put]
  :exists? (fn [ctx]
             (if (requested-method ctx :get)
               (let [employee (emps/get-employee-by-id id)]
                 [(not (empty? employee)) {::employee employee}])
               true))

  :processable? (fn [ctx]
                 (if (requested-method ctx :delete)
                   (if-let [employee (emps/get-employee-by-id id)]
                     [true {::employee employee}]
                     false)
                   true))

  :delete! (fn [ctx]
             (emps/delete-employee id))

  :respond-with-entity? (fn [ctx] (not (empty? (::employee ctx))))

  :handle-ok ::employee
  :handle-not-found "Employee not found")
