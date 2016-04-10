(ns metime.common.handlers
  (:require-macros
    [cljs.core.async.macros :refer [go]]
    [bouncer.validators :refer [defvalidator]])
  (:require [metime.utils :as utils]
            [cljs.core.async :refer [<! >! chan]]
            [metime.routes :as routes]
            [cljs-http.client :as http]
            [re-frame.core :refer [register-handler
                                   path
                                   debug
                                   after
                                   enrich
                                   register-sub
                                   dispatch
                                   subscribe]]))


(defn set-auth-cookie! [token]
  (let [expiry (* 60 60 24 30)]                             ; 30 days (secs mins hours days)
    (utils/set-cookie! "auth" token {:max-age expiry :path "/"})))

(register-handler
  :authenticated
  (fn hdlr-authenticated [db [_ token]]
    (routes/set-route-token! [:home])
    (assoc db :authentication-token token :authentication-failed-msg "" :nav-bar nil :view :home)))

(register-handler
  :authentication-failed
  (fn hdlr-authentication-failed [db [_]]
    (assoc db :authentication-token ""
              :authentication-failed-msg "Invalid email/password"
              :view :login)))

(defn response-ok? [response]
  (= 200 (:status response)))

(defn build-url [db]
  (str (routes/api-endpoint-for :authtoken) "?email=" (get-in db [:employee :email]) "&" "password=" (get-in db [:employee :password])))

(register-handler
  :log-in
  (fn hdlr-log-in [db [_]]
    (go
      (let [url (build-url db)
            response (<! (http/get url))
            token (if (response-ok? response)
                    ((js->clj (response :body)) :token)
                    "")]
        (set-auth-cookie! token)
        (if (empty? token)
          (dispatch [:authentication-failed])
          (dispatch [:authenticated token]))))
    db))

(register-handler
  :log-out
  (fn hdlr-log-out [db [_]]
    (utils/remove-cookie! "auth")
    (assoc db
      :view :login
      :authentication-failed-msg "")))

