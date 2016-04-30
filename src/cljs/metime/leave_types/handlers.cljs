(ns metime.leave-types.handlers
  (:require-macros
    [cljs.core.async.macros :refer [go]]
    [bouncer.validators :refer [defvalidator]])
  (:require [metime.formatting :as fmt]
            [cljs.core.async :refer [<! >! chan]]
            [metime.utils :as utils]
            [metime.routes :as routes]
            [cljs-http.client :as http]
            [cljs-time.core :refer [date-time local-date now days minus day-of-week]]
            [cljs-time.format :as f :refer [formatter parse unparse]]
            [re-frame.core :refer [register-handler
                                   path
                                   debug
                                   after
                                   enrich
                                   register-sub
                                   dispatch
                                   subscribe]]
            [metime.utils :as utils]
            [bouncer.core :as b]
            [bouncer.validators :as v]
            [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame-tracer.core :refer [tracer]]
            [metime.common.handlers]))

(def leave-type-validation-rules
  [:leave-type [[v/required :message "leave type name is required"]]])

(defn validate-leave-type [db]
  (let [leave-type (:leave-type db)
        result [(first (apply b/validate leave-type leave-type-validation-rules))]
        errors (first result)]
    (assoc-in db [:leave-type :validation-errors] errors)))

(defn is-new-leave-type? [id]
  (zero? id))

(register-handler
  :leave-type-save-failure
  (fn hdlr-save-failure [db [_]]
    ; Potentially show some kind of boostrap alert?
    (println "Problem saving leave-type")
    db))

(register-handler
  :leave-type-save-success
  (fn hdlr-save-success [db [_]]
    (dispatch [:close-leave-type-drawer])
    (dispatch [:fetch-leave-types])
    (assoc db :leave-type nil)))

(defn build-leave-type-by-id-endpoint [leave-type]
  (routes/api-endpoint-for :leave-type-by-id :id (:leave-type-id leave-type)))

(defn add-new-leave-type [db leave-type]
  "Add a new leave-type"
  (utils/send-data-to-api :POST
                          (routes/api-endpoint-for :leave-types) (:authentication-token db) leave-type
                          {:valid-fn      #(dispatch [:leave-type-save-success])
                           :invalid-fn    #(dispatch [:leave-type-save-failure])
                           :response-keys [:body :leave-types]}))

(defn update-leave-type [db leave-type]
  "Update an existing leave-type"
  (utils/send-data-to-api :PUT
                          (build-leave-type-by-id-endpoint leave-type) (:authentication-token db) leave-type
                          {:valid-fn      #(dispatch [:leave-type-save-success])
                           :invalid-fn    #(dispatch [:leave-type-save-failure])
                           :response-keys [:body :leave-types]}))

(register-handler
  :leave-type-save
  (enrich validate-leave-type)
  (fn hdlr-leave-type-save [db [_]]
    (let [leave-type (:leave-type db)]
      (when (and
              (apply b/valid? leave-type leave-type-validation-rules)
              (not (some? (get-in leave-type [:validation-errors]))))
        (if (is-new-leave-type? (:leave-type-id leave-type))
          (add-new-leave-type db leave-type)
          (update-leave-type db leave-type)))
      db)))

(register-handler
  :leave-type-delete
  (fn hdlr-leave-type-delete [db [_ id]]
    (utils/call-api :DELETE (routes/api-endpoint-for :leave-type-by-id :id id) db
                    {:success-handler-key :fetch-leave-types
                     :response-keys       [:body]})
    db))

(register-handler
  :input-change-leave-type-name
  (enrich validate-leave-type)
  (fn hdlr-input-change [db [_ leave-type-name]]
    (assoc-in db [:leave-type :leave-type] leave-type-name)))

(register-handler
  :checkbox-change-leave-type-reduce-leave
  (enrich validate-leave-type)
  (fn hdlr-checkbox-change-leave-type-reduce-leave [db [_ leave-type-reduce-leave]]
    ;(println (str "leave-type-reduce-leave: " leave-type-reduce-leave))
    (assoc-in db [:leave-type :reduce-leave] leave-type-reduce-leave)))

(register-handler
  :new-leave-type
  (fn hdlr-new-leave-type [db [_]]
    (assoc db :leave-type {:leave-type-id 0 :leave-type "" })))

(register-handler
  :edit-leave-type
  (fn hdlr-edit-leave-type [db [_ leave-type-id]]
    (dispatch [:fetch-leave-type leave-type-id])
    db))

(register-handler
  :close-leave-type-drawer
  (fn hdlr-close-leave-type-drawer [db [_]]
    (assoc db
      :leave-type nil
      :leave-type-drawer-open-id nil)))

(register-handler
  :close-all-leave-type-drawers
  (fn hdlr-close-new-leave-type-drawer [db [_]]
    (dispatch [:close-leave-type-drawer])
    (assoc db :new-leave-type-drawer-open? false)))


(register-handler
  :ui-new-leave-type-drawer-status-toggle
  (fn hdlr-ui-new-leave-type-drawer-status-toggle [db [_]]
    (dispatch [:close-leave-type-drawer])
    (if (:new-leave-type-drawer-open? db)
      (assoc db :new-leave-type-drawer-open? false)
      (do
        (dispatch [:new-leave-type])
        (assoc db :new-leave-type-drawer-open? true)))))

(register-handler
  :api-response->leave-type
  (fn hdlr-api-reponse->leave-type [db [_ leave-type]]
    (let [value (js->clj leave-type)]
      (assoc db :leave-type value))))

(register-handler
  :fetch-leave-type
  (fn hdlr-fetch-leave-type [db [_ id]]
    (utils/call-api :GET (routes/api-endpoint-for :leave-type-by-id :id id) db
                    {:success-handler-key :api-response->leave-type
                     :response-keys       [:body]})
    db))




(register-handler
  :fetch-leave-types
  (fn hdlr-fetch-leave-types [db [_]]
    (utils/call-api :GET (routes/api-endpoint-for :leave-types) db
                    {:success-handler-key :api-response->leave-types
                     :response-keys       [:body :leave-types]})
    db))

(register-handler
  :api-response->leave-types
  (fn hdlr-api-reposne->leave-types [db [_ leave-types]]
    (let [value (js->clj leave-types)]
      (assoc db :leave-types value))))
