package components;

import eventListeners.ClickListener;
import objects.CheckerPiece;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class CheckerBoardPanel extends JPanel {

    private static Space[][] spaces;
    public static JLabel statusLabel;
    public static String status;

    public CheckerBoardPanel() {
        initializeSpaces();
        setLayout(new GridBagLayout());
        initializeStatus();
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
                space.addMouseListener(new ClickListener());
                add(space, createConstraints(i + 1, j + 1, 1));
            }
        }
    }
    
    private void initializeStatus() {
    	status = "Initialized board";
    	statusLabel = new JLabel("STATUS: " + status);
    	statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	add(statusLabel, createConstraints(0, 0, 8));
    }

    private GridBagConstraints createConstraints(int gridY, int gridX, int gridWidth) {
        return new GridBagConstraints(gridX, gridY, gridWidth, 1,
                0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 50, 50);
    }

    public static CheckerPiece removePiece(int fromY, int fromX) {
        return spaces[fromY - 1][fromX - 1].removePiece();
    }

    public static void movePiece(int fromY, int fromX, int toY, int toX) {
        if(spaces[fromY - 1][fromX - 1].getPiece() == null) {
            throw new IllegalArgumentException("There is no piece to move on that space");
        }
        if(spaces[toY - 1][toX - 1].getPiece() != null) {
            throw new IllegalArgumentException("Space already has a piece");
        }
        CheckerPiece piece = removePiece(fromY, fromX);
        spaces[toY - 1][toX - 1].setPiece(piece);
    }

    public static Space[] getValidMoves(Space space) {
        if(space.getPiece() == null) {
            throw new IllegalArgumentException("There is no piece to move on that space");
        }
        Space[] openSpaces = new Space[4];
        int i = 0;
        CheckerPiece piece = space.getPiece();
        String color = piece.getColor();
        boolean isKing = piece.isKing();

        if("red".equals(color) || isKing) {
            Space aboveLeft = isValidMove(space, -1, -1);
            if(aboveLeft != null) {
                openSpaces[i] = aboveLeft;
                i++;
            }
            Space aboveRight = isValidMove(space, -1, 1);
            if(aboveRight != null) {
                openSpaces[i] = aboveRight;
                i++;
            }
        }
        if("black".equals(color) || isKing) {
            Space belowLeft = isValidMove(space, 1, -1);
            if(belowLeft != null) {
                openSpaces[i] = belowLeft;
                i++;
            }
            Space belowRight = isValidMove(space, 1, 1);
            if(belowRight != null) {
                openSpaces[i] = belowRight;
                i++;
            }
        }
        return Arrays.copyOf(openSpaces, i);
    }

    private static Space isValidMove(Space space, int changeY, int changeX) {
        int yCoordinate = space.getYCoordinate() - 1;
        int xCoordinate = space.getXCoordinate() - 1;
        CheckerPiece piece = space.getPiece();
        String color = piece.getColor();

        if(yCoordinate + changeY >= 0 && yCoordinate + changeY < 8 &&
                xCoordinate + changeX >= 0 && xCoordinate + changeX < 8) {
            Space newSpace = spaces[yCoordinate + changeY][xCoordinate + changeX];
            CheckerPiece newPiece;
            if((newPiece = newSpace.getPiece()) == null) {
                return newSpace;
            } else if(!color.equals(newPiece.getColor()) && Math.abs(changeY) < 2 && Math.abs(changeX) < 2) {
                return isValidMove(space, changeY * 2, changeX * 2);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}