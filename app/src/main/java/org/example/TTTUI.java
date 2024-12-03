package org.example;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

// Tic Tac Toe UI
public class TTTUI extends VBox {

    private Button[][] board;
    private int[][] boardState;
    private boolean xTurn;
    private Image xImage;
    private Image oImage;
    private Image boardImage;

    private StyledText status;

    private int winner = 0;

    public TTTUI() {
        board = new Button[3][3];
        boardState = new int[3][3];
        xTurn = true;

        // Load images
        xImage = new Image(getClass().getResourceAsStream("/images/x.png"));
        oImage = new Image(getClass().getResourceAsStream("/images/o.png"));
        boardImage = new Image(getClass().getResourceAsStream("/images/board.png"));

        status = new StyledText("Player 1's turn");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(22);
        grid.setVgap(22);

        // Set the background image of the grid
        Rectangle background = new Rectangle(344, 344);
        background.setFill(new ImagePattern(boardImage));
        grid.add(background, 0, 0, 3, 3);  // Add the background image as a rectangle spanning the whole grid

        // Create the hidden buttons for the Tic Tac Toe cells
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = new Button("");
                //set an attribute so that we can identify the button in the event handler
                board[i][j].setUserData(new int[]{i, j});
                board[i][j].setPrefSize(100, 100);
                board[i][j].setOnAction(e -> handleButtonClick((Button) e.getSource()));
                //remove default button styling
                board[i][j].setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0; -fx-margin: 0; -fx-border-width: 0;");
                grid.add(board[i][j], i, j);
            }
        }

        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().addAll(status, grid);
    }

    private void handleButtonClick(Button button) {
        if(winner != 0) {
            return;
        }

        int[] indices = (int[]) button.getUserData();
        int x = indices[0];
        int y = indices[1];
        //make sure spot is empty
        if (boardState[x][y] != 0) {
            return;
        }

        //update buttonState
        boardState[x][y] = xTurn ? 1 : 2;
        ImageView imageView = new ImageView(xTurn ? xImage : oImage);
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        button.setGraphic(imageView);
        xTurn = !xTurn;

        status.setText(xTurn ? "Player 1's turn" : "Player 2's turn");

        winner = checkWin();
        if (winner != 0) {
            System.out.println("Player " + winner + " wins!");
        }
    }

    public int checkWin() { //left public as Minimax algorithm (FUTURE) is going to need to be able to check the board state
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (boardState[i][0] != 0 && boardState[i][0] == boardState[i][1] && boardState[i][0] == boardState[i][2]) {
                return boardState[i][0];
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (boardState[0][i] != 0 && boardState[0][i] == boardState[1][i] && boardState[0][i] == boardState[2][i]) {
                return boardState[0][i];
            }
        }

        // Check diagonals
        if (boardState[0][0] != 0 && boardState[0][0] == boardState[1][1] && boardState[0][0] == boardState[2][2]) {
            return boardState[0][0];
        }
        if (boardState[0][2] != 0 && boardState[0][2] == boardState[1][1] && boardState[0][2] == boardState[2][0]) {
            return boardState[0][2];
        }

        return 0;
    }
}
