(ns metime.db)

(defonce default-db
         {:authentication-failed-msg  ""
          :employee                   {:is-ready? false :validation-errors nil}
          :department                 {:id 0 :department "" :manager-id 0 :validation-errors nil}
          :departments-and-employees  nil
          :departments                nil
          :view                       :home
          :route-params               [:home]
          :authentication-token       ""
          :nav-bar                    nil
          :department-draw-open-id    0})
