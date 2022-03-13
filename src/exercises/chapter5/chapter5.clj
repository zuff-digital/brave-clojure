(ns exercises.chapter5.chapter5)

;; exercise 1
(def character
  {:name "Frank Lucas"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})

(defn get-attr
  "Accepts an attribute, returns a function that retrieves 
   that attribute from a character"
  [attr]
  (comp attr :attributes))

(def get-int (get-attr :intelligence))

(get-int character)
;; => 10

;; exercise 2.1

(defn my-comp
  [& fs]
  (fn [& args]
    (let [rfs (reverse fs)]
      (reduce
       (fn [acc f]
         (f acc))
       (apply (first rfs) args)
       (rest rfs)))))

(def add-then-inc (my-comp inc +))

(add-then-inc 1 2)
;; => 4

(def my-c-int (my-comp :intelligence :attributes))

(my-c-int character)
;; => 10

;; exercise 2.2

(defn my-comp-recursive
  [& fs]
  (partial (fn [gs & args]
             (if (empty? (rest gs))
               ((first gs) args)
               (recur (drop-last gs) (apply (last gs) args)))) fs))

(def add-then-inc-recursive (my-comp-recursive inc +))

(add-then-inc-recursive 1 2 3)
;; => 7

(def my-c-int-recursive (my-comp-recursive :intelligence :attributes))

(my-c-int-recursive character)
;; => 10

;; exercise 3

