(ns advent-of-code.december-2023.day7
  (:require [advent-of-code.tools :as tools]
            [clojure.string :as str]))

(defn largest-pair [grouped-hand]
  (->> grouped-hand
       vals
       (map count)
       (apply max)))

(defn determine-type [hand]
  (let [grouped-hand (group-by identity hand)]
    (case (count grouped-hand)
      1 :five-of-a-kind
      2 (if (= 4 (largest-pair grouped-hand))
          :four-of-a-kind
          :full-house)
      3 (if (= 3 (largest-pair grouped-hand))
          :three-of-a-kind
          :two-pair)
      4 :one-pair
      5 :high-card)))

(defn parse-input [raw-input]
  (let [[hand bid] (str/split raw-input #"\s+")]
    {:hand hand
     :bid  (tools/parse-int bid)
     :type (determine-type hand)}))

(def card-order [\A \K \Q \J \T \9 \8 \7 \6 \5 \4 \3 \2])

(defn compare-hands [hand1 hand2]
  (reduce (fn [_ index]
            (let [a1 (get hand1 index)
                  b1 (get hand2 index)]
              (when (not= a1 b1)
                (reduced (if (< (.indexOf card-order a1)
                                (.indexOf card-order b1))
                           -1
                           1)))))
          0
          (range 5)))

(defn problem-1 []
  (->> (tools/read-file-lines "resources/december-2023/day7")
       (mapv parse-input)
       (group-by :type)
       ((juxt :five-of-a-kind :four-of-a-kind :full-house :three-of-a-kind :two-pair :one-pair :high-card))
       (map (partial sort-by :hand compare-hands))
       flatten
       reverse
       (map-indexed (fn [index {:keys [bid]}]
                      (* (inc index) bid)))
       (apply +)))
