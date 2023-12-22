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
