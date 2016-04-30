(ns metime.db)

(defonce default-db
         {
          :logged-in-user-id              37
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
          :employee-bookings              nil
          :fetching-department-employees? false
          :department-drawer-open-id      nil
          :leave-type-drawer-open-id      nil
          :booking-drawer-open-id         nil})
