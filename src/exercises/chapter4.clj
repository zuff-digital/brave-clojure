(ns exercises.chapter4)

;; "A Vampire Data Analysis Program"

(def filename "records/suspects.csv")

(slurp filename)

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(parse (slurp filename))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))


(glitter-filter 3 (mapify (parse (slurp filename))))

;; exercise 1

(defn glitter-filter-names
  "Return a list of names of those who satisfy the glitter requirement"
  [glitter-filter min-glitter records]
  (reduce #(conj %1 (:name %2))
          '()
          (glitter-filter min-glitter records)))

(glitter-filter-names glitter-filter 3 (mapify (parse (slurp filename))))
;; => ("Carlisle Cullen" "Jacob Black" "Edward Cullen")

;; exercise 2

(defn append
  "Add the value associated with the :name key of a record to a list of names"
  [glittery-names-list record]
  (conj glittery-names-list (:name record)))

(def glittery-list (glitter-filter-names glitter-filter 3 (mapify (parse (slurp filename)))))

(append glittery-list {:name "Nosferatu" :glitter-index 4})
;; => ("Nosferatu" "Carlisle Cullen" "Jacob Black" "Edward Cullen")


;; exercise 3 [wip, unsolved]

(def validate-keys {:name some?
                    :glitter-index some?})
(defn validate
  "Ensure that a record has a :glitter-index key and :name key prior to appending to a list"
  [criteria-map record]
  (reduce (fn [acc [key val]]
            (if ((get criteria-map key) val)
              (append acc record)
              acc))
          glittery-list
          record))


(def validated-list (validate validate-keys {:name "zuff" :glitter-index nil}))
validated-list

;; exercise 4

(defn csv-ify
  "Accept a list of maps, return a CSV"
  [coll]
  (reduce (fn [csv vamp-map]
            (let [vals (map val vamp-map)]
              (str csv (clojure.string/join "," vals) "\n")))
          ""
          coll))

(def test-collection '({:name "Edward Cullen", :glitter-index 10}
                       {:name "Jacob Black", :glitter-index 3}
                       {:name "Carlisle Cullen", :glitter-index 6}))

(csv-ify test-collection)
;;=> "Edward Cullen,10\nJacob Black,3\nCarlisle Cullen,6\n"

