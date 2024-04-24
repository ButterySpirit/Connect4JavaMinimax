/*a fully working intelligent Connect 4 command line game using Java.
The game must allow the user to select the mode of the game. The two modes are:
- 2 player (2 human players)
- 1 player (Human vs AI) a fully working intelligent Connect 4 command line game using Java.
This logic for the AI must be implemented using the strategy outlined below.
Intelligent AI:
This must be implemented using the Minimax algorithm. The computer must examine the
game state at each point and deduce the optimal (best) play that can be made
*/
//Nikola Varicak 101432361
//Ian Mcdonald 101429262
//Sebastian Varon 101394889
//Valeria Arce 101462436

import java.util.Scanner;

public class Connect4 {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final char EMPTY = '-';
    private static final char PLAYER1 = 'X';
    private static final char PLAYER2 = 'O';

    private char[][] board;
    private Scanner scanner;
    private char currentPlayer;

    public Connect4() {
        board = new char[ROWS][COLS];
        scanner = new Scanner(System.in);
        currentPlayer = PLAYER1;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    private void printBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("0 1 2 3 4 5 6");
    }

    private boolean isValidMove(int col) {
        return col >= 0 && col < COLS && board[0][col] == EMPTY;
    }

    private int dropPiece(int col, char player) {
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][col] == EMPTY) {
                board[i][col] = player;
                return i;
            }
        }
        return -1; // Column is full
    }

    private boolean checkWin(char player) {
        // Check horizontal
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                if (board[i][j] == player &&
                        board[i][j + 1] == player &&
                        board[i][j + 2] == player &&
                        board[i][j + 3] == player) {
                    return true;
                }
            }
        }
        // Check vertical
        for (int i = 0; i <= ROWS - 4; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == player &&
                        board[i + 1][j] == player &&
                        board[i + 2][j] == player &&
                        board[i + 3][j] == player) {
                    return true;
                }
            }
        }
        // Check diagonal (positive slope)
        for (int i = 0; i <= ROWS - 4; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                if (board[i][j] == player &&
                        board[i + 1][j + 1] == player &&
                        board[i + 2][j + 2] == player &&
                        board[i + 3][j + 3] == player) {
                    return true;
                }
            }
        }
        // Check diagonal (negative slope)
        for (int i = 0; i <= ROWS - 4; i++) {
            for (int j = 3; j < COLS; j++) {
                if (board[i][j] == player &&
                        board[i + 1][j - 1] == player &&
                        board[i + 2][j - 2] == player &&
                        board[i + 3][j - 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private int evaluateBoard() {
        int score = 0;
        // Check rows
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                score += evaluateLine(i, j, 0, 1); // Horizontal
            }
        }
        // Check columns
        for (int i = 0; i <= ROWS - 4; i++) {
            for (int j = 0; j < COLS; j++) {
                score += evaluateLine(i, j, 1, 0); // Vertical
            }
        }
        // Check diagonals (positive slope)
        for (int i = 0; i <= ROWS - 4; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                score += evaluateLine(i, j, 1, 1); // Diagonal
            }
        }
        // Check diagonals (negative slope)
        for (int i = 0; i <= ROWS - 4; i++) {
            for (int j = 3; j < COLS; j++) {
                score += evaluateLine(i, j, 1, -1); // Diagonal
            }
        }
        return score;
    }

    private int evaluateLine(int row, int col, int rowDir, int colDir) {
        int score = 0;
        char[] line = new char[4];
        for (int i = 0; i < 4; i++) {
            line[i] = board[row + i * rowDir][col + i * colDir];
        }
        String lineStr = new String(line);
        if (lineStr.equals("" + currentPlayer + currentPlayer + currentPlayer + currentPlayer)) {
            score += 100;
        } else if (lineStr.equals("" + EMPTY + currentPlayer + currentPlayer + currentPlayer)) {
            score += 10;
        } else if (lineStr.equals("" + currentPlayer + EMPTY + currentPlayer + currentPlayer)) {
            score += 10;
        } else if (lineStr.equals("" + currentPlayer + currentPlayer + EMPTY + currentPlayer)) {
            score += 10;
        } else if (lineStr.equals("" + currentPlayer + currentPlayer + currentPlayer + EMPTY)) {
            score += 10;
        } else if (lineStr.equals("" + EMPTY + EMPTY + currentPlayer + currentPlayer)) {
            score += 5;
        } else if (lineStr.equals("" + EMPTY + currentPlayer + EMPTY + currentPlayer)) {
            score += 5;
        } else if (lineStr.equals("" + EMPTY + currentPlayer + currentPlayer + EMPTY)) {
            score += 5;
        } else if (lineStr.equals("" + currentPlayer + EMPTY + EMPTY + currentPlayer)) {
            score += 5;
        } else if (lineStr.equals("" + currentPlayer + EMPTY + currentPlayer + EMPTY)) {
            score += 5;
        } else if (lineStr.equals("" + currentPlayer + currentPlayer + EMPTY + EMPTY)) {
            score += 5;
        } else if (lineStr.equals("" + EMPTY + currentPlayer + EMPTY + EMPTY)) {
            score += 1;
        }
        return score;
    }

    private int minimax(int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || checkWin(PLAYER1) || checkWin(PLAYER2) || isBoardFull()) {
            return evaluateBoard();
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int col = 0; col < COLS; col++) {
                if (isValidMove(col)) {
                    dropPiece(col, PLAYER2);
                    int eval = minimax(depth - 1, alpha, beta, false);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    board[findTopRow(col)][col] = EMPTY; // Undo move
                    if (beta <= alpha) {
                        break; // Beta cutoff
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int col = 0; col < COLS; col++) {
                if (isValidMove(col)) {
                    dropPiece(col, PLAYER1);
                    int eval = minimax(depth - 1, alpha, beta, true);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    board[findTopRow(col)][col] = EMPTY; // Undo move
                    if (beta <= alpha) {
                        break; // Alpha cutoff
                    }
                }
            }
            return minEval;
        }
    }

    private int findTopRow(int col) {
        for (int i = 0; i < ROWS; i++) {
            if (board[i][col] != EMPTY) {
                return i;
            }
        }
        return ROWS - 1;
    }

    private int aiMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;
        for (int col = 0; col < COLS; col++) {
            if (isValidMove(col)) {
                dropPiece(col, PLAYER2);
                int score = minimax(5, Integer.MIN_VALUE, Integer.MAX_VALUE, false); // Adjust depth as needed
                board[findTopRow(col)][col] = EMPTY; // Undo move
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = col;
                }
            }
        }
        return bestMove;
    }

    public void play() {
        int mode = selectMode();
        while (true) {
            printBoard();
            System.out.println("Player " + currentPlayer + "'s turn.");
            int col = -1;
            // Determine the move based on the mode and the current player
            if (mode == 1 || (mode == 2 && currentPlayer == PLAYER1)) {
                // Human move (either mode 1 or PLAYER1 in mode 2)
                col = getUserMove();
            } else if (mode == 2 && currentPlayer == PLAYER2) {
                // AI move in mode 2
                col = aiMove();
                System.out.println("AI chooses column " + col);
            }
            dropPiece(col, currentPlayer);
            if (checkWin(currentPlayer)) {
                printBoard();
                System.out.println("Player " + currentPlayer + " wins!");
                break;
            }
            if (isBoardFull()) {
                printBoard();
                System.out.println("It's a draw!");
                break;
            }
            // Switch players
            currentPlayer = (currentPlayer == PLAYER1) ? PLAYER2 : PLAYER1;
        }
    }


    private int selectMode() {
        System.out.println("Select mode:");
        System.out.println("1. Human vs Human");
        System.out.println("2. Human vs AI");
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }

    private int getUserMove() {
        System.out.print("Enter column (0-6): ");
        int col = scanner.nextInt();
        while (!isValidMove(col)) {
            System.out.println("Invalid move. Please try again.");
            System.out.print("Enter column (0-6): ");
            col = scanner.nextInt();
        }
        return col;
    }

    public static void main(String[] args) {
        Connect4 game = new Connect4();
        game.play();
    }
}