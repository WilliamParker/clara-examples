(ns clara.examples.dup-nodes
  (:refer-clojure :exclude [==])
  (:require [clara.rules.accumulators :as acc]
            [clara.rules :refer :all]
            [potemkin :as p]))


(p/defrecord+ BindingFact [])

(p/defrecord+ InsertionFact[b])

(defrule rule1
  [BindingFact]
  =>
  (insert! (map->InsertionFact {:a ^{:c 1} []})))

(defrule rule2
  [BindingFact]
  =>
  (insert! (map->InsertionFact {:a ^{:c 2} []})))

(defquery query-insertion
  "Query to get all InsertionFact instances"
  []
  [?i <- InsertionFact])

(defquery query-binding
  "Query to get all BindingFact instances"
  []
  [?i <- BindingFact])

(def fired-session (-> (mk-session 'clara.examples.dup-nodes)
                       (insert (map->BindingFact {}))
                       fire-rules))

