;; migrations/20140510132453277-add-demo-data.clj

(defn up []
  ["insert into departments (department) values ('Development');"
   "insert into departments (department) values ('Design');"
   "insert into departments (department) values ('Customer Support');"

   "insert into employees (firstname, lastname, email, departments_id, managerid)
    values ('Antony', 'Chesworth', 'antonychesworth@ekmsystems.co.uk',
    (select id from departments where department='EKM'),
     NULL)";

   "insert into employees (firstname, lastname, email, departments_id, managerid)
    values ('Nick', 'Stansfield', 'nickstansfield@ekmsystems.co.uk',
    (select id from departments where department='Development'),
    (select id from employees where lastname='Chesworth'));"

   "insert into employees (firstname, lastname, email, departments_id, managerid)
    values ('Simon', 'Lomax', 'simonlomax@ekmsystems.co.uk',
    (select id from departments where department='Development'),
    (select id from employees where lastname='Stansfield'));"

   "insert into employees (firstname, lastname, email, departments_id, managerid)
    values ('Simon', 'Wriggley', 'simonwriggley@ekmsystems.co.uk',
    (select id from departments where department='Development'),
    (select id from employees where lastname='Stansfield'));"

   "insert into employees (firstname, lastname, email, departments_id, managerid)
    values ('Lisa', 'Young', 'lisayoung@ekmsystems.co.uk',
    (select id from departments where department='Development'),
    (select id from employees where lastname='Stansfield'));"

   "insert into employees (firstname, lastname, email, departments_id, managerid)
    values ('Dave', 'Sharpe', 'davesharpe@ekmsystems.co.uk',
    (select id from departments where department='Design'),
    (select id from employees where lastname='Chesworth'));"

   "insert into employees (firstname, lastname, email, departments_id, managerid)
    values ('John', 'Slater', 'johnslater@ekmsystems.co.uk',
    (select id from departments where department='Design'),
    (select id from employees where lastname='Sharpe'));"

  ])

(defn down []
  [])
