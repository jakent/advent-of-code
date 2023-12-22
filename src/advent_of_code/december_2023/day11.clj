(ns advent-of-code.december-2023.day11
  (:require [advent-of-code.tools :as tools]))

(defn calculate-distance [start goal]
  (loop [current-position start
         i                0
         steps            0]
    (if (= current-position goal)
      steps
      (let [s      (get current-position i)
            d      (- (get goal i) s)
            next-i (if (zero? i) 1 0)]
        (cond
          (zero? d) (recur current-position next-i steps)
          (pos? d) (recur (update current-position i inc) next-i (inc steps))
          (neg? d) (recur (update current-position i dec) next-i (inc steps)))))))

(defn find-combinations [galaxies]
  (set (for [a galaxies
             b galaxies
             :when (not= a b)]
         (into #{} [a b]))))

(defn find-galaxies [locations]
  (->> locations
       (filter (fn [[_ v]] (= v \#)))
       (map first)))

(defn calculate-shortest-path-sum [file-lines]
  (->> file-lines
       tools/file-lines->location-map
       find-galaxies
       find-combinations
       (map (partial apply calculate-distance))
       (apply +)))

(defn expand-universe [file-lines]
  (let [expand (partial mapcat #(if (every? #{\.} %) [% %] [%]))]
    (->> file-lines
         expand
         tools/transpose
         expand)))

(defn problem-1
  "10231178"
  []
  (->> (tools/read-file-lines "resources/december-2023/day11")
       (map (partial map identity))
       expand-universe
       calculate-shortest-path-sum))
