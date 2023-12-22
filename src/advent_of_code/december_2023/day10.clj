(ns advent-of-code.december-2023.day10
  (:require [advent-of-code.tools :as tools]))

(def start-char \S)
(def pipes->step
  {\| {[1 0]  [1 0]
       [-1 0] [-1 0]}
   \- {[0 1]  [0 1]
       [0 -1] [0 -1]}
   \L {[1 0]  [0 1]
       [0 -1] [-1 0]}
   \J {[1 0] [0 -1]
       [0 1] [-1 0]}
   \7 {[0 1]  [1 0]
       [-1 0] [0 -1]}
   \F {[-1 0] [0 1]
       [0 -1] [1 0]}})

(defn calculate-path [locations]
  (fn [current-location step path]
    (let [char (locations current-location)]
      (if (= start-char char)
        (conj path current-location)
        (let [to (get-in pipes->step [char step])]
          (recur (mapv + current-location to) to (conj path current-location)))))))

(defn find-first-step [locations]
  (let [s-location (-> (filter (fn [[_ v]] (= v start-char)) locations)
                       first
                       first)]
    (reduce (fn [_ loc]
              (let [found (-> (locations loc)
                              pipes->step
                              keys)
                    step  (mapv - s-location loc)]
                (when ((set found) step)
                  (reduced [loc (mapv - loc s-location)]))))
            nil
            (tools/neighbors s-location))))

(defn problem-1
  "6927"
  []
  (let [locations (->> (tools/read-file-lines "resources/december-2023/day10")
                       tools/file-lines->location-map)
        [start step] (find-first-step locations)
        path      ((calculate-path locations) start step #{})]
    (/ (count path) 2)))
