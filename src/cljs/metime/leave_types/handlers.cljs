(ns metime.leave-types.handlers
  (:require-macros
    [cljs.core.async.macros :refer [go]]
    [bouncer.validators :refer [defvalidator]])
  (:require [metime.formatting :as fmt]
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

(register-handler
  :close-leave-type-drawer
  (fn hdlr-close-leave-type-drawer [db [_]]
    (assoc db
      :leave-type nil
      :leave-type-draw-open-id nil)))

(register-handler
  :open-leave-type-drawer
  (fn hndlr-open-leave-type-drawer [db [_ leave-type-id]]
    (dispatch [:fetch-leave-type leave-type-id])
    (assoc db
      :leave-type-draw-open-id leave-type-id)))

(register-handler
  :ui-leave-type-drawer-status-toggle
  (fn hdlr-ui-leave-type-drawer-status-toggle [db [_ leave-type-id]]
    (dispatch [:close-new-leave-type-drawer])
    (if (= (:leave-type-draw-open-id db) leave-type-id)
      (dispatch [:close-leave-type-drawer])
      (dispatch [:open-leave-type-drawer leave-type-id]))
    db))

(register-handler
  :close-new-department-drawer
  (fn hdlr-close-new-department-drawer [db [_]]
    (assoc db :new-department-draw-open? false
              :department nil)))

(register-handler
  :ui-new-department-drawer-status-toggle
  (fn hdlr-ui-new-department-drawer-status-toggle [db [_]]
    (dispatch [:close-department-drawer])
    (if (:new-department-draw-open? db)
      (assoc db :new-department-draw-open? false)
      (do
        (dispatch [:new-department])
        (assoc db :new-department-draw-open? true)))))