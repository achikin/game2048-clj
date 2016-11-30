(ns game2048.core
  (:require [nightlight.core :refer [start]])
  (:require [game2048.ui :as ui])
  (:require [lanterna.screen :as s])
  (:require [game2048.game :as g])
  (:gen-class))

(def x 1)
(def y 1)

(defn game-loop
  [scr board]
  (recur
    scr
    (ui/draw-board
      scr x y
        (g/game-step (s/get-key-blocking scr) board))))

(defn -main []
  (do
    (start {:port 4000})
    (let [scr (s/get-screen) board (g/new-board)]
      (s/in-screen scr
        (do
          (ui/draw-board scr x y board)
          (ui/draw-agenda scr x (+ y (:height g/board-size) 1) g/agenda)
          (game-loop scr board))))))
