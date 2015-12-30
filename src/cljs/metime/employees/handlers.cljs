(ns metime.employees.handlers
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [put! take! <! >! chan timeout]]
            [metime.utils :as utils]
            [metime.routes :as r]
            [cljs-http.client :as http]
            [cljs-time.core :refer [date-time now days minus day-of-week]]
            [cljs-time.format :as f :refer [formatter parse unparse]]
            [re-frame.core :refer [register-handler
                                   path
                                   debug
                                   after
                                   enrich
                                   register-sub
                                   dispatch
                                   subscribe]]
            [bouncer.core :as b]
            [bouncer.validators :as v]))

(defn is-new-employee? [id]
  (zero? id))

(def british-date-format (f/formatter "dd-MM-yyyy"))

(def employee-validation-rules
  [:firstname [[v/required :message "First name is required"]]
   :lastname [[v/required :message "Last name is required"]]
   :email [[v/required :message "An email address is required"] [v/email :message "Please supply a valid email address"]]
   :dob [[v/datetime british-date-format :pre (comp seq :dob) :message "Must be a valid date"]]
   :startdate [[v/datetime british-date-format :pre (comp seq :startdate) :message "Must be a valid date"]]
   :enddate [[v/datetime british-date-format :pre (comp seq :enddate) :message "Must be a valid date"]]
   :prev_year_allowance [[v/integer :message "Must be an integer"]]
   :current_year_allowance [[v/integer :message "Must be an integer"]]
   :next_year_allowance [[v/integer :message "Must be an integer"]]])

(defn validate-employee [db _]
  (let [employee (:employee db)
        result (apply b/validate employee employee-validation-rules)
        errors (first result)]
    ;(when (seq result) (.log js/console db))
    (assoc-in db [:employee :validation-errors] errors)))

(defn handle-input-change [db [_ property-name new-value]]
  (assoc-in db [:employee property-name] new-value))

(defn handle-input-change-balances [db [_ property-name new-value]]
  (assoc-in db [:employee property-name] (utils/parse-int new-value)))

(defn handle-input-change-dates [db [_ property-name new-value]]
  (let [date-value (try (unparse british-date-format new-value)
                        (catch :default e new-value))]
    (assoc-in db [:employee property-name] date-value)))

(defn handle-employee-add [db [_ departmentid managerid]]
  (let [dep (first (filter #(= (:departmentid %) departmentid) (:deps db)))]
    ;(utils/set-hash! (r/url-for :employee-add))
    (merge db
           (assoc db :nav-bar :employees :view :employee)
           {:employee
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
             :department_id       departmentid
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
  (if (apply b/valid? (:employee db) employee-validation-rules)
    (let [employee-id (get-in db [:employee :id])
          endpoint (get-employee-save-endpoint db employee-id)
          data (assoc (:employee db) :password "password1" :password-confirm "password1")
          response (go (<! (apply (:verb endpoint) [(:url endpoint) {:form-params data}])))]

      (if (= (:status response) 409)                        ;; Conflict
        (dispatch [:show-failed-save-attempt {:email (get-in response [:body :employee])}])
        (do
          ;(utils/set-hash! (r/url-for :employees))
          (dispatch [:set-active-view :employees :employees])
          db)))
    (validate-employee db _)))


(defn handle-department-change [db [_ id]]
  (let [deps (:deps db)
        dep (first (filter #(= (:departmentid %) (js/parseInt id)) deps))
        updated-emp (assoc (:employee db)
                      :department_id id
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
  (enrich validate-employee)
  handle-input-change-dates)

(register-handler
  :input-change-balances
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

(defn set-auth-cookie! [token]
  (let [expiry (* 60 60 24 30)]                             ; 30 days (secs mins hours days)
    (utils/set-cookie! "auth" token {:max-age expiry})))

(register-handler
  :authenticated
  (fn [db [_ token]]
    (assoc db :authentication-token token :authentication-failed-msg "" :nav-bar :employees :view :employees)))

(register-handler
  :authentication-failed
  (fn [db [_]]
    (assoc db :authentication-token ""
              :authentication-failed-msg "Invalid email/password"
              :nav-bar nil
              :view :login)))

(defn response-ok? [response]
  (= 200 (:status response)))

(defn authenticate-user [db [_]]
  (go
    (let [url (str (:api-root-url db) "/auth-token?" "email=" (get-in db [:employee :email]) "&" "password=" (get-in db [:employee :password]))
          response (<! (http/get url))
          token (if (response-ok? response)
                  ((js->clj (response :body)) :token)
                  "")]
      (set-auth-cookie! token)
      (if (empty? token)
        (dispatch [:authentication-failed])
        (dispatch [:authenticated token]))))
  db)

(register-handler
  :log-in
  authenticate-user)

(register-handler
  :log-out
  (fn [db [_]]
    (assoc db
      :view                       :login
      :authentication-failed-msg  ""
      :nav-bar                    nil
      )))