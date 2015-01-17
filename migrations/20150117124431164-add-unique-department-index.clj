;; migrations/20150117124431164-add-unique-department-index.clj

(defn up []
  ["CREATE UNIQUE INDEX department_idx ON departments(department)"])

(defn down []
  ["DROP INDEX department_idx"])
