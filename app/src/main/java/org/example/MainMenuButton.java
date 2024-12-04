package org.example;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainMenuButton extends Button {

    public MainMenuButton(String text) {
        super(text);

        // default button class
        this.getStyleClass().add("button");

        // Set fixed width and height (JavaFX ignores -fx-width/-fx-height in CSS)
        this.setPrefWidth(300);
        this.setPrefHeight(60);
        
        //load custom font
        Font quicksand = Font.loadFont(getClass().getResource("/fonts/Quicksand-Bold.ttf").toExternalForm(), 18);
        if (quicksand != null) {
            this.setFont(quicksand); // Apply font
        } else {
            System.out.println("Font could not be loaded!");
            // Fallback to Arial
            this.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        }
        //calculate how much space the text takes up
        double textWidth = this.getFont().getSize() * text.length();
        //set the minimum width to the text width
        this.setMinWidth(textWidth + 20);
        //weird fix to border radius glitch
        this.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            double radius = newHeight.doubleValue() / 2;
            this.setStyle(
                "-fx-border-radius: " + radius + "px; " +
                "-fx-background-radius: " + radius + "px;"
            );
        });

        //hover behavior
        this.setOnMouseEntered(e -> {
            if (!this.getStyleClass().contains("button-hovered")) {
                this.getStyleClass().add("button-hovered");
            }
            //change cursor
            App.scene.setCursor(javafx.scene.Cursor.HAND);
        });
        this.setOnMouseExited(e -> {
            this.getStyleClass().remove("button-hovered");
            //change cursor
            App.scene.setCursor(javafx.scene.Cursor.DEFAULT);
        });
    }
}
