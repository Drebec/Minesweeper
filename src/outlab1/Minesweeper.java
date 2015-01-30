/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package outlab1;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Drew Beck
 */
public class Minesweeper extends JFrame {

    private static SweeperPanel[][] board;
    private int width;
    private int height;

    public Minesweeper(int width, int height) {
        super("Minesweeper");
        this.width = width;
        this.height = height;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setLayout(new GridLayout(width, height));

        board = new SweeperPanel[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                SweeperPanel spot = new SweeperPanel(30, i, j);
                board[i][j] = spot;
                getContentPane().add(spot);
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!board[i][j].getMine()) {
                    board[i][j].setNumMines(String.valueOf(setNums(i, j)));
                }
            }
        }

        pack();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static SweeperPanel[][] getBoard() {
        return board;
    }

    public int setNums(int i, int j) {
        int counter = 0;

        for (int x = (i - 1); x <= (i + 1); x++) {
            for (int y = (j - 1); y <= (j + 1); y++) {
                if (x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
                    if (board[x][y].getMine()) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    public static void main(String[] args) {
        int choice = JOptionPane.showConfirmDialog(null, "Would you like to use the standard 10 by 10 board size?");
        if (choice == 0) {
            Minesweeper m1 = new Minesweeper(10, 10);
        } else if (choice == 1) {
            boolean play = false;
            String sizeString;
            int size = 0;
            while (!play) {
                try {
                    sizeString = (JOptionPane.showInputDialog(null, "What would you like the board size to be?\nMore than 25 may not fit on your screen."));
                    if (sizeString == null) {
                        System.exit(0);
                    }
                    size = Integer.parseInt(sizeString);
                    while (size < 5 || size > 25) {
                        JOptionPane.showMessageDialog(null, "Please enter a number from 5 to 25");
                        sizeString = (JOptionPane.showInputDialog(null, "What would you like the board size to be?\nEnter a number between 5 and 25."));
                        if(sizeString == null) {
                            System.exit(0);
                        }
                        size = Integer.parseInt(sizeString);
                    }
                    Minesweeper m2 = new Minesweeper(size, size);
                    play = true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter an integer value");
                }
            }
        } else {
            System.exit(0);
        }
    }
}
