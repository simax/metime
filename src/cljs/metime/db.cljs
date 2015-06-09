(ns metime.db)

(def default-db
  {:api-root-url "http://localhost:3030/api"
   :employee     {:is-ready? false}
   :nav-bar      [{:id :employees :text "Employees" :active true}
                  {:id :file-manager :text "File Manager"}
                  {:id :calendar :text "Calendar"}
                  {:id :tables :text "Tables"}
                  {:id :login :text "Login"}
                  {:id :user :text "User"}]})