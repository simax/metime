(ns metime.calendar.handlers
  (:require-macros
    [cljs.core.async.macros :refer [go]]
    [bouncer.validators :refer [defvalidator]])
  (:require [metime.formatting :as fmt]
            [clojure.string :as s]
            [cljs.core.async :refer [<! >! chan]]
            [metime.utils :as utils]
            [metime.routes :as routes]
            [cljs-http.client :as http]
            [cljs-time.core :refer [date-time local-date now days minus today day-of-week]]
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

;(trace-forms
; {:tracer (tracer :color "green")}


;[start_date]      date null,
;[start_type]      varchar(30) null,
;[end_date]        date null,
;[employee_id]     integer,
;[employee_name]   varchar(255) null,
;[leave_type_id]   integer,
;[leave_type]      varchar(255) null,
;[duration]        real,
;[deduction]       real,
;[actioned_by_id]  integer,
;[reason]          varchar(255) null,
;[declined_reason] varchar(255) null,
;[status]          varchar(255) null,
;[unit]            varchar(255) null,
; end_type integer [start_type] varchar(30) null

;:startdate [[v/datetime british-date-format :message "Must be a valid date" :pre (comp seq :startdate)]]
;:enddate [[v/datetime british-date-format :pre (comp seq :enddate) :message "Must be a valid date"]]


(def booking-validation-rules
  [
   :start-date [[v/required :message "Start date is required"]]
   :end-date [[v/required :message "End date is required"]]
   :start-type [[v/required :message "Start time is required"]]
   :end-type [[v/required :message "End time is required"]]
   :leave-type-id [[v/required :message "Leave type is required"]]
   :employee-id [[v/required :message "Leave type is required"]]
   ])


(defn validate-booking [db]
  (let [booking (:booking db)
        result [(first (apply b/validate booking booking-validation-rules))]
        errors (first result)]
    (assoc-in db [:booking :validation-errors] errors)))

(defn is-new-booking? [id]
  (zero? id))

(register-handler
  :leave-type-change
  (fn hdlr-leave-type-change [db [_ leave-type-id]]
    (assoc-in db [:booking :leave-type-id] leave-type-id)))

(register-handler
  :booking-save-failure
  (fn hdlr-save-failure [db [_]]
    ; Potentially show some kind of boostrap alert?
    (println "Problem saving booking")
    db))

(register-handler
  :booking-save-success
  (fn hdlr-save-success [db [_]]
    (dispatch [:close-booking-drawer])
    (dispatch [:fetch-bookings])
    (assoc db :booking nil)))

(defn build-booking-by-id-endpoint [booking]
  (routes/api-endpoint-for :booking-by-id :id (:booking-id booking)))

(defn add-new-booking [db booking]
  "Add a new booking"
  (utils/send-data-to-api :POST
                          (routes/api-endpoint-for :bookings) (:authentication-token db) booking
                          {:valid-fn      #(dispatch [:booking-save-success])
                           :invalid-fn    #(dispatch [:booking-save-failure])
                           :response-keys [:body :bookings]}))

(defn update-booking [db booking]
  "Update an existing booking"
  (utils/send-data-to-api :PUT
                          (build-booking-by-id-endpoint booking) (:authentication-token db) booking
                          {:valid-fn      #(dispatch [:booking-save-success])
                           :invalid-fn    #(dispatch [:booking-save-failure])
                           :response-keys [:body :bookings]}))

(register-handler
  :booking-save
  (enrich validate-booking)
  (fn hdlr-booking-save [db [_]]
    (let [booking (:booking db)]
      (when (and
              (apply b/valid? booking booking-validation-rules)
              (not (some? (get-in booking [:validation-errors]))))
        (if (is-new-booking? (:booking-id booking))
          (add-new-booking db booking)
          (update-booking db booking)))
      db)))

(register-handler
  :booking-delete
  (fn hdlr-booking-delete [db [_ id]]
    (utils/call-api :DELETE (routes/api-endpoint-for :booking-by-id :id id) db
                    {:success-handler-key :fetch-bookings
                     :response-keys       [:body]})
    db))

(register-handler
  :input-change-booking-type
  (enrich validate-booking)
  (fn hdlr-input-change [db [_ booking-type]]
    (assoc-in db [:booking :leave-type] booking-type)))

(register-handler
  :new-booking
  ;TODO: Default to current employee
  (fn hdlr-new-booking [db [_]]
    (let [current-date (unparse (formatter "dd-MM-yyyy") (now))
          default-leave-type (:leave-type-id (first (filter #(= "Holiday" (:leave-type %)) (:leave-types @re-frame.db/app-db))))]
      (assoc db :booking {:booking-id    0
                          :employee-id   (:logged-in-user-id db)
                          :leave-type-id default-leave-type
                          :start-date    current-date
                          :start-type    "Morning"
                          :end-date      current-date
                          :end-type      "End of the day"
                          :reason        ""}))))

(register-handler
  :input-change-reason
  ;(enrich validate-employee)
  (fn hdlr-input-change [db [_ property-name new-value]]
    (assoc-in db [:booking property-name] new-value)))

(register-handler
  :edit-booking
  (fn hdlr-edit-booking [db [_ booking-id]]
    (dispatch [:fetch-booking booking-id])
    db))

(register-handler
  :fetch-start-types
  (fn hdlr-fetch-start-types [db [_]]
    (assoc db
      :start-types [{:id "Morning" :label "Morning"}
                    {:id "Afternoon" :label "Afternoon"}])))
(register-handler
  :fetch-end-types
  (fn hdlr-fetch-end-types [db [_]]
    (assoc db
      :end-types [{:id "Lunchtime" :label "Lunchtime"}
                  {:id "End of the day" :label "End of the day"}])))

(register-handler
  :close-booking-drawer
  (fn hdlr-close-booking-drawer [db [_]]
    ()
    (assoc db
      :booking nil
      :booking-drawer-open-id nil)))

(register-handler
  :close-all-booking-drawers
  (fn hdlr-close-all-booking-drawers [db [_]]
    (dispatch [:close-booking-drawer])
    (assoc db :new-booking-drawer-open? false)))

(register-handler
  :ui-new-booking-drawer-status-toggle
  (fn hdlr-ui-new-booking-drawer-status-toggle [db [_]]
    (dispatch [:close-booking-drawer])
    (if (:new-booking-drawer-open? db)
      (assoc db :new-booking-drawer-open? false)
      (do
        (dispatch [:new-booking])
        (assoc db :new-booking-drawer-open? true)))))

(register-handler
  :api-response->booking
  (fn hdlr-api-reponse->booking [db [_ booking]]
    (let [value (js->clj booking)]
      (assoc db :booking value))))

(register-handler
  :fetch-booking
  (fn hdlr-fetch-booking [db [_ id]]
    (utils/call-api :GET (routes/api-endpoint-for :booking-by-id :id id) db
                    {:success-handler-key :api-response->booking
                     :response-keys       [:body]})
    db))

(register-handler
  :fetch-my-bookings
  (fn hdlr-fetch-my-bookings [db [_]]
    (utils/call-api :GET (routes/api-endpoint-for :employee-bookings :id (:logged-in-user-id db)) db
                    {:success-handler-key :api-response->bookings-by-employee-id
                     :response-keys       [:body]})
    db))

(register-handler
  :api-response->bookings-by-employee-id
  (fn hdlr-api-response->bookings-by-employee-id [db [_ employee-bookings]]
    (let [value (js->clj employee-bookings)]
      (assoc db :employee-bookings value))))

(register-handler
  :set-booking-employee-name
  (fn hdlr-set-department-employee-name [db [_ employee-id]]
    (println (str "employee-id " employee-id))
    (let [emp (first (filter #(= employee-id (:id %)) (get-in db [:departments-with-employees])))
          emp-name (str (:firstname emp) " " (:lastname emp))]
      (-> db
          (assoc-in [:booking :employee-name] emp-name)))))

(register-handler
  :set-booking-employee-id
  ;(enrich validate-department)
  (fn hdlr-set-booking-employee-id [db [_ employee-id]]
    (dispatch [:set-booking-employee-name employee-id])
    (assoc-in db [:booking :employee-id] employee-id)))

;)


