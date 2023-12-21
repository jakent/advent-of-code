(ns advent-of-code.december-2023.day4
  (:require [advent-of-code.tools :as tools]
            [clojure.set :as set]
            [clojure.string :as str]))

(defn calculate-score [my-winning-numbers]
  (reduce (fn [acc _]
            (if (zero? acc)
              1
              (* acc 2)))
          0
          my-winning-numbers))

(defn find-winning-numbers [[winning-numbers my-numbers]]
  (set/intersection (set winning-numbers) (set my-numbers)))

(defn tokenize [game-line]
  (let [[_ xs] (str/split game-line #": ")]
    (mapv (comp (partial mapv tools/parse-int)
                #(str/split % #"\s+"))
          (str/split xs #"\s\|\s+"))))

(defn problem-1 []
  (->> (tools/read-file-lines "resources/december-2023/day4")
       (map (comp calculate-score
                  find-winning-numbers
                  tokenize))
       (apply +)))
