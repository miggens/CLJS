(ns miggens-frontend.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [goog.events :as gevents]
            [goog.history.EventType :as HistoryEventType]
            [secretary.core :as secretary :refer-macros [defroute]]
            [clojure.string :as cstr]
            [miggens-frontend.utils.strings :as util-strs]
            [miggens-frontend.views.core :as views]
            [miggens-frontend.views.developer :as developer]
            [miggens-frontend.views.about :as about]
            [miggens-frontend.views.blog :as blog]
            [miggens-frontend.events.core :as events]
            [miggens-frontend.subs.core :as subs])
  (:import goog.History))

(defn hook-browser-navigation! []
  (doto (History.)
        (gevents/listen
         HistoryEventType/NAVIGATE
         (fn [event]
           (secretary/dispatch! (.-token event))))
        (.setEnabled true)))

(def miggens-frontend-views
  {:home #'views/root-view
   :developer #'developer/page
   :about #'about/page
   :blog #'blog/page})

(defn rendered-page

  []
  [:div
   [(miggens-frontend-views @(rf/subscribe [::subs/rendered-page]) )]])

;; **** ROUTES  ****

(secretary/set-config! :prefix "#")

(defroute "/" {:as params}
  (println "NAVIGATE Home -> " params)
  (rf/dispatch [::events/navigate :home params]))

(defroute "/developer" {:as params}
  (println "NAVIGATE Developer -> " params)
  (rf/dispatch [::events/navigate :developer params]))

(defroute "/about" {:as params}
  (println "NAVIGATE About-> " params)
  (rf/dispatch [::events/navigate :about params]))

(defroute "/blog" {:as params}
  (println "NAVIGATE Developer -> " params)
  (rf/dispatch [::events/navigate :blog params]))

;; ******** SET UP ********

(defn mount

  []
  (r/render [#'rendered-page] (.getElementById js/document "app")))

(defn ^:after-load re-render

  []
  (println "*** Re-loading UI! ***")
  (mount))

(defn ^:export main
  "THE MAIN FUNCTION TO RUNNIT!!!"
  []
  (enable-console-print!)
  ;(println "*** Loading UI! ***")
  (rf/dispatch [::events/get-contents-metadata])
  (rf/dispatch [::events/get-content-by-title (cstr/replace util-strs/about-site-title #"\s+" "+")])
  (rf/dispatch-sync [::events/initialize-db])
  (hook-browser-navigation!)
  (mount))