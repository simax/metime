(ns metime.data.database
  (:require
    [hugsql.core]
    [hugsql.adapter]
    [camel-snake-kebab.core :as csk]
    [camel-snake-kebab.extras :as csk-extras]))

(def db-spec
  {:classname "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname "data/metime.sqlite"})

(defn results-one-snake->kebab
  [this result options]
  (->> (hugsql.adapter/result-one this result options)
       (csk-extras/transform-keys csk/->kebab-case-keyword)))

(defn results-many-snake->kebab
  [this result options]
  (->> (hugsql.adapter/result-many this result options)
       (csk-extras/transform-keys csk/->kebab-case-keyword)))

(defmethod hugsql.core/hugsql-result-fn :1 [_] 'metime.data.database/results-one-snake->kebab)
(defmethod hugsql.core/hugsql-result-fn :one [_] 'metime.data.database/results-one-snake->kebab)
(defmethod hugsql.core/hugsql-result-fn :* [_] 'metime.data.database/results-many-snake->kebab)
(defmethod hugsql.core/hugsql-result-fn :many [_] 'metime.data.database/results-many-snake->kebab)

