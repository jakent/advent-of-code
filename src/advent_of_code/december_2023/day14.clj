(ns advent-of-code.december-2023.day14
  (:require [advent-of-code.tools :as tools]))

(defn tilt [row]
  (->> row
       (partition-by (partial = \#))
       (mapcat (fn [g]
                 (if (= \# (first g))
                   g
                   (sort-by int > g))))))

(defn score [dish]
  (-> (reduce (fn [[points total] row]
                [(dec points)
                 (->> (count (filter (partial = \O) row))
                      (* points)
                      (+ total))])
              [(count dish) 0]
              dish)
      second))

(defn problem-1
  "110090"
  []
  (->> (tools/read-file-lines "resources/december-2023/day14")
       tools/transpose
       (map tilt)
       tools/transpose
       score))
