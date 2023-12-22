(ns advent-of-code.december-2023.day9
  (:require [advent-of-code.tools :as tools]))

(defn calculate-differences [[f & rest]]
  (-> (reduce (fn [[a acc] b]
                [b (conj acc (- b a))])
              [f []]
              rest)
      second))

(defn find-zero-differences [[latest :as xss]]
  (if (every? zero? latest)
    xss
    (recur (conj xss (calculate-differences latest)))))

(defn extrapolate [xss]
  (-> (reduce (fn [[l v] xs]
                (let [last (+ l (last xs))]
                  [last (conj v (conj xs last))]))
              [0 []]
              xss)
      second))

(defn problem-1 []
  (->> (tools/read-file-lines "resources/december-2023/day9")
       (map (comp last
                  last
                  extrapolate
                  find-zero-differences
                  list
                  tools/read-vector-string))
       (apply +)))
