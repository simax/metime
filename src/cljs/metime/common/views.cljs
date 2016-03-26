(ns metime.common.views
  (:require [re-com.core :refer [throbber h-box v-box box gap
                                 title single-dropdown label
                                 input-text input-textarea datepicker datepicker-dropdown button
                                 popover-anchor-wrapper popover-content-wrapper
                                 popover-tooltip md-icon-button md-circle-icon-button row-button]]))


(defn loader-component [height color]
  [box
   :height (or height "650px")
   :align :center
   :child [:div (throbber :size :large :color (or color "lime"))]])

