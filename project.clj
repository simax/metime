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
                           ;;[figwheel "0.1.4-SNAPSHOT"]
                           [selmer "0.8.0"]
                           [environ "1.0.0"]
                           [expectations "2.0.9"]

                           ]

            :plugins [[lein-cljsbuild "1.0.4"]
                      [lein-environ "1.0.0"]
                      [lein-ring "0.9.1"]
                      [lein-figwheel "0.3.1"]
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
                        :builds {:app {:source-paths ["src/cljs"]

                                       :compiler     {:output-to     "resources/public/js/app.js"
                                                      :output-dir    "resources/public/js/out"
                                                      :asset-path    "js/out"
                                                      :source-map    "resources/public/js/out.js.map"
                                                      :preamble      ["react/react.min.js"]
                                                      :externs       ["react/externs/react.js"]
                                                      :optimizations :none
                                                      :pretty-print  true}}}}

            :profiles {:dev     {:repl-options {:init-ns          metime.server
                                                :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

                                 :dependencies [[ring-mock "0.1.5"]
                                                [ring/ring-devel "1.3.2"]
                                                [leiningen "2.5.1"]
                                                [figwheel "0.3.3"]
                                                [weasel "0.6.0-SNAPSHOT"]
                                                [pjstadig/humane-test-output "0.6.0"]]

                                 :source-paths ["env/dev/clj"]
                                 :plugins      [[lein-figwheel "0.3.1"]]

                                 :figwheel     {:http-server-root "public"
                                                :nrepl-port       7888
                                                :port             3449
                                                :css-dirs         ["resources/public/css"]
                                                :ring-handler     metime.core/app}

                                 :env          {:dev? true}

                                 :cljsbuild    {:builds {:app {:source-paths ["env/dev/cljs"]
                                                               :compiler     {:main "metime.dev" :source-map true}}}}}


                       :uberjar {:hooks       [leiningen.cljsbuild]
                                 :env         {:production true}
                                 :omit-source true
                                 :aot         :all
                                 :cljsbuild   {:jar    true
                                               :builds {:app
                                                        {:source-paths ["env/prod/cljs"]
                                                         :compiler
                                                                       {:optimizations :advanced
                                                                        :pretty-print  false}}}}}}
            )
