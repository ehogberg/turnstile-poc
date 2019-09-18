(ns turnstile-poc.state
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce ^:private state (atom {:partners {"fbc23f6d-8e45-4222-9e3a-b4b237367bf0"
                                           {:partner-name     "LendingTree"
                                            :partner-api-key  "995b7c5b-d98e-49e0-8af9-b78a3a4c63d4"}}
                                :partner-to-edit nil}))

(defn get-turnstile-data [] @state)

(defn add-partner [partner-name]
  (let [partner-id (random-uuid)]
    (swap! state update-in [:partners]
           assoc partner-id {:partner-name partner-name
                             :partner-api-key (random-uuid)})))

(defn save-partner-changes [partner-id partner]
  (swap! state update-in [:partners] assoc partner-id partner))

(defn start-editing [partner-id]
  (let [partner-to-edit (-> (get-in @state [:partners partner-id])
                            (assoc :partner-id partner-id))]
    (swap! state assoc :partner-to-edit partner-to-edit)))

(defn cancel-editing [] (swap! state assoc :partner-to-edit nil))

(comment
  (start-editing "fbc23f6d-8e45-4222-9e3a-b4b237367bf0")
  (cancel-editing)

  (add-partner "Lending Club")
  (add-partner "Some Other Partner")
  )
