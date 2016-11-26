(ns game2048.row)

(defmulti padd (fn [direction len coll] direction))
"Pad collections with zeroes either on the left or on the right"
(defmethod padd :left
  [direction len coll]
  (concat (repeat (- len (count coll)) 0) coll))
(defmethod padd :right
  [direction len coll]
  (concat coll (repeat (- len (count coll)) 0)))
  

(defmulti merger(fn [dir & args] dir))
"Check if there are equal adjustent fields and merge them
e.g. (merger :left '(1 1 2 2)) -> (2 4)"
(defmethod merger :left
  ([dir [first second & rest] newrow]
   (if first
    (if (= first second)
     (recur dir rest (cons (+ first second) newrow))
     (recur dir (cons second rest) (cons first newrow)))
    (reverse newrow)))
  ([dir row]
   (merger dir row '())))

(defmethod merger :right
  [dir row]
  (reverse (merger :left (reverse row))))


(defn remove-zeroes
  "Return collection dropping all zeroes"
  [coll]
  (filter (fn [x] (not (zero? x))) 
    coll))

(defn opposite-dir
  [dir]
  (case dir
    :left :right
    :right :left))

(defn move
  "Remove zeroes, then merge values, then pad result with zeroes
  e.g. (move :left '(1 1 0 2 2) -> (1 1 2 2) -> (2 4 0 0 0)"
  [dir row]
  (let [row-size (count row)]
    (padd (opposite-dir dir) row-size (merger dir (remove-zeroes row)))))

