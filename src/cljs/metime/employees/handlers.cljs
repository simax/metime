(ns metime.employees.handlers
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
            [re-frame-tracer.core :refer [tracer]]))

;(trace-forms
; {:tracer (tracer :color "green")}

(defn is-new-employee? [id]
  (zero? id))

(def british-date-format (f/formatter "dd-MM-yyyy"))

(defn get-employee-by-email [token email]
  (let [ch (chan)]
    (go
      (let [url (routes/api-endpoint-for :employee-by-email :email email)
            response (<! (utils/call-secure-url :GET token url))]
        (>! ch (:body response))))
    ch))

(defn email-found-for-a-different-employee? [emp id]
  (and (some? (:id emp)) (not= id (:id emp))))

(defn unique-email [email id token]
  (let [ch (chan)]
    (go
      (let [emp (<! (get-employee-by-email token email))]
        (>! ch (cond
                 (email-found-for-a-different-employee? emp id) "Email already in use"
                 (not (some? (:id emp))) ""
                 :else ""))))
    ch))


(def employee-validation-rules
  [:firstname [[v/required :message "First name is required"]]
   :lastname [[v/required :message "Last name is required"]]
   :email [[v/required :message "An email address is required"] [v/email :message "Please supply a valid email address"]]
   :dob [[v/required :message "Date of birth is required"] [v/datetime british-date-format :message "Must be a valid date"]]
   :startdate [[v/datetime british-date-format :message "Must be a valid date" :pre (comp seq :startdate)]]
   :enddate [[v/datetime british-date-format :pre (comp seq :enddate) :message "Must be a valid date"]]
   :prev_year_allowance [[v/integer :message "Must be an integer"]]
   :current_year_allowance [[v/integer :message "Must be an integer"]]
   :next_year_allowance [[v/integer :message "Must be an integer"]]])


(register-handler
  :email-uniqness-violation
  (fn hdlr-email-uniqness-violation [db [_ unique-email-error]]
    (if (not (empty? unique-email-error))
      (assoc-in db [:employee :validation-errors :email] (list unique-email-error))
      db)))

(register-handler
  :validate-email-uniqness
  (fn hdlr-validate-email-uniqness [db [_]]
    (when-not (get-in db [:employee :validation-errors :email])
      (go
        (let [unique-email-error (<! (unique-email (get-in db [:employee :email]) (get-in db [:employee :id]) (:authentication-token db)))]
          (dispatch [:email-uniqness-violation unique-email-error]))))
    db))


(defn validate-employee [db]
  (let [employee (:employee db)
        result [(first (apply b/validate employee employee-validation-rules))]
        errors (first result)]
    (assoc-in db [:employee :validation-errors] errors)))

(register-handler
  :department-change
  (fn hdlr-department-change [db [_ id]]
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
  (fn hdlr-input-change [db [_ property-name new-value]]
    (dispatch [:validate-email-uniqness])
    (assoc-in db [:employee property-name] new-value)))

(register-handler
  :input-change-no-validate
  (fn hdlr-input-change-no-validate [db [_ property-name new-value]]
    (assoc-in db [:employee property-name] new-value)))


(defn check-date-validity [input-date]
  (let [formatted-date (fmt/format-date-dd-mm-yyyy
                   (first
                     (re-find #"^([0]?[1-9]|[1|2][0-9]|[3][0|1])[-]([0]?[1-9]|[1][0-2])[-]([0-9]{4})$" input-date)))]
    (if (try (parse (formatter "dd-MM-yyyy") formatted-date) (catch js/Error _ false))
      formatted-date
      input-date)))

(register-handler
  :datepicker-change-dates
  (fn hdlr-datepicker-change-dates [db [_ property-name new-value]]
    (let [date-value (fmt/date->str new-value)]
      (dispatch [:input-change-dates property-name date-value])
      (assoc-in db [:employee property-name] date-value))))

(register-handler
  :input-change-dates
  (enrich validate-employee)
  (fn hdlr-input-change-dates [db [_ property-name new-value]]
    (let [date-value (check-date-validity new-value)]
      (assoc-in db [:employee property-name] date-value))))

(register-handler
  :input-change-balances
  (fn hdlr-input-change-balances [db [_ property-name new-value]]
    (assoc-in db [:employee property-name] (utils/parse-int new-value))))

(register-handler
  :employee-add
  (fn hdlr-employee-add [db [_ departmentid]]
    (let [dep (first (filter #(= (:departmentid %) departmentid) (:departments-and-employees db)))]
      (-> db
          (merge
            {:nav-bar :employees
             :view    :employee-add
             :employee
                      {:is-ready?              true
                       :id                     0
                       :firstname              ""
                       :lastname               ""
                       :email                  ""
                       :prev_year_allowance    25
                       :current_year_allowance 25
                       :next_year_allowance    25
                       :dob                    nil
                       :startdate              nil
                       :enddate                nil
                       :department_id          departmentid
                       :managerid              (:manager_id dep)
                       :manager-firstname      (:manager-firstname dep)
                       :manager-lastname       (:manager-lastname dep)
                       :manager-email          (:manager-email dep)
                       :password               "password1"
                       :confirmation           "password1"
                       :is_approver            false
                       }})))))


(register-handler
  :save-failure
  (fn hdlr-save-failure [db [_]]
    ; Potentially show some kind of boostrap alert?
    db))


(register-handler
  :switch-view-to-employees
  (fn hdlr-switch-view-to-employees [db [_ _]]
    (dispatch [:set-active-view :employees])
    db))

(defn build-employee-update-endpoint [employee]
  (routes/api-endpoint-for :employee-by-id :id (:id employee)))


(defn add-new-employee [db employee]
  (utils/send-data-to-api :POST
                          (routes/api-endpoint-for :employees) (:authentication-token db) employee
                          {:valid-token-handler   :switch-view-to-employees
                           :invalid-token-handler :save-failure
                           :response-keys         [:body :departments]}))

(defn update-employee [db employee]
  (utils/send-data-to-api :PUT
                          (build-employee-update-endpoint employee) (:authentication-token db) employee
                          {:valid-token-handler   :switch-view-to-employees ; Needs to be seq so we can send paramaters too
                           :invalid-token-handler :save-failure
                           :response-keys         [:body :departments]}))


(register-handler
  :employee-save
  (enrich validate-employee)
  (fn hdlr-employee-save [db [_]]
    (let [employee (:employee db)]
      (dispatch [:validate-email-uniqness])
      (when (and
              (apply b/valid? employee employee-validation-rules)
              (not (some? (get-in employee [:validation-errors]))))
        (if (is-new-employee? (:id employee))
          (add-new-employee db employee)
          (update-employee db employee)))
      db)))

(register-handler
  :ui-department-drawer-status-toggle
  (fn hdlr-ui-department-drawer-status-toggle [db [_ department]]
    (if (= (:department-draw-open-id db) department)
      (assoc db :department-draw-open-id "")
      (assoc db :department-draw-open-id department))))

(register-handler
  :ui-new-department-drawer-status-toggle
  (fn hdlr-ui-new-department-drawer-status-toggle [db [_]]
    (if (:new-department-draw-open? db)
      (assoc db :new-department-draw-open? false)
      (assoc db :new-department-draw-open? true))))

(register-handler
  :show-failed-save-attempt
  (fn hdlr-show-failed-save-attempt [db [_ errors]]
    (assoc-in db [:employee :validation-errors] errors)))

(defn set-auth-cookie! [token]
  (let [expiry (* 60 60 24 30)]                             ; 30 days (secs mins hours days)
    (utils/set-cookie! "auth" token {:max-age expiry :path "/"})))

(register-handler
  :authenticated
  (fn hdlr-authenticated [db [_ token]]
    (routes/set-route-token! [:home])
    (assoc db :authentication-token token :authentication-failed-msg "" :nav-bar nil :view :home)))

(register-handler
  :authentication-failed
  (fn hdlr-authentication-failed [db [_]]
    (assoc db :authentication-token ""
              :authentication-failed-msg "Invalid email/password"
              :view :login)))

(defn response-ok? [response]
  (= 200 (:status response)))

(defn build-url [db]
  (str (routes/api-endpoint-for :authtoken) "?email=" (get-in db [:employee :email]) "&" "password=" (get-in db [:employee :password])))


(register-handler
  :log-in
  (fn hdlr-log-in [db [_]]
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
  (fn hdlr-log-out [db [_]]
    (utils/remove-cookie! "auth")
    (assoc db
      :view :login
      :authentication-failed-msg "")))
;)