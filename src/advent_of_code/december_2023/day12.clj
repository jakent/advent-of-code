(ns advent-of-code.december-2023.day12
  (:require [advent-of-code.tools :as tools]
            [clojure.string :as str]))

(defn combinations [xs]
  (reduce (fn [acc i]
            (if (#{\?} (nth xs i))
              (mapcat #(vector (conj % \.)
                               (conj % \#))
                      acc)
              (map #(conj % (nth xs i)) acc)))
          [[]]
          (range (count xs))))

(defn valid? [xs k]
  (->> (reduce (fn [groups n]
                 (let [[match & remaining] (drop-while #(or (= \. (first %)))
                                                       groups)]
                   (if (not= n (count match))
                     (reduced [[\#]])
                     remaining)))
               (partition-by identity xs)
               k)
       (every? #(every? (partial = \.) %))))

(defn problem-1
  "8270"
  []
  (->> (tools/read-file-lines "resources/december-2023/day12")
       (map (comp (fn [[combos k]]
                    (count (filter #(valid? % k) combos)))
                  #(update % 0 combinations)
                  #(update % 1 tools/read-vector-string)
                  #(str/split % #"\s+")))
       (apply +)))
