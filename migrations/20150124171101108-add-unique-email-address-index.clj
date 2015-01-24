;; migrations/20150124171101108-add-unique-email-address-index.clj

(defn up []
  ["CREATE UNIQUE INDEX email_idx ON employees(email)"])

(defn down []
  ["DROP INDEX email_idx"])
