;; migrations/20151220121508646-add-isadmin-to-employees.clj

(defn up []
  ["ALTER TABLE employees ADD COLUMN isadmin integer not null DEFAULT 0"])

(defn down []
  [])
