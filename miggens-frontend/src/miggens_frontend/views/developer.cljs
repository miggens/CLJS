(ns miggens-frontend.views.developer
  (:require [miggens-frontend.views.core :as views-core]
            [miggens-frontend.utils.strings :as util-strs]
            [miggens-frontend.events.core :as events]
            [miggens-frontend.subs.core :as subs]))

(defn page
  ""
  []
  (let [title "Apps Projects and Proof..."]
    [:div
     (views-core/navbar)
     [:div.columns.site-background
      [:div.column.is-2]
      [:div.column.is-7
       [:h1.is-size-1.page-header title]
       [:p.is-size-4.paragraph util-strs/developer-page-purpose]
       [:div.tile.is-ancestor.base-tile
        [:div.tile.is-parent
         [:div.tile.is-child
          [:div.tile.has-background-primary.box.is-vertical
           [:p.is-size-3 [:span.icon [:i.fab.fa-java]] " Mearud"]
           [:div.tile
            [:p.is-size-5.paragraph "As far as demonstrations it doesn't get much simpler than this."]]
           [:div.tile
            [:p.is-size-5.paragraph "And this will also dwell below!?!"]]]
          [:div.tile.has-background-danger.box
           [:p.is-size-1 "TILE 2"]]]
         [:div.tile.is-parent
          [:div.tile.is-child.box
           [:p.is-size-1 "TILE 3"]]]]]]
      [:div.column.is-3]]
     (views-core/footer)]))