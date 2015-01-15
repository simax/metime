(ns metime.resources
  (:require [liberator.core :refer [resource defresource]]
            [liberator.representation :refer [as-response]]
            [metime.data.departments :as deps]
            [metime.data.employees :as emps]
            [clojure.java.io :as io]
            ;; [cheshire.core :as json]
            [clojure.data.json :as json]
            ))

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


(defresource get-departments []
  :available-media-types ["application/json"]
  :allowed-methods [:get]
  :known-content-type? #(check-content-type % ["text/html" "application/x-www-form-urlencoded" "application/json"])

  :as-response (fn [d ctx]
                 (as-response d ctx))

  :exists? (fn [ctx]
              [true {::departments {:departments (deps/get-all-with-employees)}}])

  :handle-ok ::departments)

(defresource create-departments []
  :available-media-types ["application/json"]
  :allowed-methods [:post]
  :known-content-type? #(check-content-type % ["text/html" "application/x-www-form-urlencoded" "application/json"])

  :as-response (fn [d ctx]
                 (as-response d ctx))

  :post! (fn [ctx]
           (deps/insert-department (get-in ctx [:request :form-params])))

  :handle-ok ::departments)


(defresource get-employees []
  :available-media-types ["application/json"]
  :allowed-methods [:get]

  :as-response (fn [d ctx]
                 (as-response d ctx))

  :exists? (fn [ctx]
              {::employees (emps/get-all)})
  :handle-ok ::employees)


(defresource get-employee [id]
  :available-media-types ["application/json"]
  :allowed-methods [:get]

  :as-response (fn [d ctx]
                 (as-response d ctx))

  :exists? (fn [ctx]
              {::employee (emps/get-employee-by-id id)})

  :handle-ok ::employee)
