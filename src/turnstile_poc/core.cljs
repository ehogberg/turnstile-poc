(ns ^:figwheel-hooks turnstile-poc.core
  (:require
   [goog.dom :as gdom]
   [reagent.core :as reagent]
   [turnstile-poc.partner :refer [partner-main]]
   [turnstile-poc.state :refer [state get-state]]))

(println "This text is printed from src/turnstile_poc/core.cljs. Go ahead and edit it and see reloading in action.")

(defn multiply [a b] (* a b))

(defn get-app-element []
  (gdom/getElement "app"))

(defn mount [el]
  (reagent/render-component [partner-main @state] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
