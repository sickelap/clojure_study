(ns blackjack.infrastructure.adapter.driving.tablerepository.in-memory
  (:require [blackjack.port.table-repository :refer :all]
            [blackjack.app.lockable :refer :all]
            [blackjack.infrastructure.adapter.driving.shared.locking :as lo]))

(def table-map (ref {}))
(def locks (ref {}))

(defrecord InMemoryTableRepository []
  TableRepository
  (clear! [this] (dosync 
                   (ref-set table-map {})))
  (get-table [this table-id] (get @table-map table-id))
  (get-tables [this] (vals @table-map))
  (save-table! [this table] 
    (dosync
      (alter table-map into {(table :id) table})))
  Lockable
  (acquire-lock! [this id]
    (lo/acquire-lock! locks id))
  (release-lock! [this id]
    (lo/release-lock! locks id)))