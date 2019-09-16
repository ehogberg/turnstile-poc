(ns turnstile-poc.state
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce state (atom {:partners [{:partner-key 1
                                  :partner-name "Lending Tree"}]
                      :partner-to-edit nil}))

(defn get-state [] @state)

(comment
  state
  (swap! state assoc :partner-to-edit {})
  (swap! state assoc :partner-to-edit nil))
