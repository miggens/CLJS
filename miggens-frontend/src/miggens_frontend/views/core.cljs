(ns miggens-frontend.views.core
  (:require [goog.string :as gstring]))


(defn hero-section
  ""
  [title subtitle]
  [:section.hero.is-fullheight.hero-section
   [:div.hero-body
    [:div.container.has-text-right.has-text-light
     [:p.is-size-1 title]
     [:p.is-size-5 subtitle]]]])

(defn navbar
  ""
  []
  [:nav.navbar
   [:div.navbar-brand
    [:a.navbar-item {:href "/"} [:span.icon.is-large [:i.fas.fa-2x.fa-caret-up]]]]
   [:div.navbar-menu
    [:div.navbar-start
     [:a.navbar-item.is-size-5.has-text-weight.light {:href "#/about"} "About"]
     [:a.navbar-item.is-size-5.has-text-weight.light {:href "#/blog"} "Blog"]]
    [:div.navbar-end
     [:a.navbar-item {:href "http://admin.miggens.com"} [:span.icon [:i.fas.fa-2x.fa-caret-down]]]]]])


(defn text-section
  ""
  [title subtitle]
  [:section.section
   [:div.container
    [:h1.title title]
    [:br]
    [:h2.subtitle subtitle]
    [:p "Purpose:"]]])

(defn title-and-content
  ""
  [title content]
  [:div
    [:strong title]
    [:br]
    [:p content]])

(defn footer
  ""
  []
  [:footer.footer
   [:div.columns
    [:div.column.is-4
     [:p.has-text-left.has-text-white-ter.has-text-weight-light.is-size-5
      (gstring/unescapeEntities "&#x24B8;")
      "2020 miggens.com"]]
    [:div.column.is-4]
    [:div.column.is-4
     [:p.has-text-right.has-text-white-ter.has-text-weight-light.is-size-5 "Built with"
      [:a {:href "https://github.com/day8/re-frame"} 
       [:img.left-right-15 {:src "https://github.com/day8/re-frame/blob/master/docs/images/logo/old/re-frame_128w.png?raw=true"}]] 
      "on"
      [:a {:href "https://aws.amazon.com/"}
       [:span.icon.left-right-15.is-large [:i.fa-2x.fab.fa-aws]]]]]]])

(defn level-footer
  ""
  []
  [:footer.footer
   [:nav.level
    [:div.level-left
     [:p.has-text-left.has-text-white-ter.has-text-weight-light.is-size-5
      (gstring/unescapeEntities "&#x24B8;")
      "2020 miggens.com"]]
    [:div.level-right
     [:p.has-text-right.has-text-white-ter.has-text-weight-light.is-size-5 "Built with"
      [:a {:href "https://github.com/day8/re-frame"}
       [:img.left-right-15 {:src "https://github.com/day8/re-frame/blob/master/docs/images/logo/old/re-frame_128w.png?raw=true"}]]
      "on"
      [:a {:href "https://aws.amazon.com/"}
       [:span.icon.left-right-15.is-large [:i.fa-2x.fab.fa-aws]]]]]]])

;[:img.is-2by1 {:src "https://github.com/day8/re-frame/blob/master/docs/images/logo/old/re-frame_128w.png?raw=true"}]

(defn root-view
  ""
  []
  [:div
   (navbar)
   (hero-section "Miggens" "My Developer Site")
   (level-footer)
   ;(footer)
   ])