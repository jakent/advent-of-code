(ns advent-of-code.tools
  (:require [clojure.java.io :as io]))

(defn read-file-lines [filename]
  (with-open [rdr (io/reader filename)]
    (->> (line-seq rdr)
         (into []))))

(defn parse-int [s]
  (try (Integer/parseInt (str s))
       (catch Exception _)))

(defn read-vector-string [s]
  (read-string (format "[%s]" s)))

(defn neighbors [[x y]]
  (for [x2 [-1 0 1]
        y2 [-1 0 1]
        :when (not= x2 y2 0)]
    (vector (+ x x2) (+ y y2))))

(defn file-lines->location-map [file-lines]
  (->> (map-indexed (comp (partial into {})
                          (fn [[x row]]
                            (map-indexed
                              (fn [y cell] [[x y] cell])
                              row))
                          vector)
                    file-lines)
       (apply merge)))

(defn transpose [xs]
  (->> (apply interleave xs)
       (partition (count xs))))
