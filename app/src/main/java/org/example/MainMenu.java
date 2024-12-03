package org.example;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class MainMenu extends VBox {

    public MainMenu() {
        //Main Menu buttons
        MainMenuButton AIButton = new MainMenuButton("AI");
        MainMenuButton twoPlayerButton = new MainMenuButton("Local");
        MainMenuButton exitButton = new MainMenuButton("Exit");

        this.getChildren().addAll(AIButton, twoPlayerButton, exitButton);

        this.setSpacing(20); // Space between buttons
        this.setStyle("-fx-alignment: center;"); // Center the buttons in the VBox

        AIButton.setOnAction(e -> {
            //delete current scene & create 5 new buttons
            this.getChildren().clear();
            MainMenuButton easyButton = new MainMenuButton("Easy");
            MainMenuButton mediumButton = new MainMenuButton("Medium");
            MainMenuButton hardButton = new MainMenuButton("Hard");
            MainMenuButton impossibleButton = new MainMenuButton("Impossible");
            MainMenuButton backButton = new MainMenuButton("Back");
            this.getChildren().addAll(easyButton, mediumButton, hardButton, impossibleButton, backButton);
            backButton.setOnAction(e2 -> {
                //delete current scene & create 3 main menu buttons
                this.getChildren().clear();
                this.getChildren().addAll(AIButton, twoPlayerButton, exitButton);
            });
        });
        twoPlayerButton.setOnAction(e -> {
            TTTUI tttui = new TTTUI();
            Scene gameScene = new Scene(tttui, 1120, 630);
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(gameScene);
        });
        exitButton.setOnAction(e -> System.exit(0));
    }
}
