(ns advent-of-code.tools
  (:require [clojure.java.io :as io]))

(defn read-file-lines [filename]
  (with-open [rdr (io/reader filename)]
    (->> (line-seq rdr)
         (into []))))

(defn parse-int [s]
  (try (Integer/parseInt (str s))
       (catch Exception _)))
