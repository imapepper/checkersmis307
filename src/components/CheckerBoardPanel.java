package components;

import objects.CheckerPiece;

import javax.swing.*;
import java.awt.*;

public class CheckerBoardPanel extends JPanel {

    private Space[][] spaces;

    public CheckerBoardPanel() {
        initializeSpaces();
        setLayout(new GridBagLayout());
        initializeBoardGUI();
    }

    private void initializeSpaces() {
        spaces = new Space[8][8];
        int index = 0;
        while (index < 32) {
            for (int i = 0; i < 8; i++) {
                int start;
                if (i % 2 == 1) {
                    start = 0;
                } else {
                    start = 1;
                }
                for (int j = start; j < 8; j += 2) {
                    spaces[i][j] = new Space(i + 1, j + 1, true);
                    if(i < 3) {
                        spaces[i][j].setPiece(new CheckerPiece("black"));
                    } else if(i > 4) {
                        spaces[i][j].setPiece(new CheckerPiece("red"));
                    }
                    if(start == 0) {
                        spaces[i][j + 1] = new Space(i + 2, j + 1, false);
                    } else {
                        spaces[i][j - 1] = new Space(i, j + 1, false);
                    }
                    index++;
                }
            }
        }
    }

    private void initializeBoardGUI() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Space space = spaces[i][j];
                add(space, createConstraints(i, j));
            }
        }
    }

    private GridBagConstraints createConstraints(int gridY, int gridX) {
        return new GridBagConstraints(gridX, gridY, 1, 1,
                0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 50, 50);
    }

    public CheckerPiece removePiece(int fromY, int fromX) {
        return spaces[fromY][fromX].removePiece();
    }

    public void movePiece(int fromY, int fromX, int toY, int toX) {
        CheckerPiece piece = removePiece(fromY, fromX);
        spaces[toY][toX].setPiece(piece);
    }

    public Space[][] getSpaces() {
        return spaces;
    }
}