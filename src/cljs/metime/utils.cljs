(ns metime.utils
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [cljs-hash.md5 :as hashgen]
            [re-frame.core :refer [register-handler
                                   path
                                   register-sub
                                   dispatch
                                   subscribe]]
            [goog.net.cookies]
            [cljs.reader :as reader]
            [cljs.core.async :refer [put! take! <! >! chan timeout]]
            [cljs-http.client :as http]))

(defn gravatar [data]
  (let [email-address (or (:gravatar-email data) "")
        size (or (:gravatar-size data) 100)]
    [:img.gravatar.img-circle {:src (str "http://www.gravatar.com/avatar/" (hashgen/md5 email-address) "?size=" size "&r=PG&d=mm")}]))

;(defn set-hash! [loc]
;  "Set the hash portion of the url in the address bar.
;  e.g. (set-hash! '/dip') => http://localhost:3000/#/dip"
;  (set! (.-hash js/window.location) loc))

;(defn get-current-location []
;  "Get the url in the address bar, including the hash portion."
;  (subs (.-hash js/window.location) 0))

(defn api [db endpoint]
  (str (:api-root-url db) endpoint))

(defn isNaN [node]
  (and (= (.call js/toString node) (str "[object Number]"))
       (js/eval (str node " != +" node))))

(defn parse-int [s]
  "Parse the string for an integer"
  (if (or (nil? s) (empty? s))
    0
    (try
      (if (isNaN (js/parseInt s)) 0 (js/parseInt s))
      (catch js/Error _ 0)
      (catch js/Object _ 0)
      )))

(defn input-value [component] (-> component .-target .-value))

(defn get-cookie [k]
  "Returns the cookie after parsing it with cljs.reader/read-string."
  (reader/read-string (or (.get goog.net.cookies (name k)) "nil")))

(defn set-cookie! [k content & [{:keys [max-age path domain secure?]} :as opts]]
  "Stores the cookie value using pr-str."
  (let [k (name k)
        content (pr-str content)]
    (if-not opts
      (.set goog.net.cookies k content)
      (.set goog.net.cookies k content (or max-age -1) path domain (boolean secure?)))))

(defn remove-cookie! [k]
  (.remove goog.net.cookies (name k)))

(defn build-authorization-header [token]
  {:headers {"authorization" (str "Token " token)}})

(defn call-secure-url [verb url token]
  (case verb
    :GET (http/get url (build-authorization-header token))
    :DELETE (http/delete url (build-authorization-header token))))

;; TODO: Need to deal with :POST :PUT etc and form data

(defn call-secure-api [verb url token {:keys [valid-token-handler invalid-token-handler response-keys]}]
  "Make a secure url call (GET or DELETE) with authorization header.
  Dispatch redirect to login if unauthorized."
  (go
    ;; The following go block will "park" until the http request returns data
    (let [response (<! (call-secure-url verb url token))
          status (:status response)]
      (if (not= status 200)
        (dispatch [invalid-token-handler])
        (dispatch [valid-token-handler (get-in response response-keys)])))))

(defn send-data-to-secure-url [verb url token data]
  (case verb
    :POST (http/post url {:form-params data})               ; (build-authorization-header token)
    :PUT (http/put url (build-authorization-header token) data)))

(defn send-data-to-secure-api [verb url token data {:keys [valid-token-handler invalid-token-handler response-keys]}]
  "Make a secure url call with authorization header.
   Dispatch redirect to login if unauthorized."
  (go
    ;; The following go block will "park" until the http request returns data
    (let [response (<! (send-data-to-secure-url verb url token data))
          status (:status response)]
      (if (not= status 201)
        (dispatch [invalid-token-handler])
        (dispatch [valid-token-handler])))))  ; (get-in response response-keys)

; Dispatch on the 1st parameter, namely, verb
(defmulti call-api
          "GET or DELETE to secure URL with token and
           response map {:valid-dispatch and :invalid-dispatch handlers
           and :response keys of fetched data to display}"
          identity)
(defmethod call-api :GET [verb url token response-map] (call-secure-api verb url token response-map))
(defmethod call-api :DELETE [verb url token response-map] (call-secure-api verb url token response-map))

; Dispatch on the 1st parameter, namely, verb
(defmulti send-data-to-api
          "POST or PUT to secure URL with token,
           data and response map {:valid-dispatch and :invalid-dispatch handlers}" identity)
(defmethod send-data-to-api :POST [verb url token data response-map] (send-data-to-secure-api verb url token data response-map))
(defmethod send-data-to-api :PUT [verb url token data response-map] (send-data-to-secure-api verb url token data response-map))
