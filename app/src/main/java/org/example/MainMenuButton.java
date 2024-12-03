package org.example;

import javafx.scene.control.Button;

public class MainMenuButton extends Button {

    public MainMenuButton(String text) {
        // Set the button's text
        super(text);

        this.getStyleClass().add("button");


        this.setOnMouseEntered(e -> {
            this.getStyleClass().add("button-hover");
            this.getStyleClass().remove("button");
        });
        this.setOnMouseExited(e -> {
            this.getStyleClass().add("button");
            this.getStyleClass().remove("button-hover");
        });
    }
}
