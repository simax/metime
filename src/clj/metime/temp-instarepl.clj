;; Anything you type in here will be executed
;; immediately with the results shown on the
;; right.


(require
 '[metime.resources :as res]
 '[metis.core :refer [defvalidator] :as v]
 '[metime.data.departments :as d]
 '[clj-time.core :as t]
 '[clj-time.format :as f]
 '[clj-time.coerce :as c]
 '[korma.core :as kc]
 '[korma.db :as kd]
 '[metime.data.employees :as emps])


(def db (korma.db/sqlite3
         {:classname "org.sqlite.JDBC"
          :subprotocol "sqlite"
          :subname "data/metime.test"}))


(exec-raw db "insert into departments ('department', 'managerid') values ('bbb - department', 10)" :keys)

;; (d/get-department-by-id 3)

(exec-raw db ["select * from departments where id = ?" [3]] :results)


(emps/get-all)


;; (defn date [m k _]
;;   (if (seq (k m))
;;     (try
;;       (f/parse (f/formatters :date) (k m))
;;       nil
;;       (catch Exception e
;;         "Invalid date"))
;;     ))

;; (def x {})

;; (not (seq x))

;; (if-let [simon {}] :true :false)

;; (defvalidator employee-validator
;;   ;;[:id :numericality {:only-integer true :greater-than 0 :only :adding}]
;;   [:firstname :length {:greater-than 0 :less-than 31}]
;;   [:lastname :length {:greater-than 0 :less-than 31}]
;;   [:email :email {:greater-than 0 :less-than 31}]
;;   [:departments_id :numericality {:only-integer true :greater-than 0}]
;;   [:managerid :numericality {:only-integer true :greater-than 0}]
;;   [:password [:length {:greater-than-or-equal-to 8}
;;               :formatted {:pattern #"(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}"
;;                           :message "alpha numeric, at least one number"}
;;               :confirmation {:confirm :password-confirm}]]
;;   [:dob :date])


;; (def my-employee {   :firstname "John"
;;                      :lastname "Doe"
;;                      :email "a@a.com"
;;                      :departments_id 4
;;                      :managerid 27
;;                      :password "abcd1234"
;;                      :password-confirm "abcd1234"
;;                      :dob "2015-01-24"
;;                      })

;; (employee-validator my-employee)

;; (emps/prepare-for-insert my-employee)


;; (date {:dob ""} :dob nil)

;; (str (t/date-time 2015 01 24))

;; (def built-in-formatter (f/formatters :basic-date-time))

;; (def custom-formatter (f/formatter "dd/MM/yyyy"))

;; ;;(f/parse built-in-formatter "2015-01-01T00:00:000")

;; (f/parse (f/formatters :date) "2015-01-24")

;; (c/to-date "2015-01-24")

;; ;; (f/show-formatters)

;; (defn uuid [] (str (java.util.UUID/randomUUID)))

;; (uuid)

;; ;; select email, count(*) as cnt from employees group by email having cnt > 1 order by email
