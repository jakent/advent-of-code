(ns advent-of-code.december-2023.day6
  (:require [advent-of-code.tools :as tools]
            [clojure.string :as str]))

(defn remove-labels [x]
  (second (str/split x #":\s+")))

(defn find-number-of-winning-durations [[race-length record-distance]]
  (->> (range 1 (inc race-length))
       (filter (fn [hold-duration]
                 (> (* (- race-length hold-duration)
                       hold-duration)
                    record-distance)))
       count))

(defn problem-1
  "128700"
  []
  (->> (tools/read-file-lines "resources/december-2023/day6")
       (map #(tools/read-vector-string (remove-labels %)))
       tools/transpose
       (map find-number-of-winning-durations)
       (apply *)))

(defn remove-whitespace [s]
  (str/replace s #"\s+" ""))

(defn problem-2 []
  (->> (tools/read-file-lines "resources/december-2023/day6")
       (map #(tools/read-vector-string (remove-whitespace (remove-labels %))))
       tools/transpose
       (map find-number-of-winning-durations)
       first))
