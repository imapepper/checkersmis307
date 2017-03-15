package components;

import objects.CheckerPiece;

import javax.swing.*;
import java.awt.*;

public class Space extends JLabel {

    private final static ImageIcon blackPiece = new ImageIcon("resources/img/blackCheckerPiece.png");
    private final static ImageIcon blackKing = new ImageIcon("resources/img/blackCheckerPieceKing.png");
    private final static ImageIcon redPiece = new ImageIcon("resources/img/redCheckerPiece.png");
    private final static ImageIcon redKing = new ImageIcon("resources/img/redCheckerPieceKing.png");
    private final static ImageIcon selectedBlackPiece = new ImageIcon("resources/img/blackCheckerPieceSelected.png");
    private final static ImageIcon selectedBlackKing = new ImageIcon("resources/img/blackCheckerPieceKingSelected.png");
    private final static ImageIcon selectedRedPiece = new ImageIcon("resources/img/redCheckerPieceSelected.png");
    private final static ImageIcon selectedRedKing = new ImageIcon("resources/img/redCheckerPieceKingSelected.png");

    private int xCoordinate;
    private int yCoordinate;
    private boolean isPlayable;
    private CheckerPiece piece;

    Space(int yCoordinate, int xCoordinate, boolean isPlayable) {
        this.yCoordinate = yCoordinate;
        this.xCoordinate = xCoordinate;
        this.isPlayable = isPlayable;
        initializeLabel();
    }

    private void initializeLabel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(20, 20));
        if(isPlayable()) {
            setBackground(new Color(78, 49, 36));
        } else {
            setBackground(new Color(212, 164, 114));
        }
        setOpaque(true);
        setHorizontalAlignment(JLabel.CENTER);
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    private boolean isPlayable() {
        return isPlayable;
    }

    public CheckerPiece getPiece() {
        return piece;
    }

    void setPiece(CheckerPiece piece) {
        this.piece = piece;
        String color = piece.getColor();
        if(("black".equals(color) && this.yCoordinate == 8) || ("red".equals(color) && this.yCoordinate == 1)) {
            piece.setKing(true);
            CheckerBoardPanel.playSoundEffect("resources/sounds/king.wav");
        }
        changeIcon(false);
    }

    public void changeIcon(boolean selected) {
        String color = piece.getColor();
        if(piece.isKing()) {
            if ("black".equals(color)) {
                setIcon(selected ? selectedBlackKing : blackKing);
                if(CheckerBoardPanel.statusLabel != null) {
                	CheckerBoardPanel.statusLabel.setText("Black king piece selected for movement");
                }
            } else {
                setIcon(selected ? selectedRedKing : redKing);
                if(CheckerBoardPanel.statusLabel != null) {
                	CheckerBoardPanel.statusLabel.setText("Red king piece selected for movement");
                }
            }
        } else {
            if ("black".equals(color)) {
                setIcon(selected ? selectedBlackPiece : blackPiece);
                if(CheckerBoardPanel.statusLabel != null) {
                	CheckerBoardPanel.statusLabel.setText("Black piece selected for movement");
                }
            } else {
                setIcon(selected ? selectedRedPiece : redPiece);
                if(CheckerBoardPanel.statusLabel != null) {
                	CheckerBoardPanel.statusLabel.setText("Red piece selected for movement");
                }
            }
        }
    }

    CheckerPiece removePiece() {
        CheckerPiece removed = piece;
        piece = null;
        setIcon(null);
        return removed;
    }
}
