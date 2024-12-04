package org.example;

public class Minimax_C4_Algorithm {

    // Evaluate the board state. Returns a score based on how favorable the board is for Player 2 (AI).
    private static int evaluateBoard(int[][] board) {
        int score = 0;
        
        // Horizontal, Vertical and Diagonal checks for scoring
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (board[row][col] == 1) {
                    score -= evaluateDirection(board, row, col, 1);  // Player 1's chips hurt the score
                } else if (board[row][col] == 2) {
                    score += evaluateDirection(board, row, col, 2);  // Player 2's chips help the score
                }
            }
        }
        return score;
    }

    // Helper method to evaluate each direction (horizontal, vertical, diagonal)
    private static int evaluateDirection(int[][] board, int row, int col, int player) {
        int score = 0;
        
        // Horizontal check
        score += evaluateLine(board, row, col, 0, 1, player);  // Right direction
        // Vertical check
        score += evaluateLine(board, row, col, 1, 0, player);  // Down direction
        // Diagonal (bottom-left to top-right)
        score += evaluateLine(board, row, col, 1, 1, player);  // Down-right direction
        // Diagonal (top-left to bottom-right)
        score += evaluateLine(board, row, col, 1, -1, player);  // Down-left direction
        
        return score;
    }

    // Evaluate a line of 4 cells in a direction (used for horizontal, vertical, diagonal scoring)
    private static int evaluateLine(int[][] board, int row, int col, int dRow, int dCol, int player) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int r = row + i * dRow;
            int c = col + i * dCol;
            if (r >= 0 && r < 6 && c >= 0 && c < 7) {
                if (board[r][c] == player) {
                    count++;
                } else if (board[r][c] != 0) {
                    return 0;  // A mixed line (blocked by the opponent)
                }
            }
        }
        return count;
    }

    // Check if a player has won
    private static int checkWinner(int[][] board) {
        // Horizontal, Vertical, and Diagonal checks for winner
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] != 0 && board[row][col] == board[row][col + 1] 
                    && board[row][col] == board[row][col + 2] && board[row][col] == board[row][col + 3]) {
                    return board[row][col];
                }
            }
        }
        
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 3; row++) {
                if (board[row][col] != 0 && board[row][col] == board[row + 1][col]
                    && board[row][col] == board[row + 2][col] && board[row][col] == board[row + 3][col]) {
                    return board[row][col];
                }
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] != 0 && board[row][col] == board[row + 1][col + 1]
                    && board[row][col] == board[row + 2][col + 2] && board[row][col] == board[row + 3][col + 3]) {
                    return board[row][col];
                }
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 3; col < 7; col++) {
                if (board[row][col] != 0 && board[row][col] == board[row + 1][col - 1]
                    && board[row][col] == board[row + 2][col - 2] && board[row][col] == board[row + 3][col - 3]) {
                    return board[row][col];
                }
            }
        }

        // Check for draw
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

    // Make a move on the board for a given player (1 or 2)
    private static boolean makeMove(int[][] board, int col, int player) {
        for (int row = 5; row >= 0; row--) {
            if (board[row][col] == 0) {
                board[row][col] = player;
                return true;
            }
        }
        return false;  // Column is full
    }

    // Undo a move for the given column
    private static void undoMove(int[][] board, int col) {
        for (int row = 0; row < 6; row++) {
            if (board[row][col] != 0) {
                board[row][col] = 0;
                break;
            }
        }
    }

    // Minimax Algorithm with Alpha-Beta Pruning
    private static int minimax(int[][] board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        int winner = checkWinner(board);
        if (winner != 0) {
            if (winner == 1) {
                return -1000;  // Player 1 wins (bad for AI)
            } else if (winner == 2) {
                return 1000;  // Player 2 wins (good for AI)
            } else {
                return 0;  // Draw
            }
        }

        if (depth == 0) {
            return evaluateBoard(board);  // Evaluate the board at this depth
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int col = 0; col < 7; col++) {
                if (board[0][col] == 0) {  // If the column is not full
                    makeMove(board, col, 2);  // Try AI move (Player 2)
                    int eval = minimax(board, depth - 1, alpha, beta, false);
                    undoMove(board, col);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) {
                        break;  // Beta cutoff
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int col = 0; col < 7; col++) {
                if (board[0][col] == 0) {  // If the column is not full
                    makeMove(board, col, 1);  // Try Player 1 move
                    int eval = minimax(board, depth - 1, alpha, beta, true);
                    undoMove(board, col);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) {
                        break;  // Alpha cutoff
                    }
                }
            }
            return minEval;
        }
    }

    // Find the best move for the AI (Player 2)
    public static int findBestMove(int[][] board) {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        // Evaluate each column to find the best move
        for (int col = 0; col < 7; col++) {
            if (board[0][col] == 0) {  // If the column is not full
                makeMove(board, col, 2);  // Try AI move (Player 2)
                int score = minimax(board,5, Integer.MIN_VALUE, Integer.MAX_VALUE, false);  // Depth 5 for AI
                undoMove(board, col);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = col;
                }
            }
        }
        return bestMove;  // Return the best column index
    }
}
