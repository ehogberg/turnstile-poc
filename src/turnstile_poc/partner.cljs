(ns turnstile-poc.partner)

(defn partner-link [partner]
  [:li.list-group-item (:partner-name partner)])

(defn partner-list [partner-list]
  [:div.card
   [:h5.card-title "Partner List"]
   [:div.card-body
    [:ul.list-group (for [partner partner-list]
           ^{:key (:partner-key partner)} [partner-link partner])]]])

(defn active-editing [partner-to-edit])

(defn editing-placeholder []
  [:div.jumbotron
   [:p.lead "Select a partner to edit their information here."]])

(defn edit-partner [{:keys [partner-to-edit]}]
  [:div.card
   [:h5.card-title "Edit Partner"]
   [:div.card-body
    (if partner-to-edit
      [active-editing partner-to-edit]
      [editing-placeholder])]])

(defn partner-main [state]
  [:main.container-fluid
   [:h1 "Turnstile : Partner Creation And Editing"]
   [:div.row
    [:div.col-sm-3 [partner-list (:partners state)]]
    [:div.col-sm [edit-partner state]]]])


