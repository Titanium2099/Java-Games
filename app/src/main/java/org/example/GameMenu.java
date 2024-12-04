package org.example;

import javafx.scene.layout.HBox;
public class GameMenu extends HBox {

    public GameMenu() {
        //add spacing between cards
        this.setSpacing(20);
        GameMenuCard TicTacToe = new GameMenuCard("Tic Tac Toe", "images/TTT.png");
        GameMenuCard Connect4 = new GameMenuCard("Connect 4", "images/Connect4.png");
        TicTacToe.setOnMouseClicked(e -> {
            App.scene.setRoot(App.mainMenu);
        });
        Connect4.setOnMouseClicked(e -> {
            App.scene.setRoot(App.mainMenuC4);
        });
        this.getChildren().addAll(TicTacToe, Connect4);
        this.setStyle("-fx-alignment: center;");
}
}
