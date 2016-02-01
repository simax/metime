(ns metime.formatting
  (:require
    [clojure.string :as string]
    #?(:cljs [cljs-time.format :refer [formatter parse unparse]])
    #?(:cljs [cljs-time.core :refer [within? before? after? date-time now days minus day-of-week]])
    #?(:cljs [cljs-time.coerce])
       ))

(defn pad-with-leading-zero [s]
  (if (= 1 (count s))
    (str "0" s)
    s))

(defn format-date-yyyy-mm-dd [date-str]
  "Pad with zeros if necessary and return as 1999-12-31"
  (if (empty? date-str)
    ""
    (->> (string/split date-str, #"-")
         (map pad-with-leading-zero)
         (reverse)
         (interpose "-")
         (apply str))))

(defn format-date-dd-mm-yyyy [date-str]
  "Pad with zeros if necessary and return as 31-12-1999"
  (if (empty? date-str)
    ""
    (->> (string/split date-str, #"-")
         (map pad-with-leading-zero)
         (interpose "-")
         (apply str))))
#?(:cljs
     (defn date->str [date]
       "Return an empty string is passed nil or an invalid date.
        Otherwise, returns a correctly formatted date string
        in the dd-MM-yyyy format e.g. 01-01-2015."
       (if (nil? date)
         ""
         (let [formatted-date-str (try (unparse (formatter "dd-MM-yyyy") date)
                                       (catch :default e date))]
           formatted-date-str))))

#?(:cljs

     (defn str->date [date-str]
       "Returns a date object from date-str. Returns nil if date-str is empty"
       (if (empty? date-str)
         nil
         (let [d (format-date-yyyy-mm-dd date-str)]
           (try (parse (formatter "yyyy-MM-dd") d)
                (catch :default e nil)))))
     )
