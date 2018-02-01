(ns tictactoe.core
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(println "This text is printed from src/tictactoe/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload
(def board-dimension 3)

(defn game-board [dimension]
  (vec (repeat dimension (vec (repeat dimension :empty)))))

(defonce app-state
  (atom
    {:page
      {:title "TicTacToe in Reagent"
       :message "Shall we play a game?"}
     :board (game-board board-dimension)}))

 ; (if (= :empty (get-in @app-state [:board y-cell x-cell]))
 ;        "#8defd2"   ;turquoise if empty
 ;        "#ef8daa")  ;pink if clicked

(defn cell-empty [x-cell y-cell]
  ^{:key (str x-cell y-cell)}
  [:rect {:width 0.9
         :height 0.9
         :fill "lightgrey"
         :x x-cell
         :y y-cell
         :on-click
          (fn rectangle-click [e]
            (println "Cell at COL" x-cell "ROW" y-cell "was clicked!")
            (println
              (swap! app-state assoc-in [:board y-cell x-cell] :nought)))}])

(defn cell-cross [x-cell y-cell]
  ^{:key (str x-cell y-cell)}
  [:g {:stroke "#ef8daa"
       :stroke-width 0.3
       :stroke-linecap "round"
       :transform
       (str "translate(" (+ 0.42 x-cell) "," (+ 0.42 y-cell) ") "
            "scale(0.3)")}
    [:line {:x1 -1 :y1 -1 :x2 1 :y2 1}]
    [:line {:x1 1 :y1 -1 :x2 -1 :y2 1}]])

(defn cell-nought [x-cell y-cell]
  ^{:key (str x-cell y-cell)}
  [:circle {:r 0.36
            :fill "white"
            :stroke "#8defd2"
            :stroke-width 0.1
            :cx (+ 0.42 x-cell)
            :cy (+ 0.42 y-cell)
            :on-click
             (fn rectangle-click [e]
               (println "Cell at COL" x-cell "ROW" y-cell "was clicked!")
               (println
                 (swap! app-state assoc-in [:board y-cell x-cell] :cross)))}])

(defn tictactoe-game []
  [:div
    [:h1 (get-in @app-state [:page :title])]
    [:p (get-in @app-state [:page :message])]
    [:button {:on-click (fn new-game-click [e]
                (swap! app-state assoc :board (game-board board-dimension)))}
                "Start a new game"]
    [:center
      [:svg {:view-box "0 0 3 3"
             :width 500
             :height 500}
        (for [x-cell (range (count (:board @app-state)))
              y-cell (range (count (:board @app-state)))]

              (case (get-in @app-state [:board y-cell x-cell])
                  :empty [cell-empty x-cell y-cell]
                  :cross [cell-cross x-cell y-cell]
                  :nought [cell-nought x-cell y-cell]
                  ))]]])


(reagent/render-component [tictactoe-game]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
