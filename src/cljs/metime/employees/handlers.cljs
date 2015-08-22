(ns metime.employees.handlers
  (:require [metime.utils :as utils]
            [metime.routes :as r]
            [cljs-http.client :as http]
            [secretary.core :as secretary]
            [re-frame.core :refer [register-handler
                                   path
                                   debug
                                   after
                                   enrich
                                   register-sub
                                   dispatch
                                   subscribe]]
            [validateur.validation :refer [numericality-of
                                           errors
                                           validation-set
                                           valid?
                                           invalid?
                                           presence-of
                                           length-of
                                           validate-when] :as v]))

;;TODO: Need to work out how to make sure confirmation is same as password
(def employee-validator
  (validation-set
    (length-of :firstname :within (range 1 31))
    (length-of :lastname :within (range 1 31))
    (presence-of :email)
    (numericality-of :departments_id :only-integer true :gt 0 :messages {:blank "Must be greater then 0"})
    (numericality-of :managerid :only-integer true :gt 0 :messages {:blank "Must be greater then 0"})
    (length-of :password :within (range 8 100))
    (validate-when #(contains? % :password) (presence-of :confirmation))))

;(defvalidator employee-validator
;  [:firstname :length {:greater-than 0 :less-than 31}]
;  [:lastname :length {:greater-than 0 :less-than 31}]
;  [:email :email {:greater-than 0 :less-than 31}]
;  [:departments_id :numericality {:only-integer true :greater-than 0}]
;  [:managerid :numericality {:only-integer true :greater-than 0}]
;  [:password [:length {:greater-than-or-equal-to 8}
;              :formatted {:pattern #"(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}"
;                          :message "alpha numeric, at least one number"}
;              :confirmation {:confirm :password-confirm}]]
;  [:dob :date])

(defn is-new-employee? [id]
  (zero? id))

(defn validate-employee [db _]
  ;(let [employee (:employee db)
  ;      result (v/valid? employee-validator employee)]
  ;  (when result (println (str "Errors in employee's firstname: " (errors :firstname (employee-validator employee)))))
  ;  db)
  (let [errors {:firstname           "Firstname required"
                :lastname            nil
                :email               "Must be unique"
                :dob                 nil
                :startdate           nil
                :enddate             nil
                :this_year_opening   "This year remaining should be set to something to prevent THIS error"
                :this_year_remaining nil
                :next_year_opening   nil
                :next_year_remaining nil
                }]
    (assoc-in db [:employee :validation-errors] errors)))


(defn handle-input-change [db [_ property-name new-value]]
  (assoc-in db [:employee property-name] new-value))

(defn handle-employee-add [db [_ departmentid managerid]]
  (let [url (r/employee-add-route)
        dep (first (filter #(= (:departmentid %) departmentid) (:deps db)))]
    (println (str "departmentid : " departmentid))
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
  (let [employee-id (get-in db [:employee :id])
        endpoint (get-employee-save-endpoint db employee-id)
        data (assoc (:employee db) :password "password1" :password-confirm "password1")]
    (apply (:verb endpoint) [(:url endpoint) {:form-params data}])
    (utils/set-hash! (r/employees-route))
    (dispatch [:switch-route :employees :employees]))
  db)

(defn handle-department-change [db [_ id]]
  (let [deps (:deps db)
        dep (first (filter #(= (:departmentid %) (js/parseInt id)) deps))
        updated-emp (assoc (:employee db)
                      :departments_id id
                      :managerid (:managerid dep)
                      :manager-email (:manager-email dep))]
    (assoc db :employee updated-emp)))

(register-handler
  :input-change
  (enrich validate-employee)
  handle-input-change)

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
