;; migrations/20150205184155792-add-not-empty-employees-firstname-and-lastname-email-constraints.clj

(defn up []
  [
   "ALTER TABLE employees RENAME TO tmp_employees;"

   "CREATE TABLE 'employees' ('id' INTEGER PRIMARY KEY NOT NULL ,
    'firstname' TEXT NOT NULL CHECK(firstname <> ''),
    'lastname' TEXT NOT NULL CHECK(lastname <> ''),
    'email' TEXT NOT NULL CHECK(email <> ''),
    'startdate' DATETIME,
    'enddate' DATETIME,
    'departments_id' INTEGER NOT NULL ,
    'managerid' INTEGER NOT NULL ,
    dob date null,
    salt varchar(255) not null DEFAULT '',
    password varchar(255) not null DEFAULT 'password',
    this_year_opening int not null DEFAULT 0,
    this_year_remaining int not null DEFAULT 0,
    next_year_opening int not null DEFAULT 0,
    next_year_remaining int not null DEFAULT 0)
    ;"

   "INSERT INTO employees SELECT * FROM tmp_employees"

   "DROP TABLE tmp_employees"
  ])

(defn down []
  [
   "ALTER TABLE employees RENAME TO tmp_employees;"
   "CREATE TABLE 'employees' ('id' INTEGER PRIMARY KEY NOT NULL ,
    'firstname' TEXT NOT NULL,
    'lastname' TEXT NOT NULL,
    'email' TEXT NOT NULL,
    'startdate' DATETIME,
    'enddate' DATETIME,
    'departments_id' INTEGER NOT NULL ,
    'managerid' INTEGER NOT NULL ,
    dob date null,
    salt varchar(255) not null DEFAULT '',
    password varchar(255) not null DEFAULT 'password',
    this_year_opening int not null DEFAULT 0,
    this_year_remaining int not null DEFAULT 0,
    next_year_opening int not null DEFAULT 0,
    next_year_remaining int not null DEFAULT 0);"

   "INSERT INTO employees SELECT * FROM tmp_employees"

   "DROP TABLE tmp_employees"
  ])
