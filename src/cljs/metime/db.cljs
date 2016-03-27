(ns metime.db)

(defonce default-db
         {:authentication-failed-msg      ""
          :employee                       {:is-ready? false :validation-errors nil}
          :department                     {:id 0 :department "" :manager-id nil :validation-errors nil}
          :department-employees           nil
          :departments                    nil
          :fetching-department-employees? false
          :view                           :home
          :route-params                   [:home]
          :authentication-token           ""
          :nav-bar                        nil
          :department-draw-open-id        nil})
