(ns metime.db)

(defonce default-db
         {:authentication-failed-msg      ""
          :employee                       {:is-ready? false :validation-errors nil}
          :department-employees           nil
          :department                     nil
          :departments                    nil
          :leave-type                     nil
          :leave-types                    nil
          :fetching-department-employees? false
          :view                           :home
          :route-params                   [:home]
          :authentication-token           ""
          :nav-bar                        nil
          :department-drawer-open-id      nil
          :leave-type-drawer-open-id      nil})
