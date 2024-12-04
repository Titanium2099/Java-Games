package org.example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
public class MainMenuC4 extends VBox {

    MainMenuButton exitButton;
    public MainMenuC4() {
        Image backIcon = new Image(getClass().getResourceAsStream("/images/backarrow.png"));
        ImageView backIconView = new ImageView(backIcon);
        backIconView.setFitWidth(20);
        backIconView.setFitHeight(20);

        //Main Menu buttons
        MainMenuButton AIButton = new MainMenuButton("AI");
        MainMenuButton twoPlayerButton = new MainMenuButton("Local");
        exitButton = new MainMenuButton("Exit");
        exitButton.setGraphic(backIconView);

        this.getChildren().addAll(AIButton, twoPlayerButton, exitButton);

        this.setSpacing(20); // Space between buttons
        this.setStyle("-fx-alignment: center;"); // Center the buttons in the VBox

        AIButton.setOnAction(e -> {
            //delete current scene & create 5 new buttons
            this.getChildren().clear();
            MainMenuButton easyButton = new MainMenuButton("Easy");
            MainMenuButton mediumButton = new MainMenuButton("Medium");
            MainMenuButton hardButton = new MainMenuButton("Hard");
            MainMenuButton backButton = new MainMenuButton("Back");
            backButton.setGraphic(backIconView);

            this.getChildren().addAll(easyButton, mediumButton, hardButton, backButton);
            backButton.setOnAction(e2 -> {
                //delete current scene & create 3 main menu buttons
                this.getChildren().clear();
                //fix to a weird bug where the button loses its graphic but JavaFX still thinks it has one
                exitButton = new MainMenuButton("Exit");
                exitButton.setGraphic(backIconView);
                exitButton.setOnAction(e3 -> {
                    App.scene.setRoot(App.MainGameMenu);
                });
                this.getChildren().addAll(AIButton, twoPlayerButton, exitButton);
            });
            easyButton.setOnAction(e2 -> {
                Connect4 C4 = new Connect4(1);
                App.scene.setRoot(C4);
            });
            mediumButton.setOnAction(e2 -> {
                Connect4 C4 = new Connect4(2);
                App.scene.setRoot(C4);
            });
            hardButton.setOnAction(e2 -> {
                Connect4 C4 = new Connect4(3);
                App.scene.setRoot(C4);
            });
        });
        twoPlayerButton.setOnAction(e -> {
            Connect4 C4 = new Connect4(0);
            App.scene.setRoot(C4);
        });
        exitButton.setOnAction(e3 -> {
            App.scene.setRoot(App.MainGameMenu);
        });
    }
}
