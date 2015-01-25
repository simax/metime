(ns metime.test.data.departments
  (:require [expectations :refer :all]
            [metime.data.employees :as emps]))

(defn before-run
  "rebind a var, expecations are run in the defined context"
  {:expectations-options :before-run}
  []
  (korma.db/defdb test-db (korma.db/sqlite3
                           {:classname "org.sqlite.JDBC"
                            :subprotocol "sqlite"
                            :subname "data/metime.test"}))
  (println "Before run"))


(defn after-run
  "rebind a var, expecations are run in the defined context"
  {:expectations-options :after-run}
  []
  (korma.db/defdb production-db (korma.db/sqlite3
                                 {:classname "org.sqlite.JDBC"
                                  :subprotocol "sqlite"
                                  :subname "data/metime.sqlite"}))
  (println "After run"))


(expect {:next_year_remaining 0,
         :manager-firstname "David",
         :email "adamduckett@ekmsystems.co.uk",
         :this_year_remaining 0,
         :manager-dob nil,
         :lastname "Duckett",
         :next_year_opening 0,
         :manager-email "davidsharpe@ekmsystems.co.uk",
         :enddate nil,
         :manager-lastname "Sharpe",
         :firstname "Adam (Test)",
         :startdate nil,
         :this_year_opening 0}
        (emps/get-employee-by-id 18))

(expect {:next_year_remaining 0,
         :manager-firstname "David",
         :email "adamduckett@ekmsystems.co.uk",
         :this_year_remaining 0,
         :manager-dob nil,
         :lastname "Duckett",
         :next_year_opening 0,
         :manager-email "davidsharpe@ekmsystems.co.uk",
         :enddate nil,
         :manager-lastname "Sharpe",
         :firstname "Adam (Test)",
         :startdate nil,
         :this_year_opening 0}
        (emps/get-employee-by-id 19))
