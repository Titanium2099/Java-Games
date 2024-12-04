package org.example;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Connect4 extends VBox {
    private final int[][] board = new int[6][7]; // 6 rows, 7 columns
    private int currentPlayer = 1; // 1 for player 1, 2 for player 2
    private int currentChip = -100;
    private Boolean overrideLeaveColumn = false;

    private StyledText status;

    /*
     * gameModes are as follows:
     * 0: Player vs Player
     * 1: Player vs AI (Easy)
     * 2: Player vs AI (Medium)
     * 3: Player vs AI (Hard)
     * 4: Player vs AI (Impossible)
     */
    private int gameMode;
    public Connect4(int gameMode) {
        this.gameMode = gameMode;
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
            VBox vBox = connect4Column(i);
            hBox.getChildren().add(vBox);
        }
        status = new StyledText("Player 1's turn");
        //add buttom margin to status
        VBox.setMargin(status, new Insets(0, 0, 20, 0));
        getChildren().add(status);
        getChildren().add(hBox);
        //end

    }

    private VBox connect4Column(int index) {
        VBox vBox = new VBox();
        vBox.setPrefWidth(60);
        vBox.setPrefHeight(420);
        vBox.setStyle("-fx-background-radius: 30;");
        //add attribute index to vBox
        vBox.setUserData(index);
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
            if (gameMode != 0 && currentPlayer == 2) return; 
            board[currentChip][((int) vBox.getUserData())] = currentPlayer;

            //print board for debugging
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 7; col++) {
                    System.out.print(board[row][col] + " ");
                }
                System.out.println("");
            }
            System.err.println("_________ move made by player " + currentPlayer + " in column " + ((int) vBox.getUserData()) + " _________");
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
            status.setText(currentPlayer == 1 ? "Player 1's turn" : "Player 2's turn");
            overrideLeaveColumn = true;
            int winner = checkWinner();
            if (winner == 1) {
                addConfetti();
                TPopup("Player 1 wins!", "Main Menu", () -> App.scene.setRoot(App.mainMenu));
            } else if (winner == 2) {
                addConfetti();
                TPopup("Player 2 wins!", "Main Menu", () -> App.scene.setRoot(App.mainMenu));
            } else if (winner == 3) {
                addConfetti();
                TPopup("It's a draw!", "Main Menu", () -> App.scene.setRoot(App.mainMenu));
            }

            if(gameMode != 0 && currentPlayer == 2) {
                AI_communicator();
            }
        });
        return vBox;
    }

    private int checkWinner() {
        // Check horizontal win
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] != 0 && board[row][col] == board[row][col + 1] 
                    && board[row][col] == board[row][col + 2] && board[row][col] == board[row][col + 3]) {
                    return board[row][col];  // Return the player number (1 or 2)
                }
            }
        }
    
        // Check vertical win
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 3; row++) {
                if (board[row][col] != 0 && board[row][col] == board[row + 1][col]
                    && board[row][col] == board[row + 2][col] && board[row][col] == board[row + 3][col]) {
                    return board[row][col];  // Return the player number (1 or 2)
                }
            }
        }
    
        // Check diagonal (top-left to bottom-right) win
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] != 0 && board[row][col] == board[row + 1][col + 1]
                    && board[row][col] == board[row + 2][col + 2] && board[row][col] == board[row + 3][col + 3]) {
                    return board[row][col];  // Return the player number (1 or 2)
                }
            }
        }
    
        // Check diagonal (top-right to bottom-left) win
        for (int row = 0; row < 3; row++) {
            for (int col = 3; col < 7; col++) {
                if (board[row][col] != 0 && board[row][col] == board[row + 1][col - 1]
                    && board[row][col] == board[row + 2][col - 2] && board[row][col] == board[row + 3][col - 3]) {
                    return board[row][col];  // Return the player number (1 or 2)
                }
            }
        }
        
        //check for draw
        boolean isDraw = true;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (board[row][col] == 0) {
                    isDraw = false;
                    break;
                }
            }
        }
        return isDraw ? 3 : 0;  // Return 3 for draw, 0 for no winner
    }    

    private void addConfetti() {
        //load image "confetti.gif" and display it on the screen using javafx.scene.image.ImageView
        Image confettiImage = new Image(getClass().getResourceAsStream("/images/confetti.gif"));
        ImageView confettiImageView = new ImageView(confettiImage);
        confettiImageView.setFitWidth(App.scene.getWidth());
        confettiImageView.setFitHeight(App.scene.getHeight());
        //make position absolute
        confettiImageView.setManaged(false);
        this.getChildren().add(confettiImageView);
        //destroy the confetti after 2.49 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(2.49));
        pause.setOnFinished(e -> this.getChildren().remove(confettiImageView));
        pause.play();
    }

    private void TPopup(String text, String buttonText, Runnable onButtonClick) {
        Rectangle background = new Rectangle(App.scene.getWidth(), App.scene.getHeight());
        background.setStyle("-fx-fill: rgba(0, 0, 0, 0.5);");
        background.setManaged(false);
        Rectangle textBox = new Rectangle(250, 150);
        textBox.setArcWidth(30);
        textBox.setArcHeight(30);
        textBox.setStyle("-fx-fill: white;");
        StyledText winMessage = new StyledText(text);
        MainMenuButton mainMenuButton = new MainMenuButton(buttonText);
        mainMenuButton.setStyle("-fx-padding: 10;");
        mainMenuButton.setOnAction(event -> onButtonClick.run());

        VBox popupContent = new VBox(20);
        popupContent.setAlignment(Pos.CENTER);
        popupContent.getChildren().addAll(winMessage, mainMenuButton);
        StackPane popup = new StackPane();
        popup.getChildren().addAll(textBox, popupContent);
        popup.setMaxWidth(textBox.getWidth());
        popup.setMaxHeight(textBox.getHeight());
        popup.setManaged(false);
        popup.setLayoutX((App.scene.getWidth() / 2));
        popup.setLayoutY((App.scene.getHeight() / 2));
        this.getChildren().addAll(background, popup);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), background);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        fadeIn.play();
    }
    
    private void AI_communicator() {
        // Get the best column from the Minimax algorithm for AI (Player 2)
        int bestColumn = Minimax_C4_Algorithm.findBestMove(board);
    
        // Find the first empty row in that column
        int rowToPlace = -1;
        for (int row = 5; row >= 0; row--) {
            if (board[row][bestColumn] == 0) {
                rowToPlace = row;
                break;
            }
        }
    
        // If the column is full, there's nothing to do (this should be rare)
        if (rowToPlace == -1) {
            TPopup("The AI tried to place a chip in a full column! (this should not happen)", "Main Menu", () -> App.scene.setRoot(App.mainMenu));
            return;
        }
    
        // Update the board with the AI's move (Player 2)
        board[rowToPlace][bestColumn] = 2;
    
        // Update the visual board (animate the chip falling)
        VBox columnVBox = (VBox) ((HBox) getChildren().get(1)).getChildren().get(bestColumn); // Get the VBox for the column
        Circle targetCircle = (Circle) columnVBox.getChildren().get(rowToPlace); // Get the target Circle
        targetCircle.setFill(Color.web("#f4d35e")); // Set color for AI's chip
    
        // Optionally, add fade or fall animation for the chip
        FadeTransition fade = new FadeTransition(Duration.seconds(1), targetCircle);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setCycleCount(1);
        fade.play();
    
        // Switch the current player to Player 1
        currentPlayer = 1;
        status.setText("Player 1's turn");
    
        // Check if the AI's move resulted in a win
        int winner = checkWinner();
        if (winner == 2) {
            addConfetti();
            TPopup("Player 2 wins!", "Main Menu", () -> App.scene.setRoot(App.mainMenu));
        } else if (winner == 3) {
            addConfetti();
            TPopup("It's a draw!", "Main Menu", () -> App.scene.setRoot(App.mainMenu));
        }
    }
    
}
