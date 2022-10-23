package thegame;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    public static int REWARD = 1000,
                      COST = 250,
                      ROW_COUNT = 9,
                      COL_COUNT = 14,
                      BAG_COUNT = 10;
    private final JTextField moneyField = new JTextField("", 8),
                             foundField = new JTextField("", 8);

    private int money,
               found;
    private final GameBoard board;

    public Game() {
        super("MoneyGrab");
        this.setSize(650, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.moneyField.setEditable(false);
        this.foundField.setEditable(false);
        this.board = new GameBoard(this, Game.ROW_COUNT, Game.COL_COUNT, Game.BAG_COUNT);
        JButton restart = new JButton("Restart");
        restart.addActionListener(evt -> {

                if (evt.getSource() instanceof GameButton button) {
                    if (button.getState() == -2) {
                        button.revealMoney();
                    } else {
                        button.clearGameButton(true);
                    }
                } else {
                    setupGame();
                    board.setupGameBoard();
                }

        });
        JPanel top = new JPanel();
        top.add(new JLabel("Money: $"));
        top.add(this.moneyField);
        top.add(new JLabel("Found: "));
        top.add(this.foundField);
        top.add(restart);
        this.add(top, BorderLayout.NORTH);
        this.add(this.board, BorderLayout.CENTER);
        this.setupGame();
        this.setVisible(true);
    }

    protected final void setupGame() {
        this.found = 0;
        this.money = 1000;
        this.moneyField.setText("" + this.money);
        this.foundField.setText("" + this.found + " of " + Game.BAG_COUNT);
    }

    protected final void spendMoney() {
        this.money -= Game.COST;
        this.moneyField.setText("" + this.money);
        if (this.money <= 0) {
            this.revealBoard();
        }
    }

    protected final void earnMoney() {
        this.money += Game.REWARD;
        this.moneyField.setText("" + this.money);
        this.found++;
        this.foundField.setText("" + this.found + " of " + Game.BAG_COUNT);
        if (this.found >= Game.BAG_COUNT) {
            this.revealBoard();
        }
    }

    private void revealBoard() {
        for (int i = 0; i < Game.ROW_COUNT; i++) {
            for (int j = 0; j < Game.COL_COUNT; j++) {
                if (this.board.getTableCell(i, j).getState() == -2) {
                    this.board.getTableCell(i, j).setFound(true);
                    this.board.getTableCell(i, j).revealMoney();
                } else {
                    if (this.money > 0) {
                        this.board.getTableCell(i, j).setBackground(Color.GREEN);
                    } else {
                        this.board.getTableCell(i, j).setBackground(Color.RED);
                    }
                }
                if (this.board.getTableCell(i, j).getState() == 0) {
                    this.board.getTableCell(i, j).clearGameButton(false);
                }
            }
        }
    }

    public final GameBoard getBoard() {
        return this.board;
    }

}
