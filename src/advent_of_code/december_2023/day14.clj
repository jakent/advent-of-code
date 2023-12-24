(ns advent-of-code.december-2023.day14
  (:require [advent-of-code.tools :as tools]))

(defn tilt [comp row]
  (->> row
       (partition-by (partial = \#))
       (mapcat (fn [g]
                 (if (= \# (first g))
                   g
                   (sort-by int comp g))))))

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
       (map (partial tilt >))
       tools/transpose
       score))

(defn cycle-dish [dish]
  (->> dish
       tools/transpose
       (map (partial tilt >))
       tools/transpose
       (map (partial tilt >))
       tools/transpose
       (map (partial tilt <))
       tools/transpose
       (map (partial tilt <))))

(-> (reduce (fn [dish _] (cycle-dish dish))
            (tools/read-file-lines "resources/december-2023/day14")
            (range 1000000000))
    score)
