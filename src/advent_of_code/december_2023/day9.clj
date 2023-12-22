(ns advent-of-code.december-2023.day9
  (:require [advent-of-code.tools :as tools]))

(defn calculate-differences [[f & rest]]
  (-> (reduce (fn [[a acc] b]
                ;[b (conj acc (- b a))]) -- problem 1
                [b (conj acc (- a b))])
              [f []]
              rest)
      second))

(defn find-zero-differences [[latest :as xss]]
  (if (every? zero? latest)
    xss
    (recur (conj xss (calculate-differences latest)))))

(defn extrapolate [xss]
  (-> (reduce (fn [[l v] xs]
                ;(let [last (+ l (last xs))]
                ;  [last (conj v (conj xs last))])) -- problem 1
                (let [last (+ l (first xs))]
                  [last (conj v (cons last xs))]))
              [0 []]
              xss)
      second))

(defn problem-1
  "1930746032"
  []
  (->> (tools/read-file-lines "resources/december-2023/day9")
       (map (comp last
                  last
                  extrapolate
                  find-zero-differences
                  list
                  tools/read-vector-string))
       (apply +)))

(defn problem-2
  "1154"
  []
  (->> (tools/read-file-lines "resources/december-2023/day9")
       (map (comp first
                  last
                  extrapolate
                  find-zero-differences
                  list
                  tools/read-vector-string))
       (apply +)))
