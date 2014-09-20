(defproject simon "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 ;[org.clojure/java.jdbc "0.3.0-beta1"]
                 [korma "0.3.1"]
                 [org.postgresql/postgresql "9.3-1100-jdbc4"]
                 [liberator "0.11.0"]
                 [ring "1.2.2"]
                 [cheshire "5.3.1"]]
  :ring {:handler api.core/app}
  :clj-sql-up {:database {:subprotocol "postgresql"
                          :subname "mytime"
                          :user "simonlomax"
                          :password "nomisxamol"}
               :deps [[org.postgresql/postgresql "9.3-1100-jdbc4"]]})
