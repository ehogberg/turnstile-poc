(ns ^:figwheel-hooks turnstile-poc.core
  (:require
   [goog.dom :as gdom]
   [reagent.core :as reagent]
   [turnstile-poc.partner :refer [partner-main]]))


(defn get-app-element
  "Finds the root element to be used for
   reactive rendering."
  []
  (gdom/getElement "app"))


(defn mount
  "Given a root element, execute the main page
   renderer and inject the generated content into
   this element."
  [el]
  (reagent/render-component [partner-main] el))


(defn mount-app-element
  "Conditionally starts the rendering process if a root
   element has been defined."
  []
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
