(ns metime.employees.handlers
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [put! take! <! >! chan timeout]]
            [metime.utils :as utils]
            [metime.routes :as r]
            [cljs-http.client :as http]
            [secretary.core :as secretary]
            [cljs-time.core :refer [date-time now days minus day-of-week]]
            [cljs-time.format :refer [formatter parse unparse]]
            [re-frame.core :refer [register-handler
                                   path
                                   debug
                                   after
                                   enrich
                                   register-sub
                                   dispatch
                                   subscribe]]
            [bouncer.core :as b]
            [bouncer.validators :as v]
            ))

(defn is-new-employee? [id]
  (zero? id))


(def employee-validation-rules
  [:firstname [[v/required :message "First name is required"]]
   :lastname [[v/required :message "Last name is required"]]
   :email [[v/required :message "An email address is required"] [v/email :message "Please supply a valid email address"]]
   :this_year_opening [[v/integer :message "Must be an integer"]]
   :this_year_remaining [[v/integer :message "Must be an integer"]]
   :next_year_opening [[v/integer :message "Must be an integer"]]
   :next_year_remaining [[v/integer :message "Must be an integer"]]])

(defn validate-employee [db _]
  (let [employee (:employee db)
        result (apply b/validate employee employee-validation-rules)
        errors (first result)]
    (when result (println (str "Validation: " errors)))
    (assoc-in db [:employee :validation-errors] errors)))


(defn handle-input-change [db [_ property-name new-value]]
  (assoc-in db [:employee property-name] new-value))

(defn handle-input-change-balances [db [_ property-name new-value]]
  (assoc-in db [:employee property-name] (utils/parse-int new-value)))

(defn handle-input-change-dates [db [_ property-name new-value]]
  (let [date-value (unparse (formatter "yyyy-MM-dd") new-value)] ;"dd MMM, yyyy"
    (assoc-in db [:employee property-name] date-value)))

(defn handle-employee-add [db [_ departmentid managerid]]
  (let [url (r/employee-add-route)
        dep (first (filter #(= (:departmentid %) departmentid) (:deps db)))]
    (utils/set-hash! url)
    (secretary/dispatch! url)
    (merge db {:employee
               {:is-ready?           true
                :id                  0
                :firstname           ""
                :lastname            ""
                :email               ""
                :dob                 nil
                :startdate           nil
                :enddate             nil
                :this_year_opening   25
                :this_year_remaining 25
                :next_year_opening   25
                :next_year_remaining 25
                :departments_id      departmentid
                :managerid           managerid
                :manager-firstname   (:manager-firstname dep)
                :manager-lastname    (:manager-lastname dep)
                :manager-email       (:manager-email dep)
                }})))

(defn get-employee-save-endpoint [db employee-id]
  (if (not (is-new-employee? employee-id))
    {:url (utils/api db (str "/employee/" employee-id)) :verb http/put}
    {:url (utils/api db "/employees") :verb http/post}))


(defn handle-employee-save [db _]
  (go (let [employee-id (get-in db [:employee :id])
            endpoint (get-employee-save-endpoint db employee-id)
            data (assoc (:employee db) :password "password1" :password-confirm "password1")
            response (<! (apply (:verb endpoint) [(:url endpoint) {:form-params data}]))]

        (println response)

        (if (= (get-in response [:body :status]) 403)
          (dispatch [:show-failed-save-attempt {:email '("Another employee has already been registered with this email address")}])
          (do (utils/set-hash! (r/employees-route))
              (dispatch [:switch-route :employees :employees])))))
  db)

(defn handle-department-change [db [_ id]]
  (let [deps (:deps db)
        dep (first (filter #(= (:departmentid %) (js/parseInt id)) deps))
        updated-emp (assoc (:employee db)
                      :departments_id id
                      :department (:department dep)
                      :managerid (:managerid dep)
                      :manager-email (:manager-email dep)
                      :manager-firstname (:manager-firstname dep)
                      :manager-lastname (:manager-lastname dep))]
    (assoc db :employee updated-emp)))

(register-handler
  :input-change
  (enrich validate-employee)
  handle-input-change)

(register-handler
  :input-change-dates
  ;(enrich validate-employee)
  handle-input-change-dates)

(register-handler
  :input-change-balances
  ;(enrich validate-employee)
  handle-input-change-balances)

(register-handler
  :employee-add
  handle-employee-add)

(register-handler
  :employee-save
  handle-employee-save)

(register-handler
  :department-change
  handle-department-change)

(register-handler
  :ui-department-drawer-status-toggle
  (fn [db [_ department]]
    (if (= (:department-draw-open-id db) department)
      (assoc db :department-draw-open-id "")
      (assoc db :department-draw-open-id department))))

(register-handler
  :show-failed-save-attempt
  (fn [db [_ errors]]
    (assoc-in db [:employee :validation-errors] errors)))