(ns metime.common.views
  (:require [re-com.core :refer [throbber h-box v-box box gap
                                 title single-dropdown label
                                 input-text input-textarea datepicker datepicker-dropdown button
                                 popover-anchor-wrapper popover-content-wrapper
                                 popover-tooltip md-icon-button md-circle-icon-button row-button]]))


(defn loader-component [height]
  [box
   :height (or height "650px")
   :size "auto"
   :align :center
   :justify :center
   :child [:div (throbber :size :large :color "lime")]])

