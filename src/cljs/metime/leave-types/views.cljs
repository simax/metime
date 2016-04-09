(ns metime.leave-types.views
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [metime.formatting :as fmt]
            [metime.common.views :as common-components]
            [cljs.core.async :refer [put! take! <! >! chan timeout]]
            [metime.navigation.subs]
            [metime.employees.subs]
            [metime.employees.handlers]
            [metime.routes :as routes]
            [metime.utils :as utils]
            [re-com.core :refer [h-box v-box box gap scroller
                                 title single-dropdown label
                                 input-text input-textarea datepicker datepicker-dropdown button
                                 popover-anchor-wrapper popover-content-wrapper
                                 popover-tooltip md-icon-button md-circle-icon-button row-button]
             :refer-macros [handler-fn]]
            [re-com.datepicker :refer [iso8601->date datepicker-args-desc]]
            [cljs-time.core :refer [within? before? after? date-time now days minus day-of-week]]
            [cljs-time.format :refer [formatter parse unparse]]
            [cljs-time.coerce]
            [goog.date]
            [re-frame.core :refer [dispatch subscribe]]
            [reagent.core :as reagent]
            [reagent.ratom :refer [make-reaction]]
            [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame-tracer.core :refer [tracer]]
            [cljs.pprint :refer [pprint]]))
