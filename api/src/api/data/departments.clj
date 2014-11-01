(ns api.data.departments
  (:require [cheshire.core :as json]
            [korma.db :refer :all]
            [korma.core :refer :all]
            [api.data.database :refer :all]
            [clojure.walk :as walk]))

(defn get-all []
  (select departments (with employees)))
