;; migrations/20150110161047682-add-misc-employee-fields.clj

(defn up []
  [
   "ALTER TABLE employees ADD COLUMN dob date null"
   "ALTER TABLE employees ADD COLUMN salt varchar(255) not null DEFAULT ''"
   "ALTER TABLE employees ADD COLUMN password varchar(255) not null DEFAULT 'password'"
   "ALTER TABLE employees ADD COLUMN this_year_opening int not null DEFAULT 0"
   "ALTER TABLE employees ADD COLUMN this_year_remaining int not null DEFAULT 0"
   "ALTER TABLE employees ADD COLUMN next_year_opening int not null DEFAULT 0"
   "ALTER TABLE employees ADD COLUMN next_year_remaining int not null DEFAULT 0"])

;; No easy way to drop columns in SQLite.
;; Need to create a new table, minus the unrequired column(s).
;; Copy existing records to new table
;; Drop original table
;; Rename new table to original
(defn down []
  [])
