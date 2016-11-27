(ns game2048.game
  (:require [game2048.board :as b]))

(def max-score 2048)

(def board-size {:width 4 :height 4})

(def agenda
  '("←↑→↓ - make move"
    "r - reset"
    "q - quit"))

(defn new-board []
  (b/add-random-tiles (b/empty-board board-size)))

(defn process-key
  "Either exit or transform board according to a key passed"
  [key board]
  (case key
    (:up :down :left :right) (b/make-move key board)
    \q (System/exit 0)
    \r (b/empty-board board-size)))

(defn check-board
  "Check for logical conditions and transform board accordingly"
  [board-before-keypress board]
  (let [board-after-rand (b/add-random-tiles board)]
   (cond
    (= board-before-keypress board) board
    (b/full? board-after-rand) (new-board)
    (b/contains-max? max-score board) (new-board)
    :else board-after-rand)))

(defn game-step
  [key board]
  (check-board board
    (process-key key board)))
