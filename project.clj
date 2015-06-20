(defproject metime "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

            :dependencies [[org.clojure/clojure "1.6.0"]
                           [org.clojure/clojurescript "0.0-3211"]
                           [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                           [cljs-hash "0.0.2"]
                           [com.novemberain/validateur "2.4.2"]
                           [metis "0.3.3"]
                           [ring-cors "0.1.6"]
                           [clj-time "0.8.0"]
                           [digest "1.4.4"]

                           ;; Server
                           [ring "1.3.2"]
                           [compojure "1.2.0"]
                           [cljs-http "0.1.20"]
                           [yesql "0.5.0-beta2"]
                           [org.xerial/sqlite-jdbc "3.7.2"]
                           [liberator "0.11.0"]
                           [cheshire "5.3.1"]
                           [prone "0.8.0"]
                           [org.clojure/data.json "0.2.5"]

                           ;; UI
                           [reagent "0.5.0-alpha3"]
                           [re-frame "0.2.0"]
                           [re-com "0.5.4"]
                           [secretary "1.2.3"]

                           ;; Dev
                           [org.clojure/tools.trace "0.7.8"]
                           [enlive "1.1.5"]
                           [selmer "0.8.0"]
                           [environ "1.0.0"]
                           [expectations "2.0.9"]

                           ]

            :plugins [[lein-cljsbuild "1.0.4"]
                      [lein-environ "1.0.0"]
                      [lein-ring "0.9.1"]
                      [lein-figwheel "0.3.3"]
                      [lein-asset-minifier "0.2.2"]]

  :source-paths ["src/clj" "src/cljs"]

  ;; Used for data migrations
  :clj-sql-up {:database {:classname "org.sqlite.JDBC"
                          :subprotocol "sqlite"
                          :subname "data/metime.sqlite"}
               :deps [[org.xerial/sqlite-jdbc "3.7.2"]]}


            :ring {:handler metime.core/app :auto-reload? true}

  :min-lein-version "2.5.0"

  :uberjar-name "metime.jar"
            :main metime.server
            :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

            :cljsbuild {
                        :builds [{:id           "dev"
                                  :source-paths ["src"]

                                  :figwheel     {:on-jsload "metime.core/on-js-load"}

                                  :compiler     {:main                 metime.core
                                                 :asset-path           "js/compiled/out"
                                                 :output-to            "resources/public/js/compiled/app.js"
                                                 :output-dir           "resources/public/js/compiled/out"
                                                 :source-map-timestamp true}}
                                 {:id           "min"
                                  :source-paths ["src"]
                                  :compiler     {:output-to     "resources/public/js/compiled/app.js"
                                                 :main          metime.core
                                                 :optimizations :advanced
                                                 :pretty-print  false}}]}
            :figwheel {
                       ;:http-server-root "public"
                       ;:nrepl-port       7888
                       ;:port             3449
                       :css-dirs     ["resources/public/assets/css"]
                       :ring-handler metime.core/app

                       ;; To be able to open files in your editor from the heads up display
                       ;; you will need to put a script on your path.
                       ;; that script will have to take a file path and a line number
                       ;; ie. in  ~/bin/myfile-opener
                       ;; #! /bin/sh
                       ;; emacsclient -n +$2 $1
                       ;;
                       ;; :open-file-command "myfile-opener"

                       ;; if you want to disable the REPL
                       ;; :repl false

                       ;; to configure a different figwheel logfile path
                       ;; :server-logfile "tmp/logs/figwheel-logfile.log"
                       })
