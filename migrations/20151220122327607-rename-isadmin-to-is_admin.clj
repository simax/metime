;; migrations/20151220122327607-rename-isadmin-to-is_admin.clj

(defn up []
  ["CREATE TABLE emp_backup(id integer PRIMARY KEY NOT NULL,
                           firstname varchar(255) null,
                           lastname varchar(255) null,
                           email varchar(255) null,
                           startdate date null,
                           enddate date null,
                           department_id integer ,
                           manager_id integer null,
                           is_approver integer not null,
                           dob date null,
                           salt varchar(255) not null DEFAULT '',
                           password varchar(255) not null DEFAULT 'password',
                           prev_year_allowance integer not null,
                           current_year_allowance integer not null,
                           next_year_allowance integer not null,
                           is_admin integer not null DEFAULT 0
                           );"

   "INSERT INTO emp_backup
        SELECT
         id,
         firstname,
         lastname,
         email,
         startdate,
         enddate,
         department_id,
         manager_id,
         0 as is_approver,
         dob,
         salt,
         password,
         0 as prev_year_allowance,
         0 as current_year_allowance,
         0 as next_year_allowance,
         0 as is_admin
        FROM employees;"
   "DROP TABLE employees;"
   "ALTER TABLE emp_backup RENAME TO employees;"])
(defn down []
  [])
