(ns metime.utils
  (:require [cljs-hash.md5 :as hashgen]
            [re-frame.core :refer [register-handler
                                   path
                                   register-sub
                                   dispatch
                                   subscribe]])
  (:import goog.History
           goog.History.EventType))

(defn gravatar [data]
  (let [email-address (or (:gravatar-email data) "")
        size (or (:gravatar-size data) 100)]
    [:img.gravatar.img-circle {:src (str "http://www.gravatar.com/avatar/" (hashgen/md5 email-address) "?size=" size "&r=PG&d=mm")}]))

(defn set-hash! [loc]
  "Set the hash portion of the url in the address bar.
  e.g. (set-hash! '/dip') => http://localhost:3000/#/dip"
  (set! (.-hash js/window.location) loc))

(defn get-current-location []
  "Get the url in the address bar, including the hash portion."
  (subs (.-hash js/window.location) 0))

(defn api [db endpoint]
  (str (:api-root-url db) endpoint))

(defn isNaN [node]
  (and (= (.call js/toString node) (str "[object Number]"))
       (js/eval (str node " != +" node ))))

(defn parse-int [s]
  "Parse the string for an integer"
  (if (or (nil? s) (empty? s))
    0
    (try
      (if (isNaN (js/parseInt s)) 0 (js/parseInt s))
      (catch js/Error _ 0)
      (catch js/Object _ 0)
      )))

(defn input-value [component] (-> component .-target .-value))

;(defn input-element [{:keys [id name type placeholder on-blur on-change default-value value]}]
;  "An input element which updates its value on change"
;  ^{:key id} [:input
;              {:id            id
;               :name          name
;               :placeholder   placeholder
;               :class         "form-control"
;               :type          type
;               :default-value default-value
;               :on-blur       on-blur
;               :on-change     on-change
;               }])

