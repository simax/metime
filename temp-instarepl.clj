
(require '[metime.data.departments :as deps]
         '[metime.data.employees :as emps]
         '[clojure.set :refer :all]
         '[clojure.pprint :as pp])



;; ({:department, :manager :lastname, :firstname...})
;; ({:department, :manager :empoyees [{:emp_id :firstname ....}]})


(def t0 (deps/get-all-departments-with-employees))

(def t1 (clojure.set/rename (into #{} (deps/get-all-departments)) {:id :departments_id}))


(def d (group-by :departments_id (emps/get-all-employees)))
(def t2 (into #{} (map #(hash-map :departments_id %1 :employees %2) (keys d) (vals d))))


(clojure.pprint/pprint (first (clojure.set/join t1 t2)))


;; (def x #{{:dep-id 1 :department "Design"       ;:employees []
;;           }
;;          {:dep-id 2 :department "Development"  ;:employees []}
;;          }})

;; (def y #{{:dep-id 1 :employees [{:emp-id 1 :emp-name "Simon Lomax" }
;;                                 {:emp-id 2 :emp-name "Joe Bloggs" }]
;;           }
;;          {:dep-id 2 :employees [{:emp-id 3 :emp-name "Walter White"}]
;;          }})


;; (join x y)