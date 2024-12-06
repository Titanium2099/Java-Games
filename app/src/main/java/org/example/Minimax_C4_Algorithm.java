package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class Minimax_C4_Algorithm {
    public static int MaxDepth;
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
    
        // Vertical-specific condition: ensure all rows below the evaluated line are filled
        if (dRow == 1 && dCol == 0) {
            for (int i = 0; i < 4; i++) {
                int r = row + i * dRow;
                if (r >= 6 || board[r][col] != player) {
                    return 0;  // Blocked, incomplete, or invalid vertical line
                }
            }
        } else {
            // General case for horizontal and diagonal checks
            for (int i = 0; i < 4; i++) {
                int r = row + i * dRow;
                int c = col + i * dCol;
    
                if (r < 0 || r >= 6 || c < 0 || c >= 7 || (board[r][c] != 0 && board[r][c] != player)) {
                    return 0;  // Invalid line
                }
                if (board[r][c] == player) {
                    count++;
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

    // Helper method to deep copy a 2D array
    private static int[][] deepCopyBoard(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();  // Create a copy of each row
        }
        return copy;
    }

    static class MoveEvaluationTask implements Callable<MoveEvaluation> {
        private final int[][] board;
        private final int column;
    
        public MoveEvaluationTask(int[][] board, int column) {
            this.board = deepCopyBoard(board);  // Avoid concurrency issues by deep copying
            this.column = column;
        }
    
        @Override
        public MoveEvaluation call() {
            if (makeMove(board, column, 2)) {
                int score = minimax(board, MaxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                undoMove(board, column);
                return new MoveEvaluation(column, score);
            }
            return new MoveEvaluation(column, Integer.MIN_VALUE);
        }
    }
    
    static class MoveEvaluation {
        int column;
        int score;
    
        public MoveEvaluation(int column, int score) {
            this.column = column;
            this.score = score;
        }
    }
    
    // Find the best move for the AI (Player 2)
    public static int findBestMove(int[][] board) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        List<MoveEvaluationTask> taskList = new ArrayList<>();

        for (int col = 0; col < 7; col++) {
            if (board[0][col] == 0) {  // Only create tasks for playable columns
                taskList.add(new MoveEvaluationTask(board, col));
            }
        }

        //fallback
        if (taskList.isEmpty()) {
            for (int col = 0; col < 7; col++) {
                if (board[0][col] == 0) {
                    return col;
                }
            }
            return -1;
        }        

        try {
            List<Future<MoveEvaluation>> results = pool.invokeAll(taskList);
            int bestScore = Integer.MIN_VALUE;
            int bestMove = -1;

            for (Future<MoveEvaluation> result : results) {
                MoveEvaluation eval = result.get();
                if (eval.score > bestScore) {
                    bestScore = eval.score;
                    bestMove = eval.column;
                }
            }
            return bestMove;

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return -1;  // Fallback if something goes wrong
    }
}
