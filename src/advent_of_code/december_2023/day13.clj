(ns advent-of-code.december-2023.day13
  (:require [advent-of-code.tools :as tools]
            [clojure.string :as str]))

(defn subseq? [a b]
  (->> (partition (count a) 1 b)
       (some #{a})
       some?))

(defn pair-down [pattern reflect]
  (take (- (count pattern) (count reflect))
        (reverse reflect)))

(defn score-rows [multiplier pattern]
  (-> (reduce (fn [[i past] part]
                (let [value (+ (count part) i)]
                  (if (odd? (count part))
                    [value (concat past part)]
                    (let [half    (/ (count part) 2)
                          reflect (concat past (first (partition half part)))
                          smaller (pair-down pattern reflect)]
                      (if (or (subseq? smaller pattern)
                              (= (count (concat past part)) (count pattern)))
                        (reduced (* (inc i) multiplier))
                        [value (concat past part)])))))
              [0 []]
              (partition-by identity pattern))
      tools/parse-int))

(defn problem-1
  "35521"
  []
  (->> (slurp "resources/december-2023/day13")
     (str/split-lines)
     (partition-by empty?)
     (remove #(empty? (first %)))
     (map #(or (score-rows 100 %)
               (score-rows 1 (tools/transpose %))))
     (apply +)))
