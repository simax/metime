;; Migrations tool is clj-sql-up


(defn up []
  ["CREATE TABLE departments (id serial PRIMARY KEY, department varchar(255) not null)"])

(defn down []
  ["drop table departments"])
