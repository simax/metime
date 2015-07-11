(ns metime.db
  (:require [metime.routes :as r]
            [secretary.core :as secretary]))

(secretary/set-config! :prefix "#")

(defonce default-db
  {:api-root-url "http://localhost:3030/api"
   :employee     {:is-ready? false}
   :view         :employees
   :nav-bar      :employees})
