package org.example;

public class Minimax_Algorithm {

    private static final int HUMAN_PLAYER = 1;
    private static final int AI_PLAYER = 2;

    public static int[] findBestMove(int[][] boardState) {
        int bestValue = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};
        // Loop through all cells, evaluate minimax function for empty cells, and return the best move (highest score)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boardState[i][j] == 0) {
                    boardState[i][j] = AI_PLAYER;
                    int moveValue = minimax(boardState, 0, false);
                    boardState[i][j] = 0; // Undo the move (after evaluating score)
                    if (moveValue > bestValue) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestValue = moveValue;
                    }
                }
            }
        }

        return bestMove;
    }

    private static int minimax(int[][] boardState, int depth, boolean isMaximizingPlayer) {
        int score = checkWin(boardState);
        if (score == 10) {
            return score - depth;
        }
        if (score == -10) {
            return score + depth;
        }
        if (score == -1) { // Draw (base case)
            return 0;
        }
        //if we are "maximizing" we are trying to maximize the AI's score
        //else we are trying to minimize the human player's score (hence the name "minimax" ;( )
        if (isMaximizingPlayer) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (boardState[i][j] == 0) {
                        boardState[i][j] = AI_PLAYER;
                        best = Math.max(best, minimax(boardState, depth + 1, false));
                        boardState[i][j] = 0;
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (boardState[i][j] == 0) {
                        boardState[i][j] = HUMAN_PLAYER;
                        best = Math.min(best, minimax(boardState, depth + 1, true));
                        boardState[i][j] = 0;
                    }
                }
            }
            return best;
        }
    }

    /**
     * Check the current state of the board to see if there is a winner
     * @param boardState
     * @return 10 if AI wins, -10 if human wins, -1 if draw, 0 if no winner
     */
    private static int checkWin(int[][] boardState) {
        // Check rows for a win
        for (int row = 0; row < 3; row++) {
            if (boardState[row][0] == boardState[row][1] && boardState[row][1] == boardState[row][2]) {
                if (boardState[row][0] == AI_PLAYER) {
                    return 10;
                } else if (boardState[row][0] == HUMAN_PLAYER) {
                    return -10;
                }
            }
        }

        // Check columns for a win
        for (int col = 0; col < 3; col++) {
            if (boardState[0][col] == boardState[1][col] && boardState[1][col] == boardState[2][col]) {
                if (boardState[0][col] == AI_PLAYER) {
                    return 10;
                } else if (boardState[0][col] == HUMAN_PLAYER) {
                    return -10;
                }
            }
        }

        // Check diagonals for a win
        if (boardState[0][0] == boardState[1][1] && boardState[1][1] == boardState[2][2]) {
            if (boardState[0][0] == AI_PLAYER) {
                return 10;
            } else if (boardState[0][0] == HUMAN_PLAYER) {
                return -10;
            }
        }

        if (boardState[0][2] == boardState[1][1] && boardState[1][1] == boardState[2][0]) {
            if (boardState[0][2] == AI_PLAYER) {
                return 10;
            } else if (boardState[0][2] == HUMAN_PLAYER) {
                return -10;
            }
        }

        //check for a draw
        boolean hasEmptyCell = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boardState[i][j] == 0) {
                    hasEmptyCell = true;
                    break;
                }
            }
        }
        return hasEmptyCell ? -1 : 0;
    }
}