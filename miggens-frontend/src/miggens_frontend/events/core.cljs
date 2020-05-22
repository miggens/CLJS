(ns miggens-frontend.events.core
  (:require [re-frame.core :as rf]
            [miggens-frontend.utils.strings :as util-strs]
            [clojure.string :as cstr]
            [ajax.core :as ajax]
            [secretary.core :as secretary]
            [day8.re-frame.http-fx]))

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   {:rendered-page :home
    :display-contact-modal false}))

(rf/reg-event-db
 ::update-contact-modal-display
 (fn [db [event update]]
   (assoc db :display-contact-modal update)))

(rf/reg-event-db
 ::navigate
 (fn [db [event page]]
   (println "NAVIGATING " page " KW " (keyword? page))
   (assoc db :rendered-page page)))

(defn success-event-db
  ""
  [event-db func]
  (rf/reg-event-db event-db func)
  event-db)

(defn failure-event-db
  ""
  [event-db func]
  (rf/reg-event-db event-db func)
  event-db)

(rf/reg-event-fx
 ::post-contact
 (fn [cofx [event contact-map]]
   ;(println "POSTING " contact-map)
   {:db (:db cofx)
    :http-xhrio {:method :post
                 :uri util-strs/post-contact-uri
                 :timeout 8000
                 :params contact-map
                 :format (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [(success-event-db ::post-contact-success
                                                (fn [db [db-event cmap]]
                                                  (println "Successfully posted " cmap)
                                                  db))]
                 :on-failure [(failure-event-db ::post-contact-failure
                                                (fn [db [db-event error cmap]]
                                                  (println "Failed to post " cmap " ERROR " error)
                                                  db)) contact-map]}}))

(rf/reg-event-fx
 ::get-content-by-title
 (fn [cofx [event title]]
   {:db (assoc (:db cofx) :still-fetching-content-by-title true)
    :http-xhrio {:method :get
                 :uri (str util-strs/get-content-by-title-uri title)
                 :timeout 8000
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [(success-event-db ::get-content-by-title-success
                                                (fn [db [db-event kw-title content]]
                                                  ;(println "Content By Title Success: " (str content) " ASSOC WITH " (keyword title) "Should be " (keyword (cstr/lower-case (cstr/replace title #"\+" "-"))))
                                                  (assoc db  kw-title content))) (keyword (util-strs/uri-title-to-keyword title))]
                 :on-failure [(failure-event-db ::get-content-by-title-failure
                                                (fn [db [db-event kw error]]
                                                  (println "Content By Title Failure: " error)
                                                  (assoc db kw error))) :content-by-title-error]}}))
;db now contains 

(rf/reg-event-fx                             
 ::get-contents-metadata
 (fn [cofx [event kw]]
   {:db   (assoc (:db cofx) :still-fetching-contents-metadata true)   ;; causes the twirly-waiting-dialog to show??
    :http-xhrio {:method          :get
                 :uri             util-strs/all-contents-metadata-uri
                 :timeout         8000                                           ;; optional see API docs
                 :response-format (ajax/json-response-format {:keywords? true})  ;; IMPORTANT!: You must provide this.
                 :on-success      [(success-event-db ::get-content-metadata-success
                                                     (fn [db [db-event akw content-metadata]]
                                                       ;(println "Content-Metadata SUCCESS " content-metadata " AKW " akw)
                                                       (assoc db akw content-metadata))) :content-metadata]
                 :on-failure      [(failure-event-db ::get-content-metadata-failure
                                                     (fn [db [db-event akw error]]
                                                       (println "Content-Metadata FAILURE " error " AKW " akw)
                                                       (assoc db akw error))) :content-metadata-error]}}))