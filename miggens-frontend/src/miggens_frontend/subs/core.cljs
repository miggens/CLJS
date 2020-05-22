(ns miggens-frontend.subs.core
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::rendered-page
 (fn [db]
   (:rendered-page db)))

(rf/reg-sub
 ::display-contact-modal
 (fn [db]
   (:display-contact-modal db)))

(rf/reg-sub
 ::content-metadata
 (fn [db]
   (:content-metadata db)))

(rf/reg-sub
 ::about-site-content
 (fn [db]
   (:about-this-site db)))
