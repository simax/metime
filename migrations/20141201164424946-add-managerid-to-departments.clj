;; migrations/20141201164424946-add-managerid-to-departments.clj

(defn up []
  ["ALTER TABLE departments ADD COLUMN managerid integer"])

(defn down []
  [])
