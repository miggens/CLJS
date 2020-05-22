(ns miggens-frontend.views.blog
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
     [:h1.is-size-1.page-header "Soup Sandwiches"]]
    [:div.column.is-3.blog-side-list
     (let [content-metadata-list @(rf/subscribe [::subs/content-metadata])]
       (for [meta content-metadata-list]
         (let [title (:title meta)
               date (:createdOn meta)
               js-date (js/Date. date)
               display-date (str (+ 1 (.getMonth js-date)) "/" (.getDate js-date) "/" (.getFullYear js-date))
               snippet (util-strs/parse-paragraph-links (:snippet meta))
               image-links-list (:imageLinksList meta)
               tags-list (:tagsList meta)
               links-list (:links meta)]
           [:div.blog-side-item.has-text-light.has-background-dark {:key title}
            [:p.is-size-3.top-bottom title]
            [:p.is-size-6.top-bottom.date display-date]
            [:p.is-size-4.top-bottom snippet]
            [:div.tags.top-bottom
             (for [tag tags-list]
               [:span.tag.is-warning.is-medium {:key tag}
                tag])]])))]]
   (views-core/level-footer)])


