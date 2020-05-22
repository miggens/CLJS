(ns miggens-frontend.views.core
  (:require [goog.string :as gstring]
            [re-frame.core :as rf]
            [miggens-frontend.utils.strings :as util-strs]
            [miggens-frontend.subs.core :as subs]
            [miggens-frontend.events.core :as events]))

(defn handle-contact-push
  ""
  [event]
  (let [name-elem (.getElementById js/document "c-name") 
        email-elem (.getElementById js/document "c-email")
        msg-elem (.getElementById js/document "c-msg")
        name (-> name-elem (.-value))
        email (-> email-elem (.-value))
        msg (-> msg-elem (.-value))
        contact-args {:name name :email email :msg msg}
        valid-args (util-strs/validate-contact-form contact-args)
        error-message-elem (.getElementById js/document "contact-submit-error")]
    ;(println "Gonna Validate Contact Args " contact-args " ERROR MESSAGE " error-message-elem)
    (if (nil? (first valid-args))
      (let [_ (-> name-elem (.-value) (set! ""))
            _ (-> email-elem (.-value) (set! ""))
            _ (-> msg-elem (.-value) (set! ""))
            _ (-> error-message-elem (.-innerText) (set! ""))
            delete-button-elem (.getElementById js/document "delete-button")
            _ (.click delete-button-elem)]
        (rf/dispatch [::events/post-contact contact-args]))
      (set! (.-innerText error-message-elem) 
            (str "ERROR Invalid Items: " (first valid-args)))
      )))

(defn contact-modal-active
  ""
  [update]
  [:div.modal.is-active
   [:div.modal-background]
   [:div.modal-card
    [:header.modal-card-head
     [:p.modal-card-title "Contact Developer"]
     [:button#delete-button.delete {:aria-label "close" :onClick #(rf/dispatch [::events/update-contact-modal-display (not update)])}]]
    [:section.modal-card-body
     [:section.section
      [:div.container
       [:div.field
        [:label.label "Name"]
        [:div.control
         [:input.input {:id "c-name" :type "text" :placeholder "Name"}]]]
       [:div.field
        [:label.label "Email"]
        [:div.control
         [:input.input {:id "c-email" :type "text" :placeholder "Email"}]]]
       [:div.field
        [:label.label "Message"]
        [:div.control
         [:textarea.textarea {:id "c-msg" :placeholder "Text Area"}]]]]]]
    [:footer.modal-card-foot
     [:button.button {:onClick #(handle-contact-push %1)} "Contact"]
     [:p#contact-submit-error.is-size-5.has-text-danger ""]]]])

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
  (let [display-contact-modal @(rf/subscribe [::subs/display-contact-modal])]
    [:nav.navbar
     [:div.navbar-brand
      [:a.navbar-item {:href "#/"} [:span.icon.is-large [:i.fas.fa-2x.fa-caret-up]]]]
     [:div.navbar-menu
      [:div.navbar-start
       [:a.navbar-item.is-size-5.has-text-weight.light {:href "#/developer"} "Developer"]
       [:a.navbar-item.is-size-5.has-text-weight.light {:href "#/about"} "About"]
       [:a.navbar-item.is-size-5.has-text-weight.light {:href "#/blog"} "Blog"]]
      ()
      [:div.navbar-end
       [:a.navbar-item.is-size-5.has-text-weight.light {:onClick #(rf/dispatch [::events/update-contact-modal-display (not display-contact-modal)])} "Contact"]
       [:a.navbar-item {:href "http://admin.miggens.com"} [:span.icon [:i.fas.fa-2x.fa-caret-down]]]]]
     (if display-contact-modal
      (contact-modal-active display-contact-modal)
       nil)]))


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
   ])