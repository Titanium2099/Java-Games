package org.example;

import javafx.scene.layout.VBox;

public class MainMenu extends VBox {

    public MainMenu() {
        //Main Menu buttons
        MainMenuButton AIButton = new MainMenuButton("AI");
        MainMenuButton twoPlayerButton = new MainMenuButton("Local");
        MainMenuButton exitButton = new MainMenuButton("Exit");

        this.getChildren().addAll(AIButton, twoPlayerButton, exitButton);

        this.setSpacing(20); // Space between buttons
        this.setStyle("-fx-alignment: center;"); // Center the buttons in the VBox

        AIButton.setOnAction(e -> System.out.println("Play button clicked!"));
        twoPlayerButton.setOnAction(e -> System.out.println("Settings button clicked!"));
        exitButton.setOnAction(e -> System.exit(0));
    }
}
