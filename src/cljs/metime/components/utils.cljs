(ns metime.components.utils
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [cljs-hash.md5 :as hashgen]
            [cljs-hash.goog :as gh])
  (:import goog.History
           goog.History.EventType))

(enable-console-print!)

(def history (History.))

(defn gravatar [data]
  (let [email-address (:gravatar-email data)
        size (or (:gravatar-size data) 100)]
    [:img.gravatar.img-circle
     (if (seq email-address)
       {:src (str "http://www.gravatar.com/avatar/" (hashgen/md5 email-address) "?s=" size "&r=PG&d=mm")})]))

