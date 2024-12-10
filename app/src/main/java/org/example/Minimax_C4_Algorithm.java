/**
 * Modified Version of: https://github.com/vmsaif/connect4-with-minimax-algorithm-in-java/blob/main/src/Computer.java (No License)
 * Modified for use for the Connect 4 game in this JavaFX application
 */

package org.example;

public class Minimax_C4_Algorithm {

    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final int EMPTY = 0;
    private static final int WHITE = 1;
    private static final int BLACK = 2;

    // Direction vectors for checking lines (right, down, down-right, down-left)
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};
    private static final int WINNING_LENGTH = 4; // Length of a winning sequence

    public static int MaxDepth = 5;
    public static int[] findBestMove(int[][] board) {
        int[] bestMove = new int[2]; // [row, col]
        int maxDepth = MaxDepth;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int evaluation = Integer.MIN_VALUE;

        for (int row = ROWS - 1; row >= 0; row--) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == EMPTY) {
                    // Check if this is a valid move (empty cell)
                    if (row == ROWS - 1 || board[row + 1][col] != EMPTY) {
                        // Try this move
                        board[row][col] = BLACK;
                        int temp = minimax(board, maxDepth, alpha, beta, false);

                        // Reset the move
                        board[row][col] = EMPTY;

                        if (temp > evaluation) {
                            // Got a better move
                            evaluation = temp;
                            bestMove[0] = row;
                            bestMove[1] = col;
                        }
                    }
                }
            }
        }
        return bestMove;
    }

    // Evaluate the board for a given player (BLACK or WHITE)
    private static int evaluateBoardForPlayer(int[][] board, int player) {
        int score = 0;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                for (int[] dir : DIRECTIONS) {
                    int countPlayerPieces = 0;
                    int countEmpty = 0;

                    for (int i = 0; i < WINNING_LENGTH; i++) {
                        int newRow = row + dir[0] * i;
                        int newCol = col + dir[1] * i;

                        if (newRow >= 0 && newRow < ROWS && newCol >= 0 && newCol < COLS) {
                            if (board[newRow][newCol] == player) {
                                countPlayerPieces++;
                            } else if (board[newRow][newCol] == EMPTY) {
                                countEmpty++;
                            }
                        }
                    }

                    if (countPlayerPieces + countEmpty == WINNING_LENGTH) {
                        int initialScore = (WINNING_LENGTH * 10) + 10; // For winningLength = 4, initialScore = 50
                        for (int i = 1; i < WINNING_LENGTH; i++) {
                            if (countPlayerPieces == WINNING_LENGTH - i) {
                                score += initialScore / i;
                                initialScore -= 10;
                            }
                        }
                    }
                }
            }
        }
        return score;
    }

    // Minimax algorithm with alpha-beta pruning
    private static int minimax(int[][] board, int depth, int alpha, int beta, boolean isWhitesTurn) {
        int result = 0;
        boolean stopSearch = false;

        if (depth == 0 || checkWin(board, BLACK) || checkWin(board, WHITE) || checkDraw(board)) {
            if (checkWin(board, BLACK)) {
                result = 1000 + depth;
            } else if (checkWin(board, WHITE)) {
                result = -(1000 + depth);
            } else if (checkDraw(board)) {
                result = 0;
            } else {
                int blackScore = evaluateBoardForPlayer(board, BLACK);
                int whiteScore = evaluateBoardForPlayer(board, WHITE);
                result = blackScore - whiteScore;
            }
        } else {
            if (!isWhitesTurn) {
                // Maximizing player: COMPUTER (BLACK)
                result = Integer.MAX_VALUE;
                for (int row = ROWS - 1; row >= 0 && !stopSearch; row--) {
                    for (int col = 0; col < COLS && !stopSearch; col++) {
                        if (board[row][col] == EMPTY) {
                            if (row == ROWS - 1 || board[row + 1][col] != EMPTY) {
                                board[row][col] = WHITE;
                                int temp = minimax(board, depth - 1, alpha, beta, true);
                                board[row][col] = EMPTY;
                                result = Math.min(result, temp);
                                beta = Math.min(beta, result);

                                if (beta <= alpha) {
                                    stopSearch = true;
                                }
                            }
                        }
                    }
                }
            } else {
                // Minimizing player: USER (WHITE)
                result = Integer.MIN_VALUE;
                for (int row = ROWS - 1; row >= 0 && !stopSearch; row--) {
                    for (int col = 0; col < COLS && !stopSearch; col++) {
                        if (board[row][col] == EMPTY) {
                            if (row == ROWS - 1 || board[row + 1][col] != EMPTY) {
                                board[row][col] = BLACK;
                                int temp = minimax(board, depth - 1, alpha, beta, false);
                                board[row][col] = EMPTY;
                                result = Math.max(result, temp);
                                alpha = Math.max(alpha, result);

                                if (beta <= alpha) {
                                    stopSearch = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    // Check if a player has won
    private static boolean checkWin(int[][] board, int player) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                for (int[] dir : DIRECTIONS) {
                    int count = 0;
                    for (int i = 0; i < WINNING_LENGTH; i++) {
                        int newRow = row + dir[0] * i;
                        int newCol = col + dir[1] * i;
                        if (newRow >= 0 && newRow < ROWS && newCol >= 0 && newCol < COLS) {
                            if (board[newRow][newCol] == player) {
                                count++;
                            }
                        }
                    }
                    if (count == WINNING_LENGTH) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Check if the board is full (draw condition)
    private static boolean checkDraw(int[][] board) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}
