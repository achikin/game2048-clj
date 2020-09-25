(ns game2048.core
  (:require [game2048.ui :as ui]
            [lanterna.screen :as s]
            [game2048.game :as g])
  (:gen-class))

(def x 1)
(def y 1)


(defn game-loop
  [scr board]
  (recur
    scr
    (ui/render-board
      scr x y
        (g/game-step (s/get-key-blocking scr) board))))


(defn -main []
  (let [scr (s/get-screen) board (g/new-board)]
    (s/in-screen scr
                 (do
                   (ui/render-board scr x y board)
                   (ui/render-agenda scr x (+ y (:height g/board-size) 1) g/agenda)
                   (game-loop scr board)))))
