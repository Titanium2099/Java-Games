package org.example;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

// Tic Tac Toe UI
public class TTTUI extends VBox {
    private Button[][] board;
    private boolean xTurn;

    public TTTUI() {
        board = new Button[3][3];
        xTurn = true;

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = new Button("");
                board[i][j].setPrefSize(100, 100);
                board[i][j].setOnAction(e -> handleButtonClick((Button) e.getSource()));
                grid.add(board[i][j], i, j);
            }
        }

        Text status = new Text("X's turn");        
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().addAll(grid, status);
    }

    private void handleButtonClick(Button button) {
        if (!button.getText().isEmpty()) {
            return;
        }

        button.setText(xTurn ? "X" : "O");
        xTurn = !xTurn;
    }
}
