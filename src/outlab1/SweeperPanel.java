/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package outlab1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.SwingUtilities;

/**
 *
 * @author Drew
 *
 */
public class SweeperPanel extends JPanel {

    //instance variables
    private boolean mine, flagged, clicked;
    private int size, x, y;
    private String numMines;

    //constructor
    public SweeperPanel(int size, int x, int y) {
        this.size = size;
        this.x = x;
        this.y = y;
        flagged = false;
        clicked = false;
        setLayout(new BorderLayout());

        //set dimensions
        Dimension dims = new Dimension(size, size);
        setPreferredSize(dims);
        setMaximumSize(dims);
        setMinimumSize(dims);

        //add mouse listener
        addMouseListener(new MSMouseListener());
        //create a random number
        int rand = (int) (Math.random() * 8);

        //spots have a one in seven chance of being a mine
        if (rand == 0) {
            mine = true;
        } else {
            mine = false;
        }
    }

    //get and set methods
    public boolean getMine() {
        return mine;
    }

    public boolean getClicked() {
        return clicked;
    }

    public void setClicked() {
        clicked = true;
    }

    public boolean getFlagged() {
        return flagged;
    }

    public void setFlagged() {
        flagged = true;
    }

    public void setNumMines(String s) {
        numMines = s;
    }

    public SweeperPanel[][] getBoard() {
        return Minesweeper.returnBoard();
    }

    public SweeperPanel getBoard(int i, int j) {
        return Minesweeper.returnBoard()[i][j];
    }

    //lose method reveals all mines on the board and tells the user they lost
    public void lose() {
        for (int i = 0; i < getBoard().length; i++) {
            for (int j = 0; j < getBoard()[i].length; j++) {
                if (getBoard(i, j).getMine()) {
                    getBoard(i, j).setClicked();
                    getBoard(i, j).repaint();
                }
            }
        }
        JOptionPane.showMessageDialog(this, "You Hit A Mine!");
        System.exit(0);
    }

    //win method flags all unflagged mines and tells the user they won
    public boolean win() {
        for (int i = 0; i < getBoard().length; i++) {
            for (int j = 0; j < getBoard()[i].length; j++) {
                if (!getBoard(i, j).getClicked() && !getBoard(i, j).getMine()) {
                    return false;
                }
            }
        }
        for (int i = 0; i < getBoard().length; i++) {
            for (int j = 0; j < getBoard()[i].length; j++) {
                if (getBoard(i, j).getMine()) {
                    getBoard(i, j).setFlagged();
                    getBoard(i, j).repaint();
                }
            }
        }
        return true;
    }

    //flood method checks the 8 spots surrounding the last clicked spot
    //if the spot has no mines around it it clicks that spot, repaints it, then calls flood on that spot
    //otherwise it clicks the spot and repaints it
    public void flood(int a, int b) {
        for (int i = (a - 1); i <= (a + 1); i++) {
            for (int j = (b - 1); j <= (b + 1); j++) {
                if (i >= 0 && i < getBoard().length && j >= 0 && j < getBoard()[0].length) {
                    if (getBoard(i, j).numMines.equals("0") && !getBoard(i, j).clicked) {
                        getBoard(i, j).setClicked();
                        getBoard(i, j).repaint();
                        getBoard(i, j).flood(i, j);
                    } else {
                        getBoard(i, j).setClicked();
                        getBoard(i, j).repaint();
                    }
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        //print the "F" over flagged spots
        if (flagged) {
            Font f = new Font("Times", Font.PLAIN, 30);
            g.setFont(f);
            FontMetrics fm = g.getFontMetrics();
            int a = fm.getAscent();
            int h = fm.getHeight();
            int fl = fm.stringWidth("F");
            g.setColor(new Color(170, 171, 167));
            g.fillRect(0, 0, size, size);
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, size, size);
            g.setColor(Color.WHITE);
            g.drawString("F", size / 2 - fl / 2, size / 2 + a - h / 2);
        } else if (clicked) {
            //make the mines spots solid red squares
            if (mine) {
                g.setColor(Color.RED);
                g.fillRect(0, 0, size, size);
                g.setColor(Color.BLACK);
                g.drawRect(0, 0, size, size);
            } else {
                //if the spot has mines around it print the number in a specific color
                if (!numMines.equals("0")) {
                    Font f = new Font("Times", Font.PLAIN, 30);
                    g.setFont(f);
                    FontMetrics fm = g.getFontMetrics();
                    int a = fm.getAscent();
                    int h = fm.getHeight();
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, size, size);
                    g.setColor(Color.BLACK);
                    g.drawRect(0, 0, size, size);
                    int w = fm.stringWidth(numMines);
                    switch (numMines) {
                        case "1":
                            g.setColor(Color.BLACK);
                            break;
                        case "2":
                            g.setColor(Color.BLUE);
                            break;
                        case "3":
                            g.setColor(Color.RED);
                            break;
                        case "4":
                            g.setColor(Color.GREEN);
                            break;
                        case "5":
                            g.setColor(Color.ORANGE);
                            break;
                        case "6":
                            g.setColor(Color.YELLOW);
                            break;
                        case "7":
                            g.setColor(Color.CYAN);
                            break;
                        case "8":
                            g.setColor(Color.PINK);
                            break;
                        default:
                            break;
                    }
                    g.drawString(numMines, size / 2 - w / 2, size / 2 + a - h / 2);
                    //if the spot has no mines around it leave it blank
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, size, size);
                    g.setColor(Color.BLACK);
                    g.drawRect(0, 0, size, size);
                }
            }
            //if the spot was not clicked or flagged print the normal color
        } else {
            g.setColor(new Color(170, 171, 167));
            g.fillRect(1, 1, size - 2, size - 2);
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, size, size);
        }
    }

    private class MSMouseListener implements MouseListener {

        @Override
        public void mousePressed(MouseEvent e) {
            //System.out.println("press");
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //System.out.println("release");
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //System.out.println("mouse entered");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //System.out.println("mouse exited");
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            //on right click
            if (SwingUtilities.isRightMouseButton(e)) {
                //if unflagged and unclicked, flag
                if (!flagged && !clicked) {
                    flagged = true;
                    //if flagged, unflag
                } else if (flagged) {
                    flagged = false;
                    //on left click
                }
            } else if (SwingUtilities.isLeftMouseButton(e)) {
                //if unflagged and unclicked, click
                if (!flagged && !clicked) {
                    clicked = true;
                    repaint();
                    //if the spot was a mine run the lose method
                    if (mine) {
                        lose();
                        //if the spot had no mines around it flood
                    } else if (numMines.equals("0")) {
                        flood(x, y);
                    }
                }
                //if the player won, tell them and close the game
                if (win()) {
                    JOptionPane.showMessageDialog(null, "You Win!");
                    System.exit(0);
                }
            }
            repaint();
        }
    }
}
