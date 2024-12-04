package org.example;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Connect4 extends VBox {
    private final int[][] board = new int[6][7]; // 6 rows, 7 columns
    private int currentPlayer = 1; // 1 for player 1, 2 for player 2
    private int currentChip = -100;
    private Boolean overrideLeaveColumn = false;
    public Connect4() {
        //draw the connect 4 board
        setAlignment(Pos.CENTER);
        setPrefSize(360, 360);
        setMaxSize(360, 360);
        setMinSize(360, 360);
        setStyle("-fx-background-color:#eeeeee;");
        HBox hBox = new HBox();
        hBox.setPrefSize(456, 3960);
        hBox.setMaxSize(456, 396);
        hBox.setMinSize(456, 396);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setStyle("-fx-background-color:#00a5db; -fx-background-radius: 40; -fx-border-color: #0082ac; -fx-border-width: 8; -fx-border-radius: 30;");
        for (int i = 0; i < 7; i++) {
            VBox vBox = connect4Column();
            hBox.getChildren().add(vBox);
        }
        getChildren().add(hBox);
        //end

    }

    private VBox connect4Column() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(60);
        vBox.setPrefHeight(420);
        vBox.setStyle("-fx-background-radius: 30;");
        for (int i = 0; i < 6; i++) {
            Circle circle = new Circle(20, Color.web("#eeeeee"));
            VBox.setMargin(circle, new Insets(10)); // Add margin to circles
            vBox.getChildren().add(circle);
        }

        // List to hold FadeTransition objects for each circle
        List<FadeTransition> fadeTransitions = new ArrayList<>();

        vBox.setOnMouseEntered(e -> {
            overrideLeaveColumn = false;
            vBox.setStyle("-fx-background-color: #017399; -fx-background-radius: 20;");

            // Loop through the circles
            for (int i = 5; i >= 0; i--) {
                Circle circle = (Circle) vBox.getChildren().get(i);

                // Check if the circle is white
                if (circle.getFill().equals(Color.web("#eeeeee"))) {
                    currentChip = i;
                    
                    // Change the color based on the current player
                    if (currentPlayer == 1) circle.setFill(Color.web("#ff6542"));
                    else circle.setFill(Color.web("#f4d35e"));
                    
                    // Create a FadeTransition for the circle
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), circle);
                    fadeTransition.setFromValue(0);  // Start opacity at 0
                    fadeTransition.setToValue(1);    // End opacity at 1
                    fadeTransition.setCycleCount(FadeTransition.INDEFINITE);  // Loop the animation
                    fadeTransition.setAutoReverse(true);  // Make the fade transition back and forth
                    fadeTransition.play();  // Start the animation

                    // Store the FadeTransition in the list
                    fadeTransitions.add(fadeTransition);

                    break;
                }
            }
        });

        vBox.setOnMouseExited(e -> {
            vBox.setStyle("");  // Reset the background color and style
            if (overrideLeaveColumn) {
                overrideLeaveColumn = false;
                return;
            }
            if(currentChip == -100) return;  // Do nothing if the current chip is not set
        
            // Loop through the circles to find the one that was changed
            int i = currentChip;
            Circle circle = (Circle) vBox.getChildren().get(i);
            circle.setFill(Color.web("#eeeeee"));
        
            // Collect transitions to remove in a separate list
            List<FadeTransition> transitionsToRemove = new ArrayList<>();
            for (FadeTransition transition : fadeTransitions) {
                if (transition.getNode() == circle) {
                    transition.stop();  // Stop the fade animation
                    transitionsToRemove.add(transition);  // Mark for removal
                }
            }
        
            // Remove the transitions after the loop
            fadeTransitions.removeAll(transitionsToRemove);
            //reset opacity
            circle.setOpacity(1);
            currentChip = -100;
        });        
        vBox.setOnMouseClicked(e -> {
            if (currentChip == -100) return;  // Do nothing if the current chip is not set
            board[currentChip][currentPlayer] = 1;

            int i = currentChip;
            Circle circle = (Circle) vBox.getChildren().get(i);
            circle.setFill(Color.web(currentPlayer == 1 ? "#ff6542" : "#f4d35e"));
        
            // Collect transitions to remove in a separate list
            List<FadeTransition> transitionsToRemove = new ArrayList<>();
            for (FadeTransition transition : fadeTransitions) {
                if (transition.getNode() == circle) {
                    transition.stop();  // Stop the fade animation
                    transitionsToRemove.add(transition);  // Mark for removal
                }
            }
        
            // Remove the transitions after the loop
            fadeTransitions.removeAll(transitionsToRemove);
            //reset opacity
            circle.setOpacity(1);
            currentChip = -100;
            currentPlayer = currentPlayer == 1 ? 2 : 1;
            overrideLeaveColumn = true;
        });
        return vBox;
    }
}
