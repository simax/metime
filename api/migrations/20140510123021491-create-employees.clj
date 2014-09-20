;; migrations/20140510123021491-create-employees.clj

(defn up []
  ["CREATE TABLE employees (id serial PRIMARY KEY,
                            firstname varchar(255) null,
                            lastname varchar(255) null,
                            email varchar(255) null,
                            startdate date null,
                            enddate date null,
                            active boolean DEFAULT true,
                            departments_id integer references departments(id) ON DELETE CASCADE,
                            managerid integer references employees(id) null)"])

(defn down []
  ["drop table employees"])



