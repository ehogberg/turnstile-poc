(ns turnstile-poc.partner
  (:require [reagent.core :refer [atom]]
            [turnstile-poc.state :refer [get-turnstile-data
                                         save-partner-changes
                                         start-editing
                                         cancel-editing]]
            [turnstile-poc.util :refer [update-form-atom]]))

(defn partner-link [partner-id {:keys [partner-name] :as partner}]
  [:li.list-group-item
   {:on-click (fn [e] (start-editing partner-id))}
   partner-name])

(defn partner-list [partner-list]
  [:div.card
   [:h5.card-title.ml-2 "Partner List"]
   [:div.card-body
    [:ul.list-group
     (for [[partner-id partner] partner-list]
       ^{:key partner-id} [partner-link partner-id partner])]]])

(defn active-editing [{:keys [partner-id partner-name partner-api-key]
                       :as partner}]
  (let [p (atom partner)]
    (fn []
      [:form {:on-submit (fn [e]
                           (.preventDefault e)
                           (save-partner-changes partner-id @p)
                           (cancel-editing))}
       [:div.form-group
        [:label {:for "partnerName"} "Partner Name"]
        [:input#partnerName.form-control
         {:type "text" :default-value partner-name
          :on-change (partial update-form-atom p :partner-name)}]]
       [:div.form-group
        [:label {:for "partnerAPIKey"} "Partner API Key"]
        [:input#partnerAPIKey.form-control
         {:type "text" :default-value partner-api-key
          :on-change (partial update-form-atom p :partner-api-key)}]]
       [:button.btn.btn-primary.mr-1
        "Save Changes"]
       [:button.btn.btn-secondary
        {:type "button"
         :on-click cancel-editing}
        "Cancel"]])))

(defn editing-placeholder []
  [:div.jumbotron
   [:p.lead "Select a partner to edit their information here."]])

(defn edit-partner [partner-to-edit]
  [:div.card
   [:h5.card-title.ml-2 "Edit Partner"]
   [:div.card-body
    (if partner-to-edit
      [active-editing partner-to-edit]
      [editing-placeholder])]])

(defn partner-header []
  [:nav.navbar.navbar-expand-lg.navbar-dark.bg-dark
   [:a.navbar-brand {:href "#"} "Turnstile POC"]
   [:div.collapse.navbar-collapse
    ]])

(defn partner-main []
  (let [turnstile-data (get-turnstile-data)]
    [:main.container-fluid
     [partner-header]
     [:div.row.mt-2
      [:div.col-sm
       [partner-list (:partners turnstile-data)]]
      [:div.col-sm
       [edit-partner (:partner-to-edit turnstile-data)]]]]))
