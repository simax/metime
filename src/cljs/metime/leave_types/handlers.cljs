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

(def leave-type-validation-rules
  [:leave-type [[v/required :message "leave type name is required"]]])

(defn validate-leave-type [db]
  (let [leave-type (:leave-type db)
        result [(first (apply b/validate leave-type leave-type-validation-rules))]
        errors (first result)]
    (assoc-in db [:leave-type :validation-errors] errors)))

(register-handler
  :input-change-leave-type-name
  (enrich validate-leave-type)
  (fn hdlr-input-change [db [_ leave-type-name]]
    (assoc-in db [:leave-type :leave-type] leave-type-name)))

(register-handler
  :new-leave-type
  (fn hdlr-new-leave-type [db [_]]
    (assoc db :leave-type {:leave-type-id 0 :leave-type "" })))

(register-handler
  :edit-leave-type
  (fn hdlr-edit-leave-type [db [_ leave-type-id]]
    (dispatch [:fetch-leave-type leave-type-id])
    db))

(register-handler
  :ui-new-leave-type-drawer-status-toggle
  (fn hdlr-ui-new-leave-type-drawer-status-toggle [db [_]]
    (dispatch [:close-leave-type-drawer])
    (if (:new-leave-type-drawer-open? db)
      (assoc db :new-leave-type-drawer-open? false)
      (do
        (dispatch [:new-leave-type])
        (assoc db :new-leave-type-drawer-open? true)))))

(register-handler
  :ui-new-leave-type-drawer-status-toggle
  (fn hdlr-ui-new-leave-type-drawer-status-toggle [db [_]]
    (dispatch [:close-leave-type-drawer])
    (if (:new-leave-type-drawer-open? db)
      (assoc db :new-leave-type-drawer-open? false)
      (do
        (dispatch [:new-leave-type])
        (assoc db :new-leave-type-drawer-open? true)))))




