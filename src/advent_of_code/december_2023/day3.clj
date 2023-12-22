(ns advent-of-code.december-2023.day3
  (:require [advent-of-code.tools :as tools]))

(defn is-symbol? [char]
  (if (or (nil? char)
          (#{\. \0 \1 \2 \3 \4 \5 \6 \7 \8 \9} char))
    false
    true))

(defn adjacent-symbol? [chars [x y]]
  (->> (tools/neighbors [x y])
       (map (partial get-in chars))
       (some is-symbol?)
       boolean))

(defn find-valid-part-numbers [chars]
  (map-indexed (fn [x row]
                 (->> (conj row \.)
                      (map-indexed (fn [y cell]
                                     {:adjacent-symbol? (adjacent-symbol? chars [x y])
                                      :number           (tools/parse-int cell)}))
                      (reduce (fn [{:keys [prev adjacent? keepers]}
                                   {:keys [adjacent-symbol? number]}]
                                {:prev      (when number (str prev number))
                                 :adjacent? (when number (or adjacent? adjacent-symbol?))
                                 :keepers   (if (and (nil? number)
                                                     (some? prev)
                                                     adjacent?)
                                              (conj keepers prev)
                                              keepers)})
                              {:keepers []})
                      :keepers
                      (map tools/parse-int)))
               chars))

(defn problem-1 []
  (->> (tools/read-file-lines "resources/december-2023/day3")
       (mapv vec)
       find-valid-part-numbers
       flatten
       (apply +)))

