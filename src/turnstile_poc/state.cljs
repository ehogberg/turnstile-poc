(ns turnstile-poc.state
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce ^:private state (atom {:partners [{:partner-key (random-uuid)
                                            :partner-name "Lending Tree"}]
                                :partner-to-edit nil}))

(defn get-state-data [] @state)

(defn add-partner [partner-name]
  (swap! state update-in [:partners] conj {:partner-name partner-name
                                           :partner-key  (random-uuid)}))

(defn edit-partner [partner]
  (swap! state assoc :partner-to-edit partner))


(comment
  (edit-partner {:partner-key (random-uuid)
                 :partner-name "Lending Tree"})
  (edit-partner nil)

  (add-partner "Lending Club")
  (add-partner "Some Other Partner")
  )



