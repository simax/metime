(ns metime.employees.handlers
  (:require-macros
    [cljs.core.async.macros :refer [go]]
    [bouncer.validators :refer [defvalidator]])
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

;utils/call-api :GET (utils/api db endpoint) (:authentication-token db)
;{:valid-token-handler   :process-departments-only-response
; :invalid-token-handler :log-out
; :response-keys         [:body :departments]}



;(defvalidator unique-email
;              {:default-message-format "Email already exists"}
;              [email-value id-key subject] ; subject is the employee map
;              (let [id (get subject (keyword id-key))]
;                (when-let [emp (emps/get-employee-by-email email-value)]
;                  (= id (:id emp)))))

(def employee-validation-rules
  [:firstname [[v/required :message "First name is required"]]
   :lastname [[v/required :message "Last name is required"]]
   :email [[v/required :message "An email address is required"] [v/email :message "Please supply a valid email address"]]
   :dob [[v/required :message "A date of birth is required"] [v/datetime british-date-format :pre (comp seq :dob) :message "Must be a valid date"]]
   :startdate [[v/datetime british-date-format :pre (comp seq :startdate) :message "Must be a valid date"]]
   :enddate [[v/datetime british-date-format :pre (comp seq :enddate) :message "Must be a valid date"]]
   :prev_year_allowance [[v/integer :message "Must be an integer"]]
   :current_year_allowance [[v/integer :message "Must be an integer"]]
   :next_year_allowance [[v/integer :message "Must be an integer"]]])

(defn validate-employee [db]
  (let [employee (:employee db)
        result [(first (apply b/validate employee employee-validation-rules))
                ;(first (b/validate employee :email [[v/required] [unique-email "id" employee]]))
                ]
        errors (first result)]
    (assoc-in db [:employee :validation-errors] errors)))

(register-handler
  :department-change
  (fn [db [_ id]]
    (let [deps (:departments db)
          dep (first (filter #(= (:departmentid %) (js/parseInt id)) deps))
          updated-emp (assoc (:employee db)
                        :department_id id
                        :department (:department dep)
                        :managerid (:manager_id dep)
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
  (fn [db [_ departmentid]]
    (let [dep (first (filter #(= (:departmentid %) departmentid) (:departments-and-employees db)))]
      (-> db
          (merge
            {:nav-bar :employees
             :view :employee-add
             :employee
                      {:is-ready?               true
                       :id                      0
                       :firstname               ""
                       :lastname                ""
                       :email                   ""
                       :prev_year_allowance     25
                       :current_year_allowance  25
                       :next_year_allowance     25
                       :dob                     nil
                       :startdate               nil
                       :enddate                 nil
                       :department_id           departmentid
                       :managerid               (:manager_id dep)
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
  :switch-view-to-employees
  (fn [db [_ _]]
    (dispatch [:set-active-view :employees])
    db))

(defn build-employee-add-endpoint [db]
  (str (utils/api db "/employees")))

(defn build-employee-update-endpoint [db employee]
  (str (utils/api db (str  "/employee/" (:id employee)))))


(defn add-new-employee [db employee]
  (utils/send-data-to-api :POST
                          (build-employee-add-endpoint db) (:authentication-token db) employee
                          {:valid-token-handler   :switch-view-to-employees
                           :invalid-token-handler :save-failure
                           :response-keys         [:body :departments]}))

(defn update-employee [db employee]
  (utils/send-data-to-api :PUT
                          (build-employee-update-endpoint db employee) (:authentication-token db) employee
                          {:valid-token-handler   :switch-view-to-employees ; Needs to be seq so we can send paramaters too
                           :invalid-token-handler :save-failure
                           :response-keys         [:body :departments]}))


(register-handler
  :employee-save
  (fn [db [_]]
    (let [employee (:employee db)]
      (when (apply b/valid? employee employee-validation-rules)
        (if (is-new-employee? (:id employee))
          (add-new-employee db employee)
          (update-employee db employee)))
      (validate-employee db))))

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