(ns metime.db
  (:require [secretary.core :as secretary]))

(secretary/set-config! :prefix "#")

(defonce default-db
         {:api-root-url               "http://localhost:3030/api"
          :authentication-failed-msg  ""
          :employee                   {:is-ready? false :validation-errors nil}
          :view                       :login
          :authentication-token       ""
          :nav-bar                    nil
          :department-draw-open-id    0})
