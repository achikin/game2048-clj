(ns game2048.board-test
  (:use clojure.test)
  (:require [game2048.board :as b]))
(def empty-board-3-3 
  {:width 3
   :height 3
   :data '(0 0 0 0 0 0 0 0 0)})

(def empty-board-2-4 
  {:width 2
   :height 4
   :data '(0 0 0 0 0 0 0 0)})

(deftest empty-board
  (is (= (b/empty-board {:width 3 :height 3}) empty-board-3-3))
  (is (= (b/empty-board {:width 2 :height 4}) empty-board-2-4)))

(def part-board-2-2
  {:width 2
   :height 2
   :data '(1 1 2 2)})
(def part-board-2-2-left
  {:width 2
   :height 2
   :data '(2 0 4 0)})
   
(def part-board-2-2-horizontal '((1 1)(2 2)))
(def part-board-2-2-vertical '((1 2)(1 2)))

(deftest part-board
  (is (= (b/part-board :horizontal part-board-2-2) part-board-2-2-horizontal))
  (is (= (b/part-board :vertical part-board-2-2) part-board-2-2-vertical)))

(deftest gather
  (is (= (b/gather :horizontal '((1 1) (2 2))) part-board-2-2))
  (is (= (b/gather :vertical '((1 2) (1 2))) part-board-2-2)))

(defn index-fn-1
  [coll]
  1)
(defn index-fn-3
  [coll]
  3)

(defn value-fn-2
  []
  2)

(defn value-fn-4
  []
  4)
(deftest rand-replace
  (is (= (b/rand-replace index-fn-1 0 value-fn-2 '(0 0 0)) '(0 2 0)))
  (is (= (b/rand-replace index-fn-3 0 value-fn-4 '(0 0 0 0 0)) '(0 0 0 4 0))))
  
(def board-move
  {:width 2
   :height 2
   :data '(2 4 2 4)})
(def board-move-up
  {:width 2
   :height 2
   :data '(4 8 0 0)})

(def board-move-down
  {:width 2
   :height 2
   :data '(0 0 4 8)})
  
(deftest make-move
  (is (= (b/make-move :left part-board-2-2) part-board-2-2-left))
  (is (= (b/make-move :up board-move) board-move-up))
  (is (= (b/make-move :down board-move) board-move-down))
  (is (= (b/make-move :right board-move) board-move)))

(deftest full
  (is (b/full? {:width 2 :height 2 :data '(1 2 3 4)}))
  (is (not (b/full? {:width 2 :height 2 :data '(1 2 3 0)}))))

(deftest contains-max
  (is (b/contains-max? 2048 {:width 2 :height 2 :data '(1 2 2048 4)})))
