(ns turnstile-poc.util)

(defn update-form-atom [form field evt]
  (swap! form assoc field (-> evt
                                   .-target
                                   .-value)))
