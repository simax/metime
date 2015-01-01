(ns metime.components.utils
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [sablono.core :as html :refer-macros [html]]
            [cljs-hash.md5 :as hashgen]
            [cljs-hash.goog :as gh])
  (:import goog.History
           goog.History.EventType))

(enable-console-print!)

(declare app-state)

(def history (History.))


(defcomponent gravatar [data owner]
  (display-name [_]
                "gravatar")

  (render [_]
          (html
           (let [email-address (:gravatar-email data)
                 size (or (:gravatar-size data) 100)]
           [:img.gravatar.img-circle
            {:src (str "http://www.gravatar.com/avatar/" (hashgen/md5 email-address) "?s=" size "&r=PG&d=mm")}]))))
