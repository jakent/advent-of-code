(ns advent-of-code.december-2023.day1
  (:require [advent-of-code.tools :as tools]
            [clojure.string :as str]))

(defn remove-letters [code]
  (keep #(try (Integer/parseInt (str %))
              (catch Exception _))
        (vec code)))

(defn find-sum-of-first-and-last-ints [xs]
  (->> xs
       (map #(let [ints (remove-letters %)]
               (Integer/parseInt (str (first ints) (last ints)))))
       (apply +)))

(defn problem-1 []
  (find-sum-of-first-and-last-ints (tools/read-file-lines "resources/december-2023/day1")))

(def int-strings (reverse (map-indexed (fn [idx x] [(str (inc idx)) x])
                                       [#"one" #"two" #"three" #"four" #"five" #"six" #"seven" #"eight" #"nine"])))

(defn test [s acc idx re]
  (let [rs   (str/replace s re idx)
        racc (str/replace acc re idx)]
    (when (and (not= (count s) (count rs))
               (= (count acc) (count racc))
               (not= rs racc)
               (not= rs acc))
      (println [s rs acc racc]))))

(defn problem-2 []
  (->> (read-file-lines "resources/december-2023/day1")
       (map #(reduce (fn [acc [idx re]]
                       (test % acc idx re)
                       (str/replace acc re idx))
                     %
                     int-strings))
       find-sum-of-first-and-last-ints))
