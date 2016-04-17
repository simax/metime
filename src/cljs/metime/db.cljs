(ns metime.db)

(defonce default-db
         {
          :authentication-token           ""
          :authentication-failed-msg      ""
          :route-params                   [:home]
          :nav-bar                        nil
          :view                           :home
          :employee                       {:is-ready? false :validation-errors nil}
          :search-criteria                ""
          :department-employees           nil
          :filtered-department-employees  nil
          :department                     nil
          :departments                    nil
          :leave-type                     nil
          :leave-types                    nil
          :fetching-department-employees? false
          :department-drawer-open-id      nil
          :leave-type-drawer-open-id      nil})
