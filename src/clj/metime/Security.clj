(ns metime.security
  (:require [buddy.sign.jws :as jws]
            [clj-time.core :as time]
            [buddy.hashers :as hashers]
            [metime.data.employees :as emps]))

(defn auth-user [credentials]
  (let [user (emps/get-employee-by-email (:email credentials))
        unauthed [false {:message "Invalid email or password"}]]
    (if user
      (if (hashers/check (:password credentials) (:password user))
        [true (dissoc user :password)]
        unauthed)
      unauthed)))

(defn create-auth-token-service [credentials]
  "If we wanted to use public/private key we could pass auth-conf and use it
   See buddy documentation for that."
  (let [[ok? res] (auth-user credentials)
        mins-to-expiry 2
        ; [expiry (* 60 60 24 30)] ; 30 days (secs mins hours days)
        exp (-> (time/plus (time/now) (time/seconds (* 60 mins-to-expiry))))
        claims {:user res :exp exp}]
    (if ok?
      ;; Should probably use a UUID here instead of "secret" (like timetastic?)
      [true {:token (jws/sign claims "secret" {:alg :hs512})}]
      [false res])))

(defn create-auth-token [credentials]
  (let [[ok? res] (create-auth-token-service credentials)]
    (if ok? (:token res) "")))

