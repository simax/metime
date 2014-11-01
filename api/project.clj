(defproject simon "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [korma "0.3.1"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 [liberator "0.11.0"]
                 [ring "1.2.2"]
                 [cheshire "5.3.1"]
                 [prone "0.6.0"]]
  :ring {:handler api.core/app :auto-reload? true}
  :clj-sql-up {:database {:classname "org.sqlite.JDBC"
                    :subprotocol "sqlite"
                    :subname "/users/simonlomax/documents/development/clojure projects/metime/api/src/api/data/metime.sqlite"}
               :deps [[org.xerial/sqlite-jdbc "3.7.2"]]})
