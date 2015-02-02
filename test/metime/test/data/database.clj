(ns metime.test.data.database
  (:require [expectations :refer :all]
            [metime.data.departments :as deps]
            [metime.data.employees :as emps]))



(defn in-context
  {:expectations-options :in-context}
  [work]
  (with-redefs [deps/db-spec {:classname "org.sqlite.JDBC"
                              :subprotocol "sqlite"
                              ;;:subname "/users/simonlomax/documents/development/clojure projects/metime/data/metime.test"}
                              :subname "data/metime.test"}
                ]

    (println (str "Switched to : " deps/db-spec))
    (deps/delete-all-departments!)
    (emps/delete-all-employees!)
    (work)
    ))

;; Insert a department
(expect 1
        (let [department-1-input {:department "Department 1" :managerid nil}]
          (deps/insert-department! department-1-input)
          (count (deps/get-all-departments))))

;; Insert an employee into the department
(expect 1
        (let [employee-1-input {:firstname "David"
                                :lastname "Sharpe"
                                :email "davidsharpe@ekmsystems.co.uk"
                                :startdate nil
                                :enddate nil
                                :dob nil
                                :departments_id 1
                                :managerid 0
                                :password "abcd1234"
                                :password-confirm "abcd1234"
                                :this_year_opening 25
                                :this_year_remaining 25
                                :next_year_opening 25
                                :next_year_remaining 25
                                }]

          (emps/insert-employee! employee-1-input)
          (count (emps/get-all-employees))))


;;   (def employee-2-input {
;;                          :firstname "Adam"
;;                          :lastname "Duckett"
;;                          :email "adamduckett@ekmsystems.co.uk"
;;                          :startdate nil
;;                          :enddate nil
;;                          :dob nil
;;                          :departments_id 1
;;                          :managerid 1
;;                          :password "abcd1234"
;;                          :password-confirm "abcd1234"
;;                          :this_year_opening 25
;;                          :this_year_remaining 25
;;                          :next_year_opening 25
;;                          :next_year_remaining 25
;;                          })


;;   (deps/insert-department! department-1-input)
;;   (emps/insert-employee! employee-1-input)
;;   (emps/insert-employee! employee-2-input))


;; (def employee-2-output {:id 2
;;                         :firstname "Adam",
;;                         :lastname "Duckett",
;;                         :email "adamduckett@ekmsystems.co.uk",
;;                         :dob nil
;;                         :managerid 1
;;                         :manager-firstname "David",
;;                         :manager-lastname "Sharpe",
;;                         :manager-email "davidsharpe@ekmsystems.co.uk",
;;                         :startdate nil,
;;                         :enddate nil,
;;                         :departments_id 1
;;                         :this_year_opening 25,
;;                         :this_year_remaining 25,
;;                         :next_year_opening 25,
;;                         :next_year_remaining 25
;;                         })


;; (expect employee-2-output (dissoc (emps/get-employee-by-id 2) :salt :password)


;; (expect {
;;          :next_year_remaining 0,
;;          :manager-firstname "David",
;;          :email "davidsharpe@ekmsystems.co.uk",
;;          :this_year_remaining 0,
;;          :manager-dob nil,
;;          :lastname "Sharpe",
;;          :next_year_opening 0,
;;          :manager-email "davidsharpe@ekmsystems.co.uk",
;;          :enddate nil,
;;          :manager-lastname "Sharpe",
;;          :firstname "David",
;;          :startdate nil,
;;          :this_year_opening 0}
;;         (emps/get-employee-by-id 10))

;;)

;; (defn in-context
;;   "Switch db-spec so we connect to the test DB, not the production DB"
;;   {:expectations-options :in-context}
;;   [work]

;;   (println "Switching to metime.test")
;;   (with-redefs [deps/db-spec {:classname "org.sqlite.JDBC"
;;                             :subprotocol "sqlite"
;;                             :subname "data/metime.test"}]


;;     ;;(load-db-with-test-data)
;;     (work)))


;; (defn after-run
;;   "rebind a var, expecations are run in the defined context"
;;   {:expectations-options :after-run}
;;   []

;;   (do
;;     (println "** Switching to metime.sqlite **")
;;     ;;(switch-db "data/metime.sqlite")
;;     ))

