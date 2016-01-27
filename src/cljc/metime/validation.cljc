(ns metime.validation
  (:require #?(:clj
                [liberator.core :refer [resource defresource]])
            #?(:cljs
               :require-macros
               [cljs.core.async.macros :refer [go]])
                [metime.data.employees :as data-emps]
                [cljs.core.async :refer [<! >! chan]]))


