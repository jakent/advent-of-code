(ns advent-of-code.december-2023.day6
  (:require [advent-of-code.tools :as tools]
            [clojure.string :as str]))

(defn find-number-of-winning-durations [[race-length record-distance]]
  (->> (range 1 (inc race-length))
       (filter (fn [hold-duration]
                 (> (-> (- race-length hold-duration)
                        (* hold-duration))
                    record-distance)))
       count))

(defn problem-1 []
  (->> (tools/read-file-lines "resources/december-2023/day6")
       (map #(tools/read-vector-string (second (str/split % #":\s+"))))
       (apply interleave)
       (partition 2)
       (map find-number-of-winning-durations)
       (apply *)))
