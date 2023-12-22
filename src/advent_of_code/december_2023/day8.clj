(ns advent-of-code.december-2023.day8
  (:require [advent-of-code.tools :as tools]
            [clojure.string :as str]))

(defn parse-instructions [raw]
  (map #(case % \L :left \R :right) raw))

(defn parse-lookup [raw]
  (reduce (fn [acc x]
            (let [[k v] (str/split x #" = ")
                  [left right] (map str (read-string v))]
              (assoc acc k {:left left :right right})))
          {}
          raw))

(defn navigate-the-haunted-wasteland [instructions lookup]
  (reduce (fn [[i current-location] direction]
            (let [destination (direction (get lookup current-location))]
              (if (= "ZZZ" destination)
                (reduced (inc i))
                [(inc i) destination])))
          [0 "AAA"]
          (cycle instructions)))

(defn problem-1
  "20569"
  []
  (let [[instructions _ & rest] (tools/read-file-lines "resources/december-2023/day8")]
    (navigate-the-haunted-wasteland (parse-instructions instructions)
                                    (parse-lookup rest))))
