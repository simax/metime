(ns metime.db)

(defonce default-db
  {:api-root-url "http://localhost:3030/api"
   :employee     {:is-ready? false}
   :view         :employees
   :nav-bar      :employees
   :nav-bars     [{:id :employees :text "Employees" :path "#/employees"}
                  {:id :file-manager :text "File Manager" :path "#/file-manager"}
                  {:id :calendar :text "Calendar" :path "#/calendar"}
                  {:id :tables :text "Tables" :path "#/tables"}
                  {:id :user :text "User" :path "#/user"}]})
