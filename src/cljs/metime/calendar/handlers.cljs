(ns metime.calendar.handlers
  (:require-macros
    [cljs.core.async.macros :refer [go]]
    [bouncer.validators :refer [defvalidator]])
  (:require [metime.formatting :as fmt]
            [clojure.string :as s]
            [cljs.core.async :refer [<! >! chan]]
            [metime.utils :as utils]
            [metime.routes :as routes]
            [cljs-http.client :as http]
            [cljs-time.core :refer [date-time local-date now days minus day-of-week]]
            [cljs-time.format :as f :refer [formatter parse unparse]]
            [re-frame.core :refer [register-handler
                                   path
                                   debug
                                   after
                                   enrich
                                   register-sub
                                   dispatch
                                   subscribe]]
            [metime.utils :as utils]
            [bouncer.core :as b]
            [bouncer.validators :as v]
            [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame-tracer.core :refer [tracer]]
            [metime.common.handlers]))

;(trace-forms
; {:tracer (tracer :color "green")}

(register-handler
  :fetch-my-bookings
  (fn hdlr-fetch-my-bookings [db [_]]
    (utils/call-api :GET (routes/api-endpoint-for :employee-bookings :id (:logged-in-user-id db)) db
                    {:success-handler-key :api-response->bookings-by-employee-id
                     :response-keys       [:body]})
    db))

(register-handler
  :api-response->bookings-by-employee-id
  (fn hdlr-:api-response->bookings-by-employee-id [db [_ employee-bookings]]
    (let [value (js->clj employee-bookings)]
      (assoc db :employee-bookings value))))


;)


