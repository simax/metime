(ns metime.components.utils
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [cljs-hash.md5 :as hashgen]
            [cljs-hash.goog :as gh])
  (:import goog.History
           goog.History.EventType))

(enable-console-print!)

(defn gravatar [data]
  (let [email-address (:gravatar-email data)
        size (or (:gravatar-size data) 100)]
    [:img.gravatar.img-circle
     (if (seq email-address)
       {:src (str "http://www.gravatar.com/avatar/" (hashgen/md5 email-address) "?s=" size "&r=PG&d=mm")})]))

(defn set-hash! [loc]
  "Set the hash portion of the url in the address bar.
  e.g. (set-hash! '/dip') => http://localhost:3000/#/dip"
  (set! (.-hash js/window.location) loc))

(defn get-current-location []
  "Get the url in the address bar, including the hash portion."
  (subs (.-hash js/window.location) 0))
