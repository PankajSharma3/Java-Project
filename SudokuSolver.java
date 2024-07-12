public class SudokuSolver {
    private static final int SIZE = 9;
    private SudokuVisualizer visualizer;

    public SudokuSolver(SudokuVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    public boolean solveSudoku(int[][] board) {
        return solve(board, 0, 0);
    }

    private boolean solve(int[][] board, int row, int col) {
        if (row == SIZE) {
            return true;
        }

        if (col == SIZE) {
            return solve(board, row + 1, 0);
        }

        if (board[row][col] != 0) {
            return solve(board, row, col + 1);
        }

        for (int num = 1; num <= SIZE; num++) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;
                visualizer.updateCell(row, col, num, false);
                if (solve(board, row, col + 1)) {
                    return true;
                }
                board[row][col] = 0;
                visualizer.updateCell(row, col, 0, true);
            }
        }

        return false;
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num || 
                board[row - row % 3 + i / 3][col - col % 3 + i % 3] == num) {
                return false;
            }
        }
        return true;
    }
}
