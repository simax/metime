(ns metime.routes
  (:require [metime.utils :as utils]
            [secretary.core :refer-macros [defroute]]
            [re-frame.core :refer [register-handler
                                   path
                                   dispatch
                                   dispatch-sync
                                   subscribe]]
            [secretary.core :as secretary]))

(secretary/set-config! :prefix "#")

(defroute tables-route "/tables" []
          (dispatch [:switch-route :tables :tables]))

(defroute calendar-route "/calendar" []
          (dispatch [:switch-route :calendar :calendar]))

(defroute file-manager-route "/file-manager" []
          (dispatch [:switch-route :file-manager :file-manager]))

(defroute user-route "/user" []
          (dispatch [:switch-route :user :user]))

(defroute login-route "/login" []
          (dispatch [:switch-route :login :login]))

(defroute employees-route "/employees" []
          (dispatch [:switch-route :employees :employees]))

(defroute root-route "/" []
          (utils/set-hash! "#/employees")
          (dispatch [:switch-route :employees :employees]))

(defroute employee-route "/employee/:id" [id]
          (dispatch [:employee-route-switcher :employees :employee id]))

(defroute employee-add-route "/employees/add" []
          (dispatch [:employee-route-switcher :employees :employee 0]))

(defroute "*" []
          (dispatch [:switch-route :employees :not-found]))