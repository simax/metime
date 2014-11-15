(defproject metime-server "0.1.0-SNAPSHOT"
  :description "MeTime server-simply serves index.html"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring/ring-core "1.2.2"]
                 [ring/ring-json "0.2.0"]
                 [compojure "1.1.8"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2342"]
                 [sablono "0.2.22"]
                 [om "0.8.0-alpha2"]
                 [racehub/om-bootstrap "0.3.1"]
                 [org.clojure/core.async "0.1.338.0-5c5012-alpha"]
                 [cljs-http "0.1.2"]
                 [lein-cljsbuild "1.0.3"]
                 [com.facebook/react "0.11.2"]]

  :source-paths ["src/clj"]
  :plugins [
            [lein-ring "0.8.11"]
            [lein-pdo "0.1.1"]]

  :aliases {"up" ["pdo" "cljsbuild" "auto" "dev," "ring" "server-headless"]}

  :ring {:handler metime-server.handler/app}

  :cljsbuild {:builds [{:id "dev"
                      :source-paths ["src/cljs"]
                      :compiler {:output-to "resources/public/js/dashboard.js"
                                 :output-dir "resources/public/js/out"
                                 :optimizations :none
                                 :source-map true}}]})
