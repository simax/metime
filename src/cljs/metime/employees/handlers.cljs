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
            [re-frame-tracer.core :refer [tracer]]
            [metime.common.handlers]))

;(trace-forms
; {:tracer (tracer :color "green")}

(defn is-new-employee? [id]
  (zero? id))

(defn is-new-department? [id]
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

(def department-validation-rules
  [:department [[v/required :message "Department name is required"]]
   :manager-id [[v/required :message "A manager is required"]]])

(def employee-validation-rules
  [:firstname [[v/required :message "First name is required"]]
   :lastname [[v/required :message "Last name is required"]]
   :email [[v/required :message "An email address is required"] [v/email :message "Please supply a valid email address"]]
   :dob [[v/required :message "Date of birth is required"] [v/datetime british-date-format :message "Must be a valid date"]]
   :startdate [[v/datetime british-date-format :message "Must be a valid date" :pre (comp seq :startdate)]]
   :enddate [[v/datetime british-date-format :pre (comp seq :enddate) :message "Must be a valid date"]]
   :prev-year-allowance [[v/integer :message "Must be an integer"]]
   :current-year-allowance [[v/integer :message "Must be an integer"]]
   :next-year-allowance [[v/integer :message "Must be an integer"]]])


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

(defn validate-department [db]
  (let [department (:department db)
        result [(first (apply b/validate department department-validation-rules))]
        errors (first result)]
    (assoc-in db [:department :validation-errors] errors)))

(register-handler
  :department-change
  (fn hdlr-department-change [db [_ id]]
    (let [deps (:departments db)
          dep (first (filter #(= (:department-id %) (js/parseInt id)) deps))
          updated-emp (assoc (:employee db)
                        :department-id id
                        :department (:department dep)
                        :manager-id (:manager-id dep)
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
  :set-department-manager-id
  (enrich validate-department)
  (fn hdlr-set-department-manager-id [db [_ employee-id]]
    (dispatch [:set-department-manager-email employee-id])
    (assoc-in db [:department :manager-id] employee-id)))

(register-handler
  :set-department-manager-email
  (fn hdlr-input-change [db [_ employee-id]]
    (println (str "employee-id " employee-id))
    (let [email(get (first (filter #(= employee-id (:id %)) (get-in @re-frame.db/app-db [:departments-with-employees]))) :email)]
      (-> db
          (assoc-in [:department :manager-email] email)))))

(register-handler
  :input-change-department-name
  (enrich validate-department)
  (fn hdlr-input-change [db [_ department-name]]
    (assoc-in db [:department :department] department-name)))


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
  (fn hdlr-employee-add [db [_]]
    (let [dep (:department db)]
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
                       :prev-year-allowance    25
                       :current-year-allowance 25
                       :next-year-allowance    25
                       :dob                    nil
                       :startdate              nil
                       :enddate                nil
                       :department-id          (:department-id dep)
                       :department             (:department dep)
                       :manager-id             (:manager-id dep)
                       :manager-firstname      (:manager-firstname dep)
                       :manager-lastname       (:manager-lastname dep)
                       :manager-email          (:manager-email dep)
                       :password               "password1"
                       :confirmation           "password1"
                       :is-approver            false}})))))



(register-handler
  :employee-save-failure
  (fn hdlr-save-failure [db [_]]
    ; Potentially show some kind of boostrap alert?
    db))

(register-handler
  :department-save-failure
  (fn hdlr-save-failure [db [_]]
    ; Potentially show some kind of boostrap alert?
    (println "Problem saving department")
    db))

(register-handler
  :department-save-success
  (fn hdlr-save-failure [db [_]]
    (dispatch [:close-department-drawer])
    (dispatch [:fetch-departments])
    (assoc db :department nil)))


(register-handler
  :switch-view-to-employees
  (fn hdlr-switch-view-to-employees [db [_ _]]
    (when-let [department-id (:department-drawer-open-id db)]
      (dispatch [:fetch-department-employees department-id]))
    (dispatch [:set-active-view :employees])
    db))

(defn build-department-by-id-endpoint [department]
  (routes/api-endpoint-for :department-by-id :id (:department-id department)))

(defn build-employee-update-endpoint [employee]
  (routes/api-endpoint-for :employee-by-id :id (:id employee)))

(defn add-new-department [db department]
  "Add a new department"
  (utils/send-data-to-api :POST
                          (routes/api-endpoint-for :departments) (:authentication-token db) department
                          {:valid-fn      #(dispatch [:department-save-success])
                           :invalid-fn    #(dispatch [:department-save-failure])
                           :response-keys [:body :departments]}))

(defn update-department [db department]
  "Update an existing department"
  (utils/send-data-to-api :PUT
                          (build-department-by-id-endpoint department) (:authentication-token db) department
                          {:valid-fn      #(dispatch [:department-save-success])
                           :invalid-fn    #(dispatch [:department-save-failure])
                           :response-keys [:body :departments]}))

(defn add-new-employee [db employee]
  "Add a new employee via the API"
  (utils/send-data-to-api :POST
                          (routes/api-endpoint-for :employees) (:authentication-token db) employee
                          {:valid-fn      #(dispatch [:switch-view-to-employees])
                           :invalid-fn    #(dispatch [:employee-save-failure])
                           :response-keys [:body :departments]}))

(defn update-employee [db employee]
  "Update an existing employee"
  (utils/send-data-to-api :PUT
                          (build-employee-update-endpoint employee) (:authentication-token db) employee
                          {:valid-fn      #(dispatch [:switch-view-to-employees])
                           :invalid-fn    #(dispatch [:employee-save-failure])
                           :response-keys [:body :departments]}))

(register-handler
  :department-save
  (enrich validate-department)
  (fn hdlr-department-save [db [_]]
    (let [department (:department db)]
      (when (and
              (apply b/valid? department department-validation-rules)
              (not (some? (get-in department [:validation-errors]))))
        (if (is-new-department? (:department-id department))
          (add-new-department db department)
          (update-department db department)))
      db)))

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
  :close-department-drawer
  (fn hdlr-close-department-drawer [db [_]]
    (assoc db
      :department nil
      :department-employees nil
      :department-drawer-open-id nil)))

(register-handler
  :open-department-drawer
  (fn hndlr-open-department-drawer [db [_ department-id]]
    (dispatch [:fetch-department department-id])
    (dispatch [:fetch-department-employees department-id])
    (assoc db
      :department-employees nil
      :department-drawer-open-id department-id)))

(register-handler
  :ui-department-drawer-status-toggle
  (fn hdlr-ui-department-drawer-status-toggle [db [_ department-id]]
    (if (= (:department-drawer-open-id db) department-id)
      (dispatch [:close-department-drawer])
      (dispatch [:open-department-drawer department-id]))
    db))

(register-handler
  :ui-new-department-drawer-status-toggle
  (fn hdlr-ui-new-department-drawer-status-toggle [db [_]]
    (dispatch [:close-department-drawer])
    (if (:new-department-drawer-open? db)
      (assoc db :new-department-drawer-open? false)
      (do
        (dispatch [:new-department])
        (assoc db :new-department-drawer-open? true)))))

(register-handler
  :new-department
  (fn hdlr-new-department [db [_]]
    (assoc db :department {:department-id 0 :department "" :manager-id nil})))

(register-handler
  :edit-department
  (fn hdlr-new-department [db [_ department-id]]
    (dispatch [:close-department-drawer])
    (dispatch [:fetch-department department-id])
    db))

(register-handler
  :show-failed-save-attempt
  (fn hdlr-show-failed-save-attempt [db [_ errors]]
    (assoc-in db [:employee :validation-errors] errors)))

(register-handler
  :employee-delete-success
  (fn hdlr-employee-delete-success [db [_ _]]
    (let [department-id (get-in db [:department :id])]
      (dispatch [:fetch-department-employees department-id])
      (dispatch [:fetch-departments]))
    db))

(register-handler
  :employee-delete
  (fn hdlr-employee-delete [db [_ id]]
    (utils/call-api :DELETE (routes/api-endpoint-for :employee-by-id :id id) db
                    {:success-handler-key :employee-delete-success
                     :response-keys       [:body]})
    db))


(register-handler
  :department-delete
  (fn hdlr-department-delete [db [_ id]]
    (utils/call-api :DELETE (routes/api-endpoint-for :department-by-id :id id) db
                    {:success-handler-key :fetch-departments
                     :response-keys       [:body]})
    db))


;)