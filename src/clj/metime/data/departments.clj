(ns metime.data.departments
  (:require [hugsql.core :as hugsql]))

(hugsql/def-db-fns "metime/data/sql/metime.sql")
(hugsql/def-sqlvec-fns "metime/data/sql/metime.sql")


