(ns miggens-frontend.utils.strings
  (:require [clojure.string :as cstr]))

(def image-prefix "/img/")

(def all-contents-metadata-uri "https://api.miggens.com/contents-metadata")

(def get-content-by-title-uri "https://api.miggens.com/content?title=")

(def about-site-title "About This Site")

(defn uri-title-to-keyword
  ""
  [uri-title]
  (cstr/lower-case (cstr/replace uri-title #"\+" "-")))

(defn get-metadata-by-title
  ""
  [metadata-list title-to-find]
  (loop [ml metadata-list]
    (if (empty? ml)
      nil
      (let [metadata (first ml)
            meta-title (:title metadata)]
        (if (= meta-title title-to-find)
          metadata
          (recur (rest ml)))))))

;#"^<\w+:(\w+):(h.+)>"
(defn paragraph-link-for-a-link
  ""
  [plink]
  (let [re #"^<\w+:(\w+):(h.+)>"
        found (re-find re plink)]
    (if (nil? found)
      nil
      (let [link-title (second found)
            link (last found)]
        [:a {:href link} link-title]))))

(defn parse-paragraph-links
  ""
  [dirty-paragraph]
  (let [p-tokens (cstr/split dirty-paragraph #"\s+")]
    (loop [tks p-tokens app-str "" out []]
      ;(println "APP STR " app-str)
      (if (empty? tks)
        out
        (let [a-tag (paragraph-link-for-a-link (first tks))]
          (if (nil? a-tag)
            (recur (rest tks) (str app-str " " (first tks)) out)
            (recur (rest tks) nil (conj out (cstr/trim app-str) a-tag))))) )))
