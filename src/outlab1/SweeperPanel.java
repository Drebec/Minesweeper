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

    private boolean mine, flagged, clicked;
    private int size, x, y;
    private String numMines;

    public SweeperPanel(int size, int x, int y) {
        this.size = size;
        this.x = x;
        this.y = y;
        flagged = false;
        clicked = false;
        setLayout(new BorderLayout());

        Dimension dims = new Dimension(size, size);
        setPreferredSize(dims);
        setMaximumSize(dims);
        setMinimumSize(dims);

        addMouseListener(new MSMouseListener());
        int rand = (int) (Math.random() * 8);

        if (rand == 0) {
            mine = true;
        } else {
            mine = false;
        }
    }

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

    public void lose() {
        for (int i = 0; i < Minesweeper.getBoard().length; i++) {
            for (int j = 0; j < Minesweeper.getBoard()[i].length; j++) {
                if (Minesweeper.getBoard()[i][j].getMine()) {
                    Minesweeper.getBoard()[i][j].setClicked();
                    Minesweeper.getBoard()[i][j].repaint();
                }
            }
        }
        JOptionPane.showMessageDialog(this, "You Hit A Mine!");
        System.exit(0);
    }

    public boolean win() {
        for (int i = 0; i < Minesweeper.getBoard().length; i++) {
            for (int j = 0; j < Minesweeper.getBoard()[i].length; j++) {
                if (!Minesweeper.getBoard()[i][j].getClicked() && !Minesweeper.getBoard()[i][j].getMine()) {
                    return false;
                }
            }
        }
        for (int i = 0; i < Minesweeper.getBoard().length; i++) {
            for (int j = 0; j < Minesweeper.getBoard()[i].length; j++) {
                if (Minesweeper.getBoard()[i][j].getMine()) {
                    Minesweeper.getBoard()[i][j].setFlagged();
                    Minesweeper.getBoard()[i][j].repaint();
                }
            }
        }
        return true;
    }

    public void flood(int a, int b) {
        for (int i = (a - 1); i <= (a + 1); i++) {
            for (int j = (b - 1); j <= (b + 1); j++) {
                if (i >= 0 && i < Minesweeper.getBoard().length && j >= 0 && j < Minesweeper.getBoard()[0].length) {
                    if (Minesweeper.getBoard()[i][j].numMines.equals("0") && !Minesweeper.getBoard()[i][j].clicked) {
                        Minesweeper.getBoard()[i][j].setClicked();
                        Minesweeper.getBoard()[i][j].repaint();
                        Minesweeper.getBoard()[i][j].flood(i, j);
                    } else {
                        Minesweeper.getBoard()[i][j].setClicked();
                        Minesweeper.getBoard()[i][j].repaint();
                    }
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
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
            if (mine) {
                g.setColor(Color.RED);
                g.fillRect(0, 0, size, size);
                g.setColor(Color.BLACK);
                g.drawRect(0, 0, size, size);
            } else {
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
                    switch(numMines) {
                        case "1":
                            g.setColor(Color.BLACK);
                            break;
                        case "2":
                            g.setColor(Color.BLUE);
                            break;
                        case"3":
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
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, size, size);
                    g.setColor(Color.BLACK);
                    g.drawRect(0, 0, size, size);
                }
            }
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
            if (SwingUtilities.isRightMouseButton(e)) {
                if (!flagged && !clicked) {
                    flagged = true;
                } else if (flagged) {
                    flagged = false;
                } else {
                }
            } else if (SwingUtilities.isLeftMouseButton(e)) {
                if (!flagged && !clicked) {
                    clicked = true;
                    repaint();
                    if (mine) {
                        lose();
                    } else if (numMines.equals("0")) {
                        flood(x, y);
                    }
                } else {
                }
                if (win()) {
                    JOptionPane.showMessageDialog(null, "You Win!");
                    System.exit(0);
                }
            }
            repaint();
        }
    }
}
