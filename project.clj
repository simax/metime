(defproject metime "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj" "src/cljs"]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2511" :scope "provided"]
                 [com.cemerick/piggieback "0.1.3"]
                 [weasel "0.4.0-SNAPSHOT"]
                 [leiningen "2.5.0"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [cljs-hash "0.0.2"]
                 [metis "0.3.3"]
                 [ring-cors "0.1.6"]

                 ;; Server
                 [ring "1.3.1"]
                 [compojure "1.2.0"]
                 [cljs-http "0.1.20"]
                 [korma "0.4.0"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 [liberator "0.11.0"]
                 [cheshire "5.3.1"]
                 [prone "0.6.0"]
                 [org.clojure/data.json "0.2.5"]

                 ;; UI
                 [om "0.8.0-alpha2"]
                 [sablono "0.2.22"]
                 [secretary "1.2.1"]
                 [prismatic/om-tools "0.3.6"]

                 ;; Dev
                 [enlive "1.1.5"]
                 [figwheel "0.1.4-SNAPSHOT"]
                 [environ "1.0.0"]

                 ]

  :ring {:handler metime.core/app :auto-reload? true}

  ;; Used for data migrations
  :clj-sql-up {:database {:classname "org.sqlite.JDBC"
                  :subprotocol "sqlite"
                  :subname "data/metime.sqlite"}

               :deps [[org.xerial/sqlite-jdbc "3.7.2"]]}

  :plugins [[lein-cljsbuild "1.0.3"]
            [lein-environ "1.0.0"]]

  :min-lein-version "2.5.0"

  :uberjar-name "metime.jar"

  :cljsbuild {:builds {:app {:source-paths ["src/cljs"]
                             :compiler {:output-to     "resources/public/js/app.js"
                                        :output-dir    "resources/public/js/out"
                                        :source-map    "resources/public/js/out.js.map"
                                        :preamble      ["react/react.min.js"]
                                        :externs       ["react/externs/react.js"]
                                        :optimizations :none
                                        :pretty-print  true}}}}

  :profiles {:dev {:repl-options {:init-ns metime.server
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

                   :plugins [[lein-figwheel "0.1.4-SNAPSHOT"]]

                   :figwheel {:http-server-root "public"
                              :port 3449
                              :css-dirs ["resources/public/css"]}

                   :env {:is-dev true}
                   :cljsbuild {:builds {:app {:source-paths ["env/dev/cljs"]}}}}

             :uberjar {:hooks [leiningen.cljsbuild]
                       :env {:production true}
                       :omit-source true
                       :aot :all
                       :cljsbuild {:builds {:app
                                            {:source-paths ["env/prod/cljs"]
                                             :compiler
                                             {:optimizations :advanced
                                              :pretty-print false}}}}}})
