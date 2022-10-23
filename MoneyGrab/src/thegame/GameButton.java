package thegame;

import javax.swing.*;
import java.util.Objects;

public class GameButton extends JButton {

    private final int row,
                      col;
    private int state,
                near;
    private boolean found;
    private final GameBoard board;
    public static ImageIcon UNKNOWN_BAG,
                             MONEY_BAG;

    GameButton(GameBoard inBoard, int inRow, int inCol) {
        super("");
        this.board = inBoard;
        this.row = inRow;
        this.col = inCol;
        if (GameButton.UNKNOWN_BAG == null) {
            GameButton.UNKNOWN_BAG = new ImageIcon("unkn_bag.gif");
        }
        if(GameButton.MONEY_BAG == null){
            GameButton.MONEY_BAG = new ImageIcon("mny_bag.gif");
        }
        this.setupGameButton();
    }

    protected final void setupGameButton() {
        this.setIcon(GameButton.UNKNOWN_BAG);
        this.setText("");
        this.setEnabled(true);
        this.state = 0;
        this.near = 0;
        this.found = false;
        this.setBackground(null);
    }

    protected final void clearGameButton(boolean clicked) {
        if (clicked) {
            this.board.getApp().spendMoney();
        }
        this.setIcon(null);
        this.state = -1;
        this.setEnabled(false);
        if (this.near > 0) {
            this.setText("" + near);
        } else {
            if ((this.up() != null) && (Objects.requireNonNull(this.up()).state == 0)) {
                Objects.requireNonNull(this.up()).clearGameButton(false);
            }
            if ((this.down() != null) && (Objects.requireNonNull(this.down()).state == 0)) {
                Objects.requireNonNull(this.down()).clearGameButton(false);
            }
            if ((this.left() != null) && (Objects.requireNonNull(this.left()).state == 0)) {
                Objects.requireNonNull(this.left()).clearGameButton(false);
            }
            if ((this.right() != null) && (Objects.requireNonNull(this.right()).state == 0)) {
                Objects.requireNonNull(this.right()).clearGameButton(false);
            }
        }
    }

    protected final void revealMoney() {
        this.setIcon(GameButton.MONEY_BAG);
        this.setText("");
        if (!this.found) {
            this.board.getApp().earnMoney();
            this.found = true;
        }
    }

    private GameButton up() {
        if (this.row > 0) {
            return this.board.getTableCell(this.row - 1, this.col);
        }
        return null;
    }

    private GameButton down() {
        if (this.row < this.board.getRows() - 1) {
            return this.board.getTableCell(this.row + 1, this.col);
        }
        return null;
    }

    private GameButton left() {
        if (this.col > 0) {
            return this.board.getTableCell(this.row, this.col - 1);
        }
        return null;
    }

    private GameButton right() {
        if (this.col < this.board.getCols() - 1) {
            return this.board.getTableCell(this.row, this.col + 1);
        }
        return null;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setNear(int near) {
        this.near = near;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

}