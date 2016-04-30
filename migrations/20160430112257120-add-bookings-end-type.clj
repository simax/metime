;; migrations/20160430112257120-add-bookings-end-type.clj

(defn up []
  ["ALTER TABLE bookings ADD COLUMN end_type integer [start_type] varchar(30) null"])

(defn down []
  ["DROP TABLE bookings"])
