(defproject metime "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

            :dependencies [
                           [org.clojure/clojure "1.7.0"]
                           [org.clojure/clojurescript "1.7.48"]

                           [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                           [figwheel "0.3.9"]
                           [figwheel-sidecar "0.3.9"]


                           [cljs-hash "0.0.2"]
                           [bouncer "0.3.3"]
                           [metis "0.3.3"]
                           [ring-cors "0.1.7"]
                           [clj-time "0.9.0"]
                           [com.andrewmcveigh/cljs-time "0.3.13"]
                           [digest "1.4.4"]


                           ;; Server
                           [ring "1.4.0-RC2"]
                           [compojure "1.3.4"]
                           [cljs-http "0.1.35"]
                           [yesql "0.5.0-rc3"]
                           [org.xerial/sqlite-jdbc "3.7.2"]
                           [liberator "0.13"]
                           [cheshire "5.5.0"]
                           [prone "0.8.2"]
                           [org.clojure/data.json "0.2.5"]

                           ;; UI
                           [reagent "0.5.0"]
                           [re-frame "0.4.1"]
                           [re-com "0.6.1"]
                           [secretary "1.2.3"]

                           ;; Dev
                           [org.clojure/tools.trace "0.7.8"]
                           [enlive "1.1.5"]
                           [selmer "0.8.0"]
                           [environ "1.0.0"]
                           [expectations "2.1.2"]
                           [binaryage/devtools "0.3.0"]]


  :plugins [
            [lein-figwheel "0.3.9"]
            [lein-cljsbuild "1.0.5"]
            [lein-environ "1.0.0"]
            [lein-ring "0.9.6"]
            [lein-asset-minifier "0.2.2"]]

  :source-paths ["src/clj" "src/cljs"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  ;; Used for data migrations
  :clj-sql-up {:database {:classname "org.sqlite.JDBC"
                          :subprotocol "sqlite"
                          :subname "data/metime.sqlite"}
               :deps [[org.xerial/sqlite-jdbc "3.7.2"]]}


  :ring {:handler metime.core/app :auto-reload? true}

  :min-lein-version "2.5.0"

  :uberjar-name "metime.jar"
  :main metime.server

  :cljsbuild {
              :builds [{:id           "dev"
                        :source-paths ["src/cljs"]

                        :figwheel     {:on-jsload "metime.client/mount-root"}

                        :compiler     {:main                 metime.client
                                       :asset-path           "js/compiled/out"
                                       :output-to            "resources/public/js/compiled/app.js"
                                       :output-dir           "resources/public/js/compiled/out"
                                       :source-map-timestamp true}}
                       {:id           "min"
                        :source-paths ["src/cljs"]
                        :compiler     {:output-to     "resources/public/js/compiled/app.js"
                                       :main          metime.client
                                       :optimizations :advanced
                                       :pretty-print  false}}]}
  :figwheel {
             ;:http-server-root "public" note: This is the default
             ;:nrepl-port       7888
             ;:port             3449
             :css-dirs     ["resources/public/assets/css"]
             :ring-handler metime.core/app
             })
