(ns turnstile-poc.partner
  (:require [reagent.core :as reagent]
            [turnstile-poc.state :refer [get-turnstile-data
                                         save-partner-changes
                                         start-editing
                                         cancel-editing
                                         new-partner
                                         is-editing?
                                         delete-partner]]
            [turnstile-poc.util :refer [update-form-atom]]))


(defn partner-link
  "Renders a single item representing a partner
   in a partner list."
  [partner-id {:keys [partner-name] :as partner}]
  [:li.list-group-item
   [:div.float-left
    partner-name]
   (if-not (is-editing?)
     [:div.float-right
      [:i.fas.fa-edit.mr-2.text-primary
       {:on-click #(start-editing partner-id)}]
      [:i.fas.fa-trash.text-danger
       {:on-click (fn [e]
                    (delete-partner partner-id)
                    (cancel-editing))}]])])


(defn partner-list
  "Renders a list of known partners.  The majority of
   the heavy lifting is done by partner-link."
  [partner-list]
  [:div.card
   [:div.card-title.ml-2
    [:h5.float-left.mr-2 "Partner List"]
    (if-not (is-editing?)
      [:i.fas.fa-plus.text-primary
       {:on-click new-partner}])]
   [:div.card-body
    [:ul.list-group
     (for [[partner-id partner] partner-list]
       ^{:key partner-id} [partner-link partner-id partner])]]])


(defn active-editing
  "Draw the form used to edit partner info."
  [{:keys [partner-id partner-name partner-api-key] :as partner}]
  (reagent/with-let [p (reagent/atom partner)]
    [:form {:on-submit (fn [e]
                         (.preventDefault e)
                         (save-partner-changes partner-id @p)
                         (cancel-editing))}
     [:div.form-group
      [:label.text-primary {:for "partnerName"} "Partner Name"]
      [:input#partnerName.form-control
       {:type "text" :default-value partner-name
        :on-change (partial update-form-atom p :partner-name)}]]
     [:div.form-group
      [:label.text-primary {:for "partnerAPIKey"} "Partner API Key"]
      [:input#partnerAPIKey.form-control
       {:type "text" :default-value partner-api-key
        :on-change (partial update-form-atom p :partner-api-key)}]]
     [:button.btn.btn-primary.mr-1
      "Save Changes"]
     [:button.btn.btn-secondary
      {:type "button"
       :on-click cancel-editing}
      "Cancel"]]))


(defn editing-placeholder
  "Renders boilerplate text displayed in the editing pane when
   no partner is currently selected for editing."
  []
  [:div.jumbotron
   [:p.lead "Select a partner to edit their information here."]])


(defn edit-partner
  "Draws the right-side edit panel.  If a new partner is being
   created or an existing one chosen for editing, the editing form
   is displayed, otherwise the placeholder text is rendered."
  [partner-to-edit]
  [:div.card
   [:h5.card-title.ml-2 "Edit Partner"]
   [:div.card-body
    (if partner-to-edit
      [active-editing partner-to-edit]
      [editing-placeholder])]])

(defn partner-header
  "Renders the page header."
  []
  [:nav.navbar.navbar-expand-lg.navbar-dark.bg-dark
   [:a.navbar-brand {:href "#"}
    [:h3 "Turnstile POC"]]
   [:div.collapse.navbar-collapse]])


(defn partner-main
  "Top-level page builder; accesses Turnstile data to
   reactively build a list of partners and a conditionally
   displayed/hidden editing form."
  []
  (let [{:keys [partners partner-to-edit]} (get-turnstile-data)]
    [:main.container-fluid
     [partner-header]
     [:div.row.mt-2
      [:div.col-sm
       [partner-list partners]]
      [:div.col-sm
       [edit-partner partner-to-edit]]]]))
