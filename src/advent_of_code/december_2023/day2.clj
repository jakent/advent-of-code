(ns advent-of-code.december-2023.day2
  (:require [advent-of-code.tools :as tools]
            [clojure.string :as str]))

(def bag {:red   12
          :green 13
          :blue  14})

(defn parse-command [command]
  (reduce (fn [acc ball-color-str]
            (let [[num color] (str/split ball-color-str #" ")]
              (assoc acc (keyword color) (Integer/parseInt num))))
          {}
          (str/split command #", ")))

(defn tokenize [game-line]
  (let [[game xs] (str/split game-line #": ")]
    [(Integer/parseInt (second (str/split game #" ")))
     (map parse-command
          (str/split xs #"; "))]))

(defn reveal-possible-games [[game-num game-tokens]]
  (if (->> (map (fn [cubes]
                  (every? (fn [[color num]]
                            (>= (bag color) num))
                          cubes))
                game-tokens)
           (every? true?))
    game-num
    0))

(defn problem-1 []
  (->> (tools/read-file-lines "resources/december-2023/day2")
       (map tokenize)
       (into {})
       (map reveal-possible-games)
       (apply +)))

(defn find-greatest [game-tokens]
  (reduce (fn [{max-red   :red
                max-green :green
                max-blue  :blue
                :or       {max-red 0 max-green 0 max-blue 0}}
               {:keys [red green blue]
                :or   {red 0 green 0 blue 0}}]
            {:red   (if (> red max-red) red max-red)
             :green (if (> green max-green) green max-green)
             :blue  (if (> blue max-blue) blue max-blue)})
          game-tokens))

(defn calculate-power [[_ game-tokens]]
  (->> (find-greatest game-tokens)
       vals
       (apply *)))

(defn problem-2 []
  (->> (tools/read-file-lines "resources/december-2023/day2")
       (map tokenize)
       (into {})
       (map calculate-power)
       (apply +)))
