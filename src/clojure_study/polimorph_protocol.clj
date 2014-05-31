(ns clojure-study.polimorph-protocol
  (:use clojure-study.assertion))

;;extend existing types with protocol
(defprotocol Whoami
  (whoami [this]))

(extend-protocol Whoami
  java.lang.Boolean
  (whoami [this]
    (str "I'm a boolean: " this))
  clojure.lang.Keyword
  (whoami [this]
    (str "I'm a keyword: " this))
  nil
  (whoami [this]
    "I'm a null"))

(assert-equals "I'm a boolean: true" (whoami true))
(assert-equals "I'm a keyword: :a" (whoami :a))
(assert-equals "I'm a null" (whoami nil))

<<<<<<< HEAD
;;defprotocol
(defprotocol Dog
  (eat [this])
  (bark [this]))

(defrecord Terrier)

=======
;;defrecord
(defrecord Car [year color])
(def kit (Car. 1987 :black))

(assert-equals 1987 (:year kit))

;; extend protocol
(defrecord Person [name age]
  Whoami
  (whoami [this] (str "I am " name ", " age " yrs old")))

(def john18 (Person. "John" 18))
(def jane17 (->Person "Jane" 17))

(assert-equals "I am John, 18 yrs old" (whoami john18))
(assert-equals "I am Jane, 17 yrs old" (whoami jane17))

;;defrecord with methods
(defprotocol Person-change
  (year-passed [this])
  (year-passed2 [this])
  (years-passed [this years])
  (me [this]))

(defrecord Changing-person [name age]
  Person-change
  (year-passed [this] (Changing-person. name (inc age)))
  (year-passed2 [this] (assoc this 
                              :age (inc age)))
  (years-passed [this years] (Changing-person. name (+ years age)))
  (me [this] this))

(def Jack (Changing-person. "Jack" 22))
(assert-equals (Changing-person. "Jack" 23)
               (year-passed Jack))
(assert-equals (Changing-person. "Jack" 23)
               (year-passed2 Jack))
(assert-equals (Changing-person. "Jack" 32)
               (years-passed Jack 10))
(assert-equals Jack
               (me Jack))

;;
>>>>>>> 8f89a3d6a495ee6d32b19c53f78da8a5b04e9ab8
