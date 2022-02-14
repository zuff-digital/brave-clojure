(ns exercises.chapter3)

;; exercise 1

(def sometimes-nothing "a real cool hand.")
(str "Sometimes nothing can be " sometimes-nothing " -Cool Hand Luke")

(def miyazaki (vector "Spirited Away" "Princess Mononoke" "Howl's Moving Castle"))
(def scorsese (list "Goodfellas" "The Departed" "The Wolf of Wall Street"))
(def genre (hash-map :horror "Get Out" :sci-fi "Arrival" :samurai "13 Assassins"))
(def spikes (hash-set "Spike Lee" "Spike Lee" "Spike Jonze"))

(def recommendations (vector miyazaki scorsese genre spikes))

;; exercise 2

(defn add100
  "Add 100 to a number"
  [num]
  (+ 100 num))

(add100 10)
(def dalmations (add100 1))
(add100 (reduce + [100 101 102]))

;; exercise 3

(defn dec-maker
  "Create a custom decrementor"
  [dec-by]
  #(- % dec-by))

(def dec7 (dec-maker 7))
(def dec99 (dec-maker 99))

(dec7 7)
(dec99 100)

;; exercise 4

(defn mapset
  "Apply a given function to the items in a collection, return the result as a set"
  [f coll]
  (set (map f coll)))

(mapset inc [1 1 2 2])

;; exercises 5 and 6

(def asym-body-parts [{:name "head" :size 3}
                      {:name "left-eye" :size 1}
                      {:name "left-ear" :size 1}
                      {:name "mouth" :size 1}
                      {:name "nose" :size 1}
                      {:name "neck" :size 2}
                      {:name "left-shoulder" :size 3}
                      {:name "left-upper-arm" :size 3}
                      {:name "chest" :size 10}
                      {:name "back" :size 10}
                      {:name "left-forearm" :size 3}
                      {:name "abdomen" :size 6}
                      {:name "left-kidney" :size 1}
                      {:name "left-hand" :size 2}
                      {:name "left-knee" :size 2}
                      {:name "left-thigh" :size 4}
                      {:name "left-lower-leg" :size 3}
                      {:name "left-achilles" :size 1}
                      {:name "left-foot" :size 2}])


(defn multiply-part
  "Expects a body part (a map with :name and :size),
   returns a map with the appropriate number of that body part specified by the :number keyword"
  [part num]
  (if (clojure.string/starts-with? (:name part) "left-")
    {:name (clojure.string/replace (:name part) "left-" "")
     :size (:size part)
     :number num}
    part))

(defn expand-body-parts
  "Expects a seq of maps that have :name and :size, 
   and a number by which to multiply the repeating body parts"
  [asym-body-parts num]
  (reduce
   (fn [final-body-parts part]
     (into final-body-parts [(multiply-part part num)]))
   []
   asym-body-parts))

(expand-body-parts asym-body-parts 3)
(expand-body-parts asym-body-parts 5)

(into {:a 1 :b 2} {:c 3 :d 4})

;; sample output

;; [{:name "head", :size 3}
;;  {:name "eye", :size 1, :number 3}
;;  {:name "ear", :size 1, :number 3}
;;  {:name "mouth", :size 1}
;;  {:name "nose", :size 1}
;;  {:name "neck", :size 2}
;;  {:name "shoulder", :size 3, :number 3}
;;  {:name "upper-arm", :size 3, :number 3}
;;  {:name "chest", :size 10} {:name "back", :size 10}
;;  {:name "forearm", :size 3, :number 3}
;;  {:name "abdomen", :size 6}
;;  {:name "kidney", :size 1, :number 3}
;;  {:name "hand", :size 2, :number 3}
;;  {:name "knee", :size 2, :number 3}
;;  {:name "thigh", :size 4, :number 3}
;;  {:name "lower-leg", :size 3, :number 3}
;;  {:name "achilles", :size 1, :number 3}
;;  {:name "foot", :size 2, :number 3}]
