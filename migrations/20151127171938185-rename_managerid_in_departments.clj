;; migrations/20151127171938185-rename_managerid_in_departments.clj

(defn up []
  ["CREATE TABLE dept_backup(id integer PRIMARY KEY NOT NULL,
                             department varchar(255) not null,
                             manager_id integer null);"

   "INSERT INTO dept_backup
        SELECT
         id,
         department,
         managerid as manager_id
        FROM departments;"
   "DROP TABLE departments;"
   "ALTER TABLE dept_backup RENAME TO departments;"])

(defn down []
  [])
