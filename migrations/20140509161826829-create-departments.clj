;; Migrations tool is clj-sql-up

;; 20140509161826829
(defn up []
  ["CREATE TABLE departments (id integer PRIMARY KEY NOT NULL, department varchar(255) not null)"])

(defn down []
  ["drop table departments"])
