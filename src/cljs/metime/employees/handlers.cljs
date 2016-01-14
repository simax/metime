(ns metime.employees.handlers
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<!]]
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
            [metime.utils :as utils]
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

(defn get-employee-save-endpoint [db employee-id]
  (if (not (is-new-employee? employee-id))
    {:url (utils/api db (str "/employee/" employee-id)) :verb http/put}
    {:url (utils/api db "/employees") :verb http/post}))

;
;(fn [db [_ id]]
;  (let [url (str (utils/api db "/employee/") id)
;        token (:authentication-token db)
;        valid-token-handler :process-employee-response
;        invalid-token-handler :log-out
;        response-keys [:body]]
;    (utils/api-call url token valid-token-handler invalid-token-handler response-keys))
;  db)
;

(register-handler
  :department-change
  (fn [db [_ id]]
    (let [deps (:departments db)
          dep (first (filter #(= (:departmentid %) (js/parseInt id)) deps))
          updated-emp (assoc (:employee db)
                        :department_id id
                        :department (:department dep)
                        :managerid (:managerid dep)
                        :manager-email (:manager-email dep)
                        :manager-firstname (:manager-firstname dep)
                        :manager-lastname (:manager-lastname dep))]
      (assoc db :employee updated-emp))))

(register-handler
  :input-change
  (enrich validate-employee)
  (fn [db [_ property-name new-value]]
    (assoc-in db [:employee property-name] new-value)))

(register-handler
  :input-change-dates
  (enrich validate-employee)
  (fn [db [_ property-name new-value]]
    (let [date-value (try (unparse british-date-format new-value)
                          (catch :default e new-value))]
      (assoc-in db [:employee property-name] date-value))))

(register-handler
  :input-change-balances
  (fn [db [_ property-name new-value]]
    (assoc-in db [:employee property-name] (utils/parse-int new-value))))

(register-handler
  :employee-add
  (fn [db [_ departmentid managerid]]
    (let [dep (first (filter #(= (:departmentid %) departmentid) (:departments-and-employees db)))]
      (-> db
          (merge
            {:nav-bar :employees :view :employee-add
             :employee
                      {:is-ready?               true
                       :id                      0
                       :firstname               "John"
                       :lastname                "Doe"
                       :email                   "johndoe@somewhere.com"
                       :prev_year_allowance     25
                       :current_year_allowance  25
                       :next_year_allowance     25
                       :dob                     (parse (formatter "yyyyMMdd") "19800101")
                       :startdate               (parse (formatter "yyyyMMdd") "20000101")
                       :enddate                 nil
                       :department_id           departmentid
                       :managerid               managerid
                       :manager-firstname       (:manager-firstname dep)
                       :manager-lastname        (:manager-lastname dep)
                       :manager-email           (:manager-email dep)
                       :password                "password1"
                       :confirmation            "password1"
                       :is_approver             false
                       }})))))


(defn show-conflict-error [response]
  (if (= (:status response) 409)                            ;; Conflict
    (dispatch [:show-failed-save-attempt {:email (get-in response [:body :employee])}])
    (do
      ;(utils/set-hash! (r/url-for :employees))
      (dispatch [:set-active-view :employees]))))


(register-handler
  :save-success
  (fn [db [_]]
    (js/alert "Employee saved!!!")
    db))

(register-handler
  :save-failure
  (fn [db [_]]
    (js/alert "Oooops, failed saving employee :(")
    db))


(register-handler
  :employee-save
  (fn [db [_ endpoint]]
    (let [employee (:employee db)]
      (if (apply b/valid? employee employee-validation-rules)
        (utils/send-data-to-api :POST
                                (str (utils/api db endpoint)) (:authentication-token db) employee
                                {:valid-token-handler   :process-departments-only-response
                                 :invalid-token-handler :log-out
                                 :response-keys         [:body :departments]})
        (validate-employee db _)))))

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
    (utils/set-cookie! "auth" token {:max-age expiry :path "/"})))

(register-handler
  :authenticated
  (fn [db [_ token]]
    (r/set-route-token! [:home])
    (assoc db :authentication-token token :authentication-failed-msg "" :nav-bar nil :view :home)))

(register-handler
  :authentication-failed
  (fn [db [_]]
    (assoc db :authentication-token ""
              :authentication-failed-msg "Invalid email/password"
              :view :login)))

(defn response-ok? [response]
  (= 200 (:status response)))

(defn build-url [db]
  (str (:api-root-url db) "/authtoken?" "email=" (get-in db [:employee :email]) "&" "password=" (get-in db [:employee :password])))


(register-handler
  :log-in
  (fn [db [_]]
    (go
      (let [url (build-url db)
            response (<! (http/get url))
            token (if (response-ok? response)
                    ((js->clj (response :body)) :token)
                    "")]
        (set-auth-cookie! token)
        (if (empty? token)
          (dispatch [:authentication-failed])
          (dispatch [:authenticated token]))))
    db))

(register-handler
  :log-out
  (fn [db [_]]
    (utils/remove-cookie! "auth")
    (assoc db
      :view :login
      :authentication-failed-msg "")))