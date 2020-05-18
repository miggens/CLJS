(ns miggens-frontend.views.about
  (:require [goog.string :as gstring]
            [re-frame.core :as rf]
            [miggens-frontend.subs.core :as subs]
            [miggens-frontend.views.core :as views-core]
            [miggens-frontend.utils.strings :as util-strs]))

(defn page
  ""
  []
  [:div
   (views-core/navbar)
   [:div.columns.site-background
    [:div.column.is-2]
    [:div.column.is-7
     [:h1.is-size-1.has-text-right.page-header
      "What's All This..."]
     [:figure.image.is-128x128.about-image
      [:img.is-rounded {:src (str util-strs/image-prefix "about_me_256x256.png")}]]
     ;[:p.about-content.is-size-5 "Here is a quickline"]
     (let [content-metadata-list @(rf/subscribe [::subs/content-metadata])
           about-site-content @(rf/subscribe [::subs/about-site-content])
           content-title (:title about-site-content)
           content-metadata (util-strs/get-metadata-by-title content-metadata-list content-title)
           ;about-title (:title content-metadata)
           about-created-on (:createdOn content-metadata)
           tags-list (:tagsList content-metadata)
           full-contents-list (:fullContentList about-site-content)]
       [:div
        [:p.is-size-2.title-font-weight content-title]
        [:p.is-size-5 about-created-on]
        [:div.tags.top-bottom
         (for [tag tags-list]
           [:span.tag.is-primary.is-medium {:key tag}
            tag])]
        [:div
         (for [paragraph full-contents-list]
           (let [strings-and-links (util-strs/parse-paragraph-links paragraph)]
             [:p.is-size-5.paragraph {:key paragraph}
              (str strings-and-links)]))]])]
    [:div.column.is-3]]
   (views-core/footer)])