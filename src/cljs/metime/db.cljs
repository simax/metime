(ns metime.db)

(defonce default-db
  {:api-root-url "http://localhost:3030/api"
   :view         :employees
   :employee     {:is-ready? false}
   :nav-bar      [{:id :employees :text "Employees" :path "#/employees" :active true}
                  {:id :file-manager :text "File Manager" :path "#/file-manager"}
                  {:id :calendar :text "Calendar" :path "#/calendar"}
                  {:id :tables :text "Tables" :path "#/tables"}
                  {:id :login :text "Login" :path "#/login"}
                  {:id :user :text "User" :path "#/user"}]})