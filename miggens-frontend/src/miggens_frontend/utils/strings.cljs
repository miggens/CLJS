(ns miggens-frontend.utils.strings
  (:require [clojure.string :as cstr]
            [struct.core :as st]))

(def image-prefix "/img/")

(def web-gl-logo-url "https://img.icons8.com/ios/50/000000/webgl.png")

(def all-contents-metadata-uri "https://api.miggens.com/contents-metadata")

(def get-content-by-title-uri "https://api.miggens.com/content?title=")

(def post-contact-uri "https://api.miggens.com/save-contact")
;(def post-contact-uri "http://localhost:5000/save-contact")

(def about-site-title "About This Site")

(def developer-page-purpose
  (str "Unfortunately a degree isn't enough... one must offer a demonstration. Below you'll"
       " find my efforts, some apps, some repos, some you can run, and others I hope you'll pay."))

(def contact-form-schema 
  {:name [st/required st/string]
   :email [st/required st/email]
   :msg [st/required st/string]})

(defn validate-contact-form
  ""
  [contact-form-map]
  (st/validate contact-form-map contact-form-schema))

(defn parse-dt
  ""
  [dt]
  (let [reg #"(.*)T(.*)"
        grps (re-find reg dt)]
    [(second grps) (last grps)]))

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
(defn plink-for-a-tag
  ""
  [plink-vec]
  (let [re #"^<\w+:(\w+):(.+)>"
        f #(re-find re %1)
        finds (mapv f plink-vec)
        groups (mapv #(rest %1) finds)
        fgroups (flatten groups)]
    (loop [fg fgroups out []]
      (if (empty? fg)
        out
        (recur (drop 2 fg) (conj out [:a {:href (second fg)} (first fg)]))))))

(defn weave-paragraphs-and-a-tags
  ""
  [ps as]
  (if (= (count ps) (count as))
    (interleave ps as)
    (loop [p ps a as out []]
      (if (empty? a)
        (conj out (first p))
        (recur (rest p) (rest a) (conj out (first p) (first a)))))))

(defn parse-paragraph-links
  ""
  [dirty-paragraph]
  (let [p-tokens (cstr/split dirty-paragraph #"\s+")
        re #"^<\w+:(\w+):(.+)>"
        pred #(not (= nil (re-find re %1)))
        filtered-tokens (filterv pred p-tokens)
        v-of-a-tags (plink-for-a-tag filtered-tokens)
        p-no-links (cstr/split dirty-paragraph #"\s*<[\w|\d|\/|\.|:|\?|\-|=]+>\s*")]
    ;(interleave p-no-links v-of-a-tags)
    (apply list (weave-paragraphs-and-a-tags p-no-links v-of-a-tags))))
