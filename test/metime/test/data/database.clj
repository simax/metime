(ns metime.test.data.database
  (:require [expectations :refer :all]
            [metime.data.departments :as deps]
            [metime.data.employees :as emps]
            [metime.data.database :as db]))


(defn in-context
  {:expectations-options :in-context}
  [work]
  (with-redefs [db/db-spec {:classname "org.sqlite.JDBC"
                              :subprotocol "sqlite"
                              :subname "data/metime.test"}
                ]

    ;;(println (str "Using DB : " (:subname db/db-spec)))
    (deps/delete-all-departments db/db-spec)
    (emps/delete-all-employees db/db-spec)

    (work)))

    ;; ---------------------- Inserting departments ----------------------

    ;; Successfully insert a department
    (expect (more-of [:as inserted-departments]
                     1              (count inserted-departments)
                     "Department 1" (:department (first inserted-departments)))
                      (let [department-1-input {:department "Department 1" :managerid nil}]
                        (deps/insert-department db/db-spec department-1-input)
                        (deps/get-all-departments db/db-spec)))


    ;; Expect a SQL exception if attempting to insert a duplicate department
    (expect java.sql.SQLException
            (let [department-1-input {:department "Department 1" :managerid nil}]
              (deps/insert-department db/db-spec department-1-input)
              (deps/insert-department db/db-spec department-1-input)))    ;; Expect a SQL exception if attempting to insert a duplicate department

    ;; Expect to get a SQL exception if attempting to insert a department with no name
    (expect java.sql.SQLException
            (let [department-1-input {:department "" :managerid nil}]
              (deps/insert-department db/db-spec department-1-input)))



    ;; ---------------------- Updating departments ----------------------

    ;; Expect to update the department name given an existing department in the DB
    (expect "Updated Department 1"
            (let [department-1-input {:department "Department 1" :managerid nil}
                  department-1-update {:id 1 :department "Updated Department 1" :managerid nil}]
              (deps/insert-department db/db-spec department-1-input)
              (deps/update-department db/db-spec department-1-update)
              (:department (deps/get-department-by-id 1))))

    ;; Expect to receive nil if attempting to update a non existant department
    (expect nil
            (let [department-1-update {:id 1 :department "Updated Department 1" :managerid nil}]
              (deps/update-department db/db-spec department-1-update)
              (:department (deps/get-department-by-id 1))))


    ;; ---------------------- Deleting departments ----------------------

    ;; Expect to delete the department given an existing department ID.
    ;; Providing the department contains no employees.
    (expect true
            (let [department-1-input {:department "Department 1" :managerid nil}]
              (deps/insert-department db/db-spec department-1-input)
              (deps/delete-department db/db-spec 1)))

    ;;Expect false if attempting to delete a department that contains employees
    (expect false
            (let [employee-1-input {:firstname "David"
                                    :lastname "Sharpe"
                                    :email "davidsharpe@ekmsystems.co.uk"
                                    :startdate nil
                                    :enddate nil
                                    :dob nil
                                    :department_id 1
                                    :managerid 0
                                    :password "abcd1234"
                                    :password-confirm "abcd1234"
                                    :this_year_opening 25
                                    :this_year_remaining 25
                                    :next_year_opening 25
                                    :next_year_remaining 25
                                    }
                  department-1-input {:department "Department 1" :managerid nil}]

              (emps/insert-employee db/db-spec employee-1-input)
              (deps/insert-department db/db-spec department-1-input)
              (deps/delete-department db/db-spec 1)))


    ;; ---------------------- Fetching departments ----------------------

    ;; Expect to get correct department record when using deps/get-department-by-id with
    ;; department id that exists in the DB.
    (expect "Department 1"
            (let [department-1-input {:department "Department 1" :managerid nil}]
              (deps/insert-department db/db-spec department-1-input)
              (:department (deps/get-department-by-id db/db-spec 1))))

    ;; Expect to get nil when using deps/get-department-by-id with
    ;; department id that does not exists in the DB.
    (expect nil
            (let [department-1-input {:department "Department 1" :managerid nil}]
              (deps/insert-department db/db-spec department-1-input)
              (:department (deps/get-department-by-id db/db-spec 2))))

    ;; Expect to get correct department record when using deps/get-department-by-name with
    ;; department name that exists in the DB.
    (expect "Department 1"
            (let [department-1-input {:department "Department 1" :managerid nil}]
              (deps/insert-department db/db-spec department-1-input)
              (:department (first (deps/get-department-by-name db/db-spec "Department 1")))))

    ;; Expect to get nil when using deps/get-department-by-name with
    ;; department name that does not exist in the DB.
    (expect nil
            (let [department-1-input {:department "Department 1" :managerid nil}]
              (deps/insert-department db/db-spec department-1-input)
              (:department (first (deps/get-department-by-name db/db-spec "Incorrect Department")))))



    ;; ---------------------- Inserting  employees ----------------------

    ;; Successfully Insert an employee
    (expect 1
            (let [employee-1-input {:firstname "David"
                                    :lastname "Sharpe"
                                    :email "davidsharpe@ekmsystems.co.uk"
                                    :startdate nil
                                    :enddate nil
                                    :dob nil
                                    :department_id 1
                                    :managerid 0
                                    :password "abcd1234"
                                    :password-confirm "abcd1234"
                                    :this_year_opening 25
                                    :this_year_remaining 25
                                    :next_year_opening 25
                                    :next_year_remaining 25
                                    }]

              (emps/insert-employee db/db-spec employee-1-input)
              (count (emps/get-all-employees db/db-spec))))

    ;; Expect a SQL exception if attempting to insert an employee with a duplicate email address
    (expect java.sql.SQLException
            (let [original-employee-input
                  {:firstname "David"
                   :lastname "Sharpe"
                   :email "davidsharpe@ekmsystems.co.uk"
                   :startdate nil
                   :enddate nil
                   :dob nil
                   :department_id 1
                   :managerid 0
                   :password "abcd1234"
                   :password-confirm "abcd1234"
                   :this_year_opening 25
                   :this_year_remaining 25
                   :next_year_opening 25
                   :next_year_remaining 25
                   }
                  duplicate-employee-input original-employee-input]

              (emps/insert-employee db/db-spec original-employee-input)
              (emps/insert-employee db/db-spec duplicate-employee-input)))

    ;; Expect to get a SQL exception if attempting to insert an employee
    ;; with no firstname, lastname or email address
    ;; Note: This test sets all 3 required fields to empty.
    ;; One of them would be enough to cause the expected SQL exception.

    (expect java.sql.SQLException
            (let [invalid-employee-input
                  {:firstname ""
                   :lastname ""
                   :email ""
                   :startdate nil
                   :enddate nil
                   :dob nil
                   :department_id 1
                   :managerid 0
                   :password "abcd1234"
                   :password-confirm "abcd1234"
                   :this_year_opening 25
                   :this_year_remaining 25
                   :next_year_opening 25
                   :next_year_remaining 25
                   }]

              (emps/insert-employee db/db-spec invalid-employee-input)))

    ;; ---------------------- Updating employees ----------------------

    ;; Expect to update the employee name given the existing employee in the DB
    (expect "Updated Lastname"
            (let [employee-1-input
                  {:firstname "Firstname"
                   :lastname "Lastname"
                   :email "someone@somewehere.com"
                   :startdate nil
                   :enddate nil
                   :dob nil
                   :department_id 1
                   :managerid 0
                   :password "abcd1234"
                   :password-confirm "abcd1234"
                   :this_year_opening 25
                   :this_year_remaining 25
                   :next_year_opening 25
                   :next_year_remaining 25
                   }
                  employee-1-update
                  {:id 1
                   :firstname "Firstname"
                   :lastname "Updated Lastname"
                   :email "someone@somewehere.com"
                   :startdate nil
                   :enddate nil
                   :dob nil
                   :department_id 1
                   :managerid 0
                   :password "abcd1234"
                   :password-confirm "abcd1234"
                   :this_year_opening 25
                   :this_year_remaining 25
                   :next_year_opening 25
                   :next_year_remaining 25
                   }]

              (emps/insert-employee db/db-spec employee-1-input)
              (emps/update-employee db/db-spec employee-1-update)
              (:lastname (emps/get-employee-by-id db/db-spec {:id 1}))))

    ;; Expect to receive nil if attempting to update a non existant department
    (expect nil
            (let [missing-employee
                  {:id 99
                   :firstname "Firstname"
                   :lastname "Updated Lastname"
                   :email "someone@somewehere.com"
                   :startdate nil
                   :enddate nil
                   :dob nil
                   :department_id 1
                   :managerid 0
                   :password "abcd1234"
                   :password-confirm "abcd1234"
                   :this_year_opening 25
                   :this_year_remaining 25
                   :next_year_opening 25
                   :next_year_remaining 25
                   }]

              (emps/update-employee db/db-spec (emps/format-dates missing-employee))
              (:lastname (emps/get-employee-by-id db/db-spec {:id 99}))))



    ;; ---------------------- Deleting employees ----------------------

    ;; Expect to delete the employee given an existing employee ID.
    (expect true
            (let [employee-1-input
                  {:firstname "Firstname"
                   :lastname "Updated Lastname"
                   :email "someone@somewehere.com"
                   :startdate nil
                   :enddate nil
                   :dob nil
                   :department_id 1
                   :managerid 0
                   :password "abcd1234"
                   :password-confirm "abcd1234"
                   :this_year_opening 25
                   :this_year_remaining 25
                   :next_year_opening 25
                   :next_year_remaining 25
                   }]

              (emps/insert-employee db/db-spec employee-1-input)
              (emps/delete-employee db/db-spec {:id 1})))

    ;;Expect false if attempting to delete an employee that doesn't exist
    (expect false (emps/delete-employee db/db-spec {:id 99}))


    ;; ---------------------- Fetching employees ----------------------

    ;; Expect to get correct employee record when employee exists in the DB.
    (expect "someone@somewhere.com"
            (let [employee-1-input
                  {:firstname "Firstname"
                   :lastname "Updated Lastname"
                   :email "someone@somewhere.com"
                   :startdate nil
                   :enddate nil
                   :dob nil
                   :department_id 1
                   :managerid 0
                   :password "abcd1234"
                   :password-confirm "abcd1234"
                   :this_year_opening 25
                   :this_year_remaining 25
                   :next_year_opening 25
                   :next_year_remaining 25
                   }]
              (emps/insert-employee db/db-spec employee-1-input)
              (:email (emps/get-employee-by-id db/db-spec {:id 1}))))

    ;; Expect to get nil when attempting to fetch employee that does not exists in the DB.
    (expect nil (:email (emps/get-employee-by-id db/db-spec {:id 99})))



