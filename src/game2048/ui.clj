(ns game2048.ui
 (:require [game2048.board :as b])
 (:require [lanterna.screen :as s])
 (:require [game2048.utils :as utils]))

(def maxlen 5)


(defn max-length
  [board]
  (+ (utils/count-digits (utils/max-number board)) 2))


(defn render-row
  ([screen x y row]
   (if-not (empty? row)
      (do
       (s/put-string screen x y (utils/pad-number maxlen (first row)))
       (recur screen (+ x maxlen) y (rest row))))))


(defn render-rows
  "Draw each row and update screen"
  [screen x y rows]
  (if-not (empty? rows)
    (do
      (render-row screen x y (first rows))
      (recur screen x (inc y) (rest rows)))
    (s/redraw screen)))


(defn render-board
  "Break board into horizontal rows and draw them into lanterna/screen
  returns initial board for further processing"
  [screen x y board]
  (do
    (render-rows screen x y (b/part-board :horizontal board))
    board))


(defn render-agenda
  [scr x y [first & rest]]
  (if first
    (do
      (s/put-string scr x y first)
      (recur scr x (inc y) rest))
    (s/redraw scr)))
