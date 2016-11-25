(ns game2048.board
  (:require [game2048.row :as row]))

(defn get-n-zeroes
  [n]
  (repeat n 0))
  
(defn empty-board 
  "Board is represented as width, height and 1 dimentional list of fields
  Zero represents empty field"
  [size]
  (merge size {:data (get-n-zeroes (* (:width size) (:height size)))}))
    
(defn part-board
  "Partition board list into horizontal or vertical slices"
  [direction board]
  (case direction
    :horizontal (partition (:width board) (:data board))
    :vertical (partition (:height board) 
               (apply interleave 
                 (partition (:width board) (:data board))))))

(defn gather
  "Gather board from horizontal or vertical slices
back into map"
  [direction board]
  (case direction
    :horizontal {:width (-> board first count) 
                 :height (count board) 
                 :data (apply concat board)}
    :vertical {:width (count board) 
               :height (-> board first count) 
               :data (apply interleave board)}))

(defn find-indexes
  "Find all indexes of value in collection" 
  [val coll]
  (reduce-kv 
    (fn [a k v] (if (= v val) (conj a k) a))                     
    []
    coll)) 
  
(defn choose-index
  "Choose random value from collection"
  [indexes]
  (rand-nth indexes))

(defn choose-value 
  "2 chosen with 2/3 probability
   4 chosen with 1/3 probability"
  []
  (rand-nth '(2 2 4)))
  
(defn rand-replace
  "Replace one value in collection with another one chosen with index-fn"
  [index-fn oldval value-fn coll]
  (let [array (into [] coll)
        indexes (find-indexes oldval array)]
   (if (empty? indexes)
       coll
       (seq (assoc array 
              (index-fn indexes) (value-fn))))))

(defn add-random-tile
  "Replace random zero with 2 or 4 in seq"
  [board]
  (rand-replace choose-index 0 choose-value board))

(defn add-random-tiles
  "Replace random zero with 2 or 4 in board
  in case if you want to add more than one tile"
  [board]
  (assoc board :data (add-random-tile (:data board))))


(defn which-partition
  "Determine if move is horizontal or vertical"
  [direction]
  (if (contains? #{:left :right} direction)
    :horizontal
    :vertical))

(which-partition :left)

"Up movement is eqivalent to left movement
and down movement equivalent to right movement"
(def dir-map 
  {:up :left 
   :down :right 
   :left :left 
   :right :right})

(defn make-move
  "Break board into either horizontal or vertical slices
perform move on each slice, and gather result back into new board"
  [direction board]
  (let [part (which-partition direction)]
    (gather part
      (map #(row/move (direction dir-map) %) (part-board part board)))))

(defn full?
  "True if there are no empty(0) fields left"
  [board]
  (empty? (filter #(= 0 %) (:data board))))

(defn contains-max?
  "True if one of the sells reached maximum value"
  [max-score board]
  (not (empty? (filter #(= max-score %) (:data board)))))

