(ns advent-of-code.december-2023.day8
  (:require [advent-of-code.tools :as tools]
            [clojure.string :as str]))

(defn parse-instructions [raw]
  (map #(case % \L :left \R :right) raw))

(defn parse-lookup [raw]
  (reduce (fn [acc x]
            (let [[k v] (str/split x #" = ")
                  [_ left right] (re-matches #"\((\S{3}), (\S{3})\)" v)]
              (assoc acc k {:left left :right right})))
          {}
          raw))

(defn navigate-the-haunted-wasteland [{:keys [instructions lookup]} start exit-condition]
  (reduce (fn [[i current-location] direction]
            (let [destination (direction (get lookup current-location))]
              (if (exit-condition destination)
                (reduced (inc i))
                [(inc i) destination])))
          [0 start]
          (cycle instructions)))

(defn parse [filename]
  (let [[instructions _ & rest] (tools/read-file-lines filename)]
    {:instructions (parse-instructions instructions)
     :lookup       (parse-lookup rest)}))

(defn problem-1
  "20569"
  []
  (navigate-the-haunted-wasteland (parse "resources/december-2023/day8")
                                  "AAA"
                                  (partial = "ZZZ")))

(defn -gcd [x y]
  (if (zero? y)
    x
    (recur y (mod x y))))

(defn gcd [xs]
  (reduce -gcd xs))

(defn lcm [xs]
  (reduce (fn [x y]
            (* x (/ y (gcd [x y]))))
          1
          xs))

(defn problem-2
  "21366921060721"
  []
  (let [{:keys [lookup] :as parsed} (parse "resources/december-2023/day8")]
    (->> (filterv #(str/ends-with? % "A") (keys lookup))
         (map #(navigate-the-haunted-wasteland parsed
                                               %
                                               (fn [x] (str/ends-with? x "Z"))))
         lcm)))
