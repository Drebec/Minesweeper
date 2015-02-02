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

    //instance variables
    private static SweeperPanel[][] board;
    private int width;
    private int height;

    //constructor
    public Minesweeper(int width, int height) {
        super("Minesweeper");
        this.width = width;
        this.height = height;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create a new JFrame with a grid layout
        getContentPane().setLayout(new GridLayout(width, height));

        //create a board variable to hold an array which represents the board
        board = new SweeperPanel[width][height];
        //populate the array with SweeperPanel objects
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                SweeperPanel spot = new SweeperPanel(30, i, j);
                board[i][j] = spot;
                getContentPane().add(spot);
            }
        }

        //search throught the entire board to set the number of mines arount each space
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!board[i][j].getMine()) {
                    board[i][j].setNumMines(String.valueOf(setNums(i, j)));
                }
            }
        }

        //finishing touches on the JFrame
        pack();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    //static method to return the board so it can be accessed from the SweeperPanel class
    public static SweeperPanel[][] returnBoard() {
        return board;
    }

    //search around a specific spot passed in to the method for the number of mines
    public int setNums(int i, int j) {
        //counter counts the number of mines
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

    //Driver
    public static void main(String[] args) {
        //initial question
        int choice = JOptionPane.showConfirmDialog(null, "Would you like to use the standard 10 by 10 board size?");
        //if they said yes set up a new standard 10 by 10 board
        if (choice == 0) {
            Minesweeper m1 = new Minesweeper(10, 10);
            //otherwise ask them which size they would like
        } else if (choice == 1) {
            boolean play = false;
            String sizeString;
            int size = 0;
            while (!play) {
                try {
                    sizeString = (JOptionPane.showInputDialog(null, "What would you like the board size to be?\nMore than 25 may not fit on your screen."));
                    //if they clicked cancel close the game
                    if (sizeString == null) {
                        System.exit(0);
                    }
                    //convert user input to an integer
                    size = Integer.parseInt(sizeString);
                    while (size < 5 || size > 25) {
                        JOptionPane.showMessageDialog(null, "Please enter a number from 5 to 25");
                        sizeString = (JOptionPane.showInputDialog(null, "What would you like the board size to be?\nEnter a number between 5 and 25."));
                        //if they clicked cancel close the game
                        if(sizeString == null) {
                            System.exit(0);
                        }
                        size = Integer.parseInt(sizeString);
                    }
                    //create a new board with the user input size
                    Minesweeper m2 = new Minesweeper(size, size);
                    play = true;
                //if the user input was not a number ask again
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter an integer value");
                }
            }
            //if they clicked cancel close the game
        } else {
            System.exit(0);
        }
    }
}
