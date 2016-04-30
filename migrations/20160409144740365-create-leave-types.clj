;; migrations/20160409144740365-create-leave-types.clj

(defn up []
  ["CREATE TABLE leave_types (id integer PRIMARY KEY NOT NULL, leave_type varchar(255) not null, reduce_leave integer not null)"])

(defn down []
  ["drop table leave_types"])