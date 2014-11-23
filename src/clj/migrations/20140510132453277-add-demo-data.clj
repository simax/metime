;; migrations/20140510132453277-add-demo-data.clj

(defn up []
  [
    "insert into departments (department) values ('A DEPARTMENT');"
    "insert into departments (department) values ('Design');"
    "insert into departments (department) values ('Development');"
    "insert into departments (department) values ('ekmresponse');"
    "insert into departments (department) values ('Marketing');"
    "insert into departments (department) values ('Retention');"
    "insert into departments (department) values ('Sales Inbound');"
    "insert into departments (department) values ('Sales Outbound');"
    "insert into departments (department) values ('Special');"
    "insert into departments (department) values ('Support');"
    "insert into departments (department) values ('Zod');"
    "insert into departments (department) values ('Uknown');"
  ])

(defn down []
  ["delete from departments"])
