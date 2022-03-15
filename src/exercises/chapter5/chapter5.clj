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
               (apply (first gs) args)
               (recur (drop-last gs) (vector (apply (last gs) args))))) fs))

(def add-then-inc-recursive (my-comp-recursive inc +))

(add-then-inc-recursive 1 2 3)
;; => 7

(def my-c-int-recursive (my-comp-recursive :intelligence :attributes))

(my-c-int-recursive character)
;; => 10

;; can this handle the anonymous function example?
(def stress-test (my-comp-recursive inc #(/ % 2) +))

;; sure can B^)
(stress-test 1 2 3 4)
;; => 6

;; exercise 3

(defn my-assoc-in
  [m [k & ks] v]
  (println m k v)
  (if (empty? ks)
    (assoc m k v)
    (assoc m k (my-assoc-in (get m k) ks v))))

(def test-map {:hello {:world {:foo "bar"}}})

(hash-map :hello (get test-map :hello))

(my-assoc-in test-map [:hello :world :foo] "baz")
;; => {:hello {:world {:foo "baz"}}}

(def test-vector [{:name "John Shaft" :theme "Who is the man, who would risk his neck for his brother man?"}
                  {:name "Tony Soprano" :theme "Woke up this morning, got yourself a gun."}])

(my-assoc-in test-vector [1 :fuel] "Gabagool!")
;; => [...,
;;     {:name "Tony Soprano" 
;;      :theme "Woke up this morning, got yourself a gun."
;;      :fuel "Gabagool!"}]


;; exercise 4

(def mvp-candidates {:embiid {:team "sixers" :mvps 0}
                     :antetokounmpo {:team "bucks" :mvps 2}
                     :jokic {:team "nuggets" :mvps 1}})

(update-in mvp-candidates [:embiid :mvps] inc)
;; => {:embiid {:team "sixers", :mvps 1} ... }

;; exercise 5

(defn my-update-in
  [m [k & ks] f]
  (if (empty? ks)
    (assoc m k (f (get m k)))
    (assoc m k (my-update-in (get m k) ks f))))

(my-update-in mvp-candidates [:embiid :mvps] inc)
;; => {:embiid {:team "sixers", :mvps 1} ... }

