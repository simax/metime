(ns metime.formatting
  (:require
    [clojure.string :as string]))

(defn pad-with-leading-zero [s]
  (if (= 1 (count s))
    (str "0" s)
    s))


(defn format-date-reverse [date]
  "Change date format from 31-12-1999 to 1999-12-31"
  (if (empty? date)
    ""
    (->> (string/split date, #"-")
         (map pad-with-leading-zero)
         (reverse)
         (interpose "-")
         (apply str))))

(defn format-date [date]
  "Change date format from 31-12-1999 to 1999-12-31"
  (if (empty? date)
    ""
    (->> (string/split date, #"-")
         (map pad-with-leading-zero)
         (interpose "-")
         (apply str))))




