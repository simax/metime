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

(defroute root-route "/" []
          (dispatch [:set-active-view :employees :employees]))

(defroute employees-route "/employees" []
          (dispatch [:set-active-view :employees :employees]))

(defroute tables-route "/tables" []
          (dispatch [:set-active-view :tables :tables]))

(defroute calendar-route "/calendar" []
          (dispatch [:set-active-view :calendar :calendar]))

(defroute file-manager-route "/file-manager" []
          (dispatch [:set-active-view :file-manager :file-manager]))

(defroute user-route "/user" []
          (dispatch [:set-active-view :user :user]))

(defroute login-route "/login" []
          (dispatch [:set-active-view :login :login]))

(defroute employee-add-route "/employees/add" []
          (dispatch [:employee-add]))

(defroute employee-route "/employee/:id" [id]
          (dispatch [:employee-edit id]))

(defroute "*" []
          (dispatch [:set-active-view nil :not-found]))