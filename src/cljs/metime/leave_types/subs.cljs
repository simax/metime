(ns metime.leave-types.subs
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [reagent.ratom :refer [make-reaction]]
            [re-frame.core :refer [register-sub]]
            [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame-tracer.core :refer [tracer]]))

(register-sub
  :leave-types
  (fn [db _]
    (make-reaction (fn sub-leave-types [] (:leave-types @db)))))

