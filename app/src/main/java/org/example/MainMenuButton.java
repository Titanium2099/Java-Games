package org.example;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class MainMenuButton extends Button {

    public MainMenuButton(String text) {
        super(text);

        // default button class
        this.getStyleClass().add("button");

        // Set fixed width and height (JavaFX ignores -fx-width/-fx-height in CSS)
        this.setPrefWidth(300);
        this.setPrefHeight(60);
        
        //load custom font
        Font quicksand = Font.loadFont(getClass().getResource("/fonts/quicksand.ttf").toExternalForm(), 18);
        if (quicksand != null) {
            this.setFont(quicksand); // Apply font to the button
        } else {
            System.out.println("Font could not be loaded!");
        }
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
            this.getScene().setCursor(javafx.scene.Cursor.HAND);
        });
        this.setOnMouseExited(e -> {
            this.getStyleClass().remove("button-hovered");
            //change cursor
            this.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
        });
    }
}
