(ns game2048.ui
 (:require [game2048.board :as b])
 (:require [lanterna.screen :as s]))

(def maxlen 5)

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
  [board]
  (apply max board))

(defn max-length
  [board]
  (+ (count-digits (max-number board)) 2))

(defn draw-row
  ([screen x y row]
   (if-not (empty? row)
      (do
       (s/put-string screen x y (pad-number maxlen (first row)))
       (recur screen (+ x maxlen) y (rest row))))))

(defn draw-rows
  "Draw each row and update screen"
  [screen x y rows]
  (if-not (empty? rows)
    (do
      (draw-row screen x y (first rows))
      (recur screen x (inc y) (rest rows)))
    (s/redraw screen)))

(defn draw-board
  "Break board into horizontal rows and draw them into lanterna/screen
  returns initial board for further processing"
  [screen x y board]
  (do
    (draw-rows screen x y (b/part-board :horizontal board))
    board))

(defn draw-agenda
  [scr x y [first & rest]]
  (if first
    (do
      (s/put-string scr x y first)
      (recur scr x (inc y) rest))
    (s/redraw scr)))
