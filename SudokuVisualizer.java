import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SudokuVisualizer extends JFrame {
    private static final int SIZE = 9;
    private JTextField[][] cells;
    private SudokuSolver solver;
    private JPanel gridPanel;

    public SudokuVisualizer() {
        solver = new SudokuSolver(this);
        cells = new JTextField[SIZE][SIZE];
        setTitle("Sudoku Solver");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gridPanel.setBackground(Color.DARK_GRAY);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("Arial", Font.BOLD, 20));
                cells[row][col].setBackground(Color.WHITE);
                
                // Set borders for 3x3 grids
                int top = (row % 3 == 0) ? 4 : 1;
                int left = (col % 3 == 0) ? 4 : 1;
                int bottom = (row == SIZE - 1) ? 4 : 1;
                int right = (col == SIZE - 1) ? 4 : 1;
                
                cells[row][col].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
                
                gridPanel.add(cells[row][col]);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.setBackground(Color.LIGHT_GRAY);

        // Solve Button
        JButton solveButton = new JButton("Solve");
        solveButton.setFont(new Font("Arial", Font.BOLD, 18));
        solveButton.setBackground(new Color(0, 123, 255));
        solveButton.setForeground(Color.WHITE);
        solveButton.setFocusPainted(false);
        solveButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] board = new int[SIZE][SIZE];
                for (int row = 0; row < SIZE; row++) {
                    for (int col = 0; col < SIZE; col++) {
                        String text = cells[row][col].getText();
                        if (!text.isEmpty()) {
                            board[row][col] = Integer.parseInt(text);
                            cells[row][col].setBackground(Color.LIGHT_GRAY); // Set fixed cells to gray
                            cells[row][col].setEditable(false);
                        }
                    }
                }
                new Thread(() -> solver.solveSudoku(board)).start();
            }
        });
        bottomPanel.add(solveButton);

        // Random Button
        JButton randomButton = new JButton("Random");
        randomButton.setFont(new Font("Arial", Font.BOLD, 18));
        randomButton.setBackground(new Color(0, 123, 255));
        randomButton.setForeground(Color.WHITE);
        randomButton.setFocusPainted(false);
        randomButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random rand = new Random();
                for (int row = 0; row < SIZE; row++) {
                    for (int col = 0; col < SIZE; col++) {
                        cells[row][col].setText("");
                        cells[row][col].setBackground(Color.WHITE);
                        cells[row][col].setEditable(true);
                    }
                }
                for (int i = 0; i < SIZE * 2; i++) { // adding some random numbers
                    int row = rand.nextInt(SIZE);
                    int col = rand.nextInt(SIZE);
                    int value = rand.nextInt(9) + 1;
                    cells[row][col].setText(String.valueOf(value));
                    cells[row][col].setBackground(Color.LIGHT_GRAY); // Set fixed cells to gray
                    cells[row][col].setEditable(false);
                }
            }
        });
        bottomPanel.add(randomButton);

        // Clear Button
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 18));
        clearButton.setBackground(new Color(0, 123, 255));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int row = 0; row < SIZE; row++) {
                    for (int col = 0; col < SIZE; col++) {
                        cells[row][col].setText("");
                        cells[row][col].setBackground(Color.WHITE);
                        cells[row][col].setEditable(true);
                    }
                }
            }
        });
        bottomPanel.add(clearButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void updateCell(int row, int col, int value, boolean isBacktrack) {
        try {
            Thread.sleep(50); // delay for visualization
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                cells[row][col].setText(value == 0 ? "" : String.valueOf(value));
                cells[row][col].setBackground(isBacktrack ? new Color(255, 102, 102) : new Color(102, 255, 178));
                if (value != 0) {
                    cells[row][col].setEditable(false); // Disable editing on filled cells
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuVisualizer().setVisible(true);
            }
        });
    }
}
