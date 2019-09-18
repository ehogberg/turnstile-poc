(ns turnstile-poc.partner
  (:require [reagent.core :refer [atom]]
            [turnstile-poc.state :refer [get-state-data]]))

(defn partner-link [partner]
  [:li.list-group-item (:partner-name partner)])

(defn partner-list [partner-list]
  [:div.card
   [:h5.card-title "Partner List"]
   [:div.card-body
    [:ul.list-group (for [partner partner-list]
           ^{:key (:partner-key partner)} [partner-link partner])]]])

(defn active-editing [{:keys [partner-name]}]
  (let [p (atom {:partner-name partner-name})]
    (fn []
      [:form {:on-submit (fn [e]
                           (.preventDefault e)
                           (js/console.log @p))}
       [:div.form-group
        [:label {:for "partnerName"} "Partner Name"]
        [:input#partnerName.form-control {:type "text" :default-value partner-name
                                          :on-change (fn [e]
                                                       (swap! p
                                                              assoc
                                                              :partner-name
                                                              (-> e
                                                                  .-target
                                                                  .-value)))}]]
       [:button.btn.btn-primary "Save Changes"]])))

(defn editing-placeholder []
  [:div.jumbotron
   [:p.lead "Select a partner to edit their information here."]])

(defn edit-partner [partner-to-edit]
  [:div.card
   [:h5.card-title "Edit Partner"]
   [:div.card-body
    (if partner-to-edit
      [active-editing partner-to-edit]
      [editing-placeholder])]])

(defn partner-main []
  (let [state-ref (get-state-data)]
    [:main.container-fluid
     [:div
      [partner-list (:partners state-ref)]
      [edit-partner (:partner-to-edit state-ref)]]]))

