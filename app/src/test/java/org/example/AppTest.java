/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import org.junit.jupiter.api.Test;

class AppTest {
    @Test void C4AI_TEST(){
        //test the AI with the following board:
        /*
         * 0 0 0 0 0 1 0 
            0 0 0 0 0 2 0 
            0 0 0 0 0 2 1 
            0 0 1 2 0 2 1 
            0 1 2 1 1 1 2 
            2 2 1 1 1 2 2 
         */
        int[][] board = new int[][]{
            {0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 2, 0},
            {0, 0, 0, 0, 0, 2, 1},
            {0, 0, 1, 2, 0, 2, 1},
            {0, 1, 2, 1, 1, 1, 2},
            {2, 2, 1, 1, 1, 2, 2}
        };
        Minimax_C4_Algorithm.MaxDepth = 10;
        int bestColumn = Minimax_C4_Algorithm.findBestMove(board);
        //make sure it returns an int between 0 and 6
        assert(bestColumn >= 0 && bestColumn <= 6);
    }
    @Test void C4AI_FullBoard_TEST(){
        //test the AI with the following board:
        /*
         * 1 2 1 2 1 2 1 
            2 1 2 1 2 1 2 
            1 2 1 2 1 2 1 
            2 1 2 1 2 1 2 
            1 2 1 2 1 2 1 
            2 1 2 1 2 1 2 
         */
        int[][] board = new int[][]{
            {1, 2, 1, 2, 1, 2, 1},
            {2, 1, 2, 1, 2, 1, 2},
            {1, 2, 1, 2, 1, 2, 1},
            {2, 1, 2, 1, 2, 1, 2},
            {1, 2, 1, 2, 1, 2, 1},
            {2, 1, 2, 1, 2, 1, 2}
        };
        Minimax_C4_Algorithm.MaxDepth = 10;
        int bestColumn = Minimax_C4_Algorithm.findBestMove(board);
        //make sure it returns an int between 0 and 6
        assert(bestColumn == -1);
    }
    @Test
    void AIvsAI_FULL_TEST() {
        // Initialize a blank board
        int[][] board = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };
    
        Minimax_C4_Algorithm.MaxDepth = 10; // Set difficulty level
    
        boolean gameOver = false;
        int currentPlayer = 1; // Start with Player 1
        int moves = 0;
    
        // Loop until the game is over or the board is full
        while (!gameOver && moves < 42) {
            int bestColumn = Minimax_C4_Algorithm.findBestMove(board);
    
            // Ensure the AI's move is within bounds
            assert(bestColumn >= 0 && bestColumn <= 6);
    
            // Drop the piece for the current player in the selected column
            boolean moveMade = dropPiece(board, bestColumn, currentPlayer);
            assert(moveMade); // Verify the move was valid
    
            // Print the board for debugging purposes
            System.out.println("Move #" + (moves + 1) + ": Player " + currentPlayer + " column #:" + bestColumn);
            printBoard(board);
    
            // Check if the move resulted in a win or draw
            if (checkWin(board, currentPlayer)) {
                System.out.println("Player " + currentPlayer + " wins!");
                gameOver = true;
            } else if (moves == 41) {
                System.out.println("It's a draw!");
                gameOver = true;
            }
    
            // Switch player and increment move count
            currentPlayer = (currentPlayer == 1) ? 2 : 1;
            moves++;
        }
    }
    
    // Helper method to simulate dropping a piece into the board
    private boolean dropPiece(int[][] board, int column, int player) {
        for (int i = board.length - 1; i >= 0; i--) {
            if (board[i][column] == 0) {
                board[i][column] = player;
                return true;
            }
        }
        return false; // Column is full
    }
    
    // Helper method to print the current state of the board
    private void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print((cell == 0 ? "." : (cell == 1 ? "X" : "O")) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    // Method to check if the current player has won
    private boolean checkWin(int[][] board, int player) {
        // Check horizontal, vertical, and diagonal wins
        int rows = board.length, cols = board[0].length;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (checkDirection(board, r, c, 0, 1, player) || // Horizontal
                    checkDirection(board, r, c, 1, 0, player) || // Vertical
                    checkDirection(board, r, c, 1, 1, player) || // Diagonal down-right
                    checkDirection(board, r, c, 1, -1, player)) { // Diagonal down-left
                    return true;
                }
            }
        }
        return false;
    }
    
    // Helper method to check a specific direction for a win
    private boolean checkDirection(int[][] board, int row, int col, int dRow, int dCol, int player) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int r = row + i * dRow;
            int c = col + i * dCol;
            if (r >= 0 && r < board.length && c >= 0 && c < board[0].length && board[r][c] == player) {
                count++;
            } else {
                break;
            }
        }
        return count == 4;
    }    
}
