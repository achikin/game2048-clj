(ns game2048.utils)


(defn count-digits [n]
  (if (zero? n) 1
      (-> n Math/log10 Math/floor long inc)))


(defn repeat-str
  [n st]
  (apply str (repeat n st)))


(defn pad-number
  [length number]
  (let [n (count-digits number)
        pads (/ (- length n) 2)]
    (apply str (repeat-str pads " ") (str number) (repeat-str pads " "))))


(defn max-number
  [coll]
  (apply max coll))
