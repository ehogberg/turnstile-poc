(ns turnstile-poc.state
  (:require [reagent.core :as reagent]))


;; The core atom used to track partner state.  We strongly prefer constraining
;; access to this via use of the various functions below but also resign ourselves
;; to the reality that someone can quite easily figure out a way around this.  But
;; try to not be that person, please...
(defonce ^:private state (reagent/atom {:partners {"fbc23f6d-8e45-4222-9e3a-b4b237367bf0"
                                                   {:partner-name     "LendingTree"
                                                    :partner-api-key  "995b7c5b-d98e-49e0-8af9-b78a3a4c63d4"}}
                                        :partner-to-edit nil}))


(defn get-turnstile-data
  "Returns the map of data stored in the state atom."
  []
  @state)


(defn add-partner
  "Adds a new partner to the partner list, the equivalent of
   calling new-partner, plus some editing, then save-partner-changes.
   The turbo path to adding someone quickly to the partner list, mostly
   useful for building new partners via the REPL."
  [partner-name]
  (swap! state update-in [:partners]
         assoc (random-uuid) {:partner-name partner-name
                              :partner-api-key (random-uuid)}))


(defn delete-partner
  "Removes a partner from the active partner list."
  [partner-id]
  (swap! state update-in [:partners] dissoc partner-id))


(defn new-partner
  "Creates an empty partner info structure and begins an editing session
   using it as the template for a brand new partner."
  []
  (swap! state assoc :partner-to-edit {:partner-name ""
                                       :partner-api-key (random-uuid)}))


(defn save-partner-changes
  "Persists the supplied partner data to the state atom.  The partner-id
   parameter will be nil if this partner is brand new and never saved before.
   In this case, a new UUID key is allocated and used for the persistence
   swap."
  [partner-id partner]
  (swap! state update-in [:partners]
         assoc (or partner-id (random-uuid)) partner))


(defn start-editing
  "Given a partner ID, locates the appropriate partner information in the
   state atom, then launches an editing session using it. We embed the
   ID in the partner data as a convenience to the edit form renderer,
   allowing it to easily locate and supply the ID to the update call."
  [partner-id]
  (let [partner-to-edit (-> (get-in @state [:partners partner-id])
                            (assoc :partner-id partner-id))]
    (swap! state assoc :partner-to-edit partner-to-edit)))


(defn cancel-editing
  "Cancels an in-progress editing session."
  []
  (swap! state assoc :partner-to-edit nil))


(defn is-editing?
  "Utility predicate to check whether an editing session is currently active."
  []
  (some? (:partner-to-edit @state)))


(comment
  (start-editing "fbc23f6d-8e45-4222-9e3a-b4b237367bf0")
  (cancel-editing)

  (add-partner "Lending Club")
  (add-partner "Some Other Partner")
  )
