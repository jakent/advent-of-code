(ns advent-of-code.december-2023.day7
  (:require [advent-of-code.tools :as tools]
            [clojure.string :as str]))

(defn largest-pair [grouped-hand]
  (let [coll (->> grouped-hand
                  vals
                  (map count))]
    (if (empty? coll)
      0
      (apply max coll))))

(defn determine-type [group-by-fn hand]
  (let [grouped-hand (group-by-fn hand)]
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

(defn parse-input [group-by-fn raw-input]
  (let [[hand bid] (str/split raw-input #"\s+")]
    {:hand hand
     :bid  (tools/parse-int bid)
     :type (determine-type group-by-fn hand)}))

(defn compare-hands [card-order]
  (fn [hand1 hand2]
    (reduce (fn [_ index]
              (let [a1 (get hand1 index)
                    b1 (get hand2 index)]
                (when (not= a1 b1)
                  (reduced (if (< (.indexOf card-order a1)
                                  (.indexOf card-order b1))
                             -1
                             1)))))
            0
            (range 5))))

(defn play-camel-cards [card-order group-by-fn]
  (->> (tools/read-file-lines "resources/december-2023/day7")
       (mapv (partial parse-input group-by-fn))
       (group-by :type)
       ((juxt :five-of-a-kind :four-of-a-kind :full-house :three-of-a-kind :two-pair :one-pair :high-card))
       (map (partial sort-by :hand (compare-hands card-order)))
       flatten
       reverse
       (map-indexed (fn [index {:keys [bid]}]
                      (* (inc index) bid)))
       (apply +)))

(defn problem-1
  "246912307"
  []
  (play-camel-cards [\A \K \Q \J \T \9 \8 \7 \6 \5 \4 \3 \2]
                    (partial group-by identity)))

(defn wildcard-group [hand]
  (let [grouped-hand (group-by identity hand)
        jays         (get grouped-hand \J [])
        without-jays (dissoc grouped-hand \J)
        largest-pair (largest-pair without-jays)]
    (if (zero? largest-pair)
      (assoc without-jays \A jays)
      (reduce (fn [acc k]
                (if (= largest-pair (count (get acc k [])))
                  (update acc k concat jays)
                  acc))
              without-jays
              [\A \K \Q \T \9 \8 \7 \6 \5 \4 \3 \2]))))

(defn problem-2
  "246894760"
  []
  (play-camel-cards [\A \K \Q \T \9 \8 \7 \6 \5 \4 \3 \2 \J]
                    wildcard-group))
