(ns weather-page.components.page
  (:require [om.core :as om]
            [om-tools.core :refer-macros [defcomponent]]
            [sablono.core :refer-macros [html]]
            [weather-page.components.conditions :refer [conditions]]
            [weather-page.routing :refer [nav!]]
            [weather-page.components.forecast-chart :refer [forecast-chart]]
            [weather-page.i18n :refer [t]]
            [cljs-time.format :as tf]
            [cljs-time.core :as t]))

(defcomponent page [{:keys [error api-error] {time :time} :conditions :as data} _owner]
  (render [_]
    (html [:div
           [:.settings-link [:a {:href "javascript:false" :onClick #(nav! "/settings")} (t :page/settings)]]
           (when error [:div error])
           (when api-error [:div api-error])
           (when time
             (list
               [:.updated-at (t :page/updated-at) (tf/unparse (tf/formatters :hour-minute) (t/to-default-time-zone time))]
               (om/build conditions (:conditions data))
               (om/build forecast-chart data)))])))
