(ns metime.db)

(defonce default-db
         {:api-root-url               "http://localhost:3000/api"
          :authentication-failed-msg  ""
          :employee                   {:is-ready? false :validation-errors nil}
          :view                       :login
          :authentication-token       ""
          :nav-bar                    nil
          :department-draw-open-id    0})
