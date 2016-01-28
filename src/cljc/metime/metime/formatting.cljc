(ns metime.formatting
  (:require
    #?(:cljs [cljs.reader :as rdr])
    [clojure.string :as string]))

(defn pad-with-leading-zero [s]
  (let [num
        #?(:cljs (rdr/read-string s))
        #?(:clj (read-string s))]
    (if (= 1 (count (str num)))
      (str "0" num)
      (str num))))

(defn format-date [date]
  "Change date format from 31-12-1999 to 1999-12-31"
  (if (empty? date)
    ""
    (->> (string/split date, #"-")
         (map pad-with-leading-zero)
         (reverse)
         (interpose "-")
         (apply str))))


