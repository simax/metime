;; migrations/20140509161826829-create-departmentvarchar(255) nullvarchar(255) nullvarchar(255) nullvarchar(255) nullvarchar(255) nullvarchar(255) nullvarchar(255) nullvarchar(255) nullvarchar(255) nullvarchar(255) nullvarchar(255) nullvarchar(255) nullvarchar(255) nulls.clj

(defn up []
  ["CREATE TABLE departments (id serial PRIMARY KEY, department varchar(255) not null)"])

(defn down []
  ["drop table departments"])
