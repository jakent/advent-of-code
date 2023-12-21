(ns advent-of-code.december-2023.day5
  (:require [clojure.string :as str]))

(defn read-vector-string [s]
  (read-string (format "[%s]" s)))

(defn parse-seeds [^String raw]
  (-> (str/split raw #"seeds: ")
      second
      read-vector-string))

(defn parse-conversion-map [^String raw]
  (->> (read-vector-string raw)
       (partition 3)))

(defn parse-almanac [^String raw-almanac]
  (let [[seeds seeds->soil soil->fertilizer fertilizer->water water->light light->temp temp->humidity humidity->location]
        (str/split raw-almanac
                   #"(seed-to-soil map:|soil-to-fertilizer map:|fertilizer-to-water map:|water-to-light map:|light-to-temperature map:|temperature-to-humidity map:|humidity-to-location map:)")]
    {:seeds                   (parse-seeds seeds)
     :seed-to-soil            (parse-conversion-map seeds->soil)
     :soil-to-fertilizer      (parse-conversion-map soil->fertilizer)
     :fertilizer-to-water     (parse-conversion-map fertilizer->water)
     :water-to-light          (parse-conversion-map water->light)
     :light-to-temperature    (parse-conversion-map light->temp)
     :temperature-to-humidity (parse-conversion-map temp->humidity)
     :humidity-to-location    (parse-conversion-map humidity->location)}))

(defn read-conversion-map [source conversion-matrix]
  (reduce (fn [default [destination-range-start source-range-start range-length]]
            (if (<= source-range-start source (+ source-range-start (dec range-length)))
              (reduced (+ destination-range-start (- source source-range-start)))
              default))
          source
          conversion-matrix))

(defn find-min-location [{:keys [seed-to-soil
                                 soil-to-fertilizer
                                 fertilizer-to-water
                                 water-to-light
                                 light-to-temperature
                                 temperature-to-humidity
                                 humidity-to-location]}]
  (map (comp #(read-conversion-map % humidity-to-location)
             #(read-conversion-map % temperature-to-humidity)
             #(read-conversion-map % light-to-temperature)
             #(read-conversion-map % water-to-light)
             #(read-conversion-map % fertilizer-to-water)
             #(read-conversion-map % soil-to-fertilizer)
             #(read-conversion-map % seed-to-soil))))

(defn problem-1 []
  (let [{:keys [seeds] :as almanac} (parse-almanac (slurp "resources/december-2023/day5"))]
    (->> (transduce (find-min-location almanac) conj seeds)
         (apply min))))

(defn problem-2 [_]
  (let [{:keys [seeds] :as almanac} (parse-almanac (slurp "resources/december-2023/day5"))]
    (transduce (comp (mapcat (fn [[start range-length]]
                               (range start (+ start range-length))))
                     (find-min-location almanac))
               min
               Long/MAX_VALUE
               (partition 2 seeds))))
