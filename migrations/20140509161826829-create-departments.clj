;; Migrations tool is clj-sql-up

;; 20140509161826829
(defn up []
  ["CREATE TABLE departments (id serial PRIMARY KEY, department varchar(255) not null)"])

(defn down []
  ["drop table departments"])
