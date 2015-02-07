;; migrations/20150205182420660-add-not-empty-departments-department-constraint.clj

(defn up []
  [
   "ALTER TABLE departments RENAME TO tmp_departments;"
   "CREATE TABLE 'departments' ('id' INTEGER PRIMARY KEY NOT NULL UNIQUE , 'department' TEXT NOT NULL CHECK(department <> ''), managerid integer);"

   "INSERT INTO departments SELECT * FROM tmp_departments"

   "DROP TABLE tmp_departments"
  ])

(defn down []
  [
   "ALTER TABLE departments RENAME TO tmp_departments;"
   "CREATE TABLE 'departments' ('id' INTEGER PRIMARY KEY  NOT NULL  UNIQUE , 'department' TEXT NOT NULL, managerid integer);"

   "INSERT INTO departments SELECT * FROM tmp_departments"

   "DROP TABLE tmp_departments"
  ])
