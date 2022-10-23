package thegame;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {

    private final int rows,
                      cols,
                      moneyBags;
    private final GameButton[][] table;
    private final Game app;

    GameBoard(Game inApp, int inRows, int inCols, int inMoneyBags) {
        this.app = inApp;
        this.rows = inRows;
        this.cols = inCols;
        this.moneyBags = inMoneyBags;
        this.table = new GameButton[this.rows][this.cols];
        this.setLayout(new GridLayout(this.rows, this.cols));
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.table[i][j] = new GameButton(this, i, j);
                this.table[i][j].addActionListener(evt -> {

                    if (evt.getSource() instanceof GameButton button) {
                        if (button.getState() == -2) {
                            button.revealMoney();
                        } else {
                            button.clearGameButton(true);
                        }
                    } else {
                        app.setupGame();
                        app.getBoard().setupGameBoard();
                    }

                });
                this.add(this.table[i][j]);
            }
        }
        this.setupGameBoard();
    }

    protected final void setupGameBoard() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.table[i][j].setupGameButton();
            }
        }
        for(int i = 0; i < this.moneyBags;) {
            int row = (int)Math.floor(Math.random() * this.rows),
                col = (int)Math.floor(Math.random() * this.cols);
            if (this.table[row][col].getState() == -2) {
                continue;
            }
            i++;
            this.table[row][col].setState(-2);
        }
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (this.table[i][j].getState() == 0) {
                    this.table[i][j].setNear(this.getBagCount(i, j));
                }
            }
        }
    }

    private int getBagCount(int row, int col) {
        int count = 0;
        for (int i = ((row > 0) ? row - 1 : row); i <= ((row < this.rows - 1) ? row + 1 : row); i++) {
            for (int j = ((col > 0) ? col - 1 : col); j <= ((col < this.cols - 1) ? col + 1 : col); j++) {
                if (this.table[i][j].getState() == -2) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getRows() {
        return this.rows;
    }

    public int getCols() {
        return this.cols;
    }

    public GameButton getTableCell(int row, int col) {
        return this.table[row][col];
    }

    public Game getApp() {
        return this.app;
    }

    @Override
    public Insets getInsets() {
        return new Insets(15, 15, 15, 15);
    }

}