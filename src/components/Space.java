package components;

import objects.CheckerPiece;

import javax.swing.*;
import java.awt.*;

public class Space extends JLabel {

    public final static ImageIcon blackPiece = new ImageIcon("resources/img/blackCheckerPiece80x80.png");
    public final static ImageIcon blackKing = new ImageIcon("resources/img/blackCheckerPieceKing80x80.png");
    public final static ImageIcon redPiece = new ImageIcon("resources/img/redCheckerPiece80x80.png");
    public final static ImageIcon redKing = new ImageIcon("resources/img/redCheckerPieceKing80x80.png");
    public final static ImageIcon selectedBlackPiece = new ImageIcon("resources/img/blackCheckerPieceSelected.png");
    public final static ImageIcon selectedBlackKing = new ImageIcon("resources/img/blackCheckerPieceKingSelected.png");
    public final static ImageIcon selectedRedPiece = new ImageIcon("resources/img/redCheckerPieceSelected.png");
    public final static ImageIcon selectedRedKing = new ImageIcon("resources/img/redCheckerPieceKingSelected.png");

    private int xCoordinate;
    private int yCoordinate;
    private boolean isPlayable;
    private CheckerPiece piece;

    public Space(int yCoordinate, int xCoordinate, boolean isPlayable) {
        this.yCoordinate = yCoordinate;
        this.xCoordinate = xCoordinate;
        this.isPlayable = isPlayable;
        initializeLabel();
    }

    private void initializeLabel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(30, 30));
        if(isPlayable()) {
            setBackground(new Color(78, 49, 36));
        } else {
            setBackground(new Color(212, 164, 114));
        }
        setOpaque(true);
        setHorizontalAlignment(JLabel.CENTER);
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public boolean isPlayable() {
        return isPlayable;
    }

    public CheckerPiece getPiece() {
        return piece;
    }

    public void setPiece(CheckerPiece piece) {
        this.piece = piece;
        String color = piece.getColor();
        if(("black".equals(color) && this.yCoordinate == 8) || ("red".equals(color) && this.yCoordinate == 1)) {
            piece.setKing(true);
        }
        changeIcon(false);
    }

    public void changeIcon(boolean selected) {
        String color = piece.getColor();
        if(piece.isKing()) {
            if ("black".equals(color)) {
                setIcon(selected ? selectedBlackKing : blackKing);
            } else {
                setIcon(selected ? selectedRedKing : redKing);
            }
        } else {
            if ("black".equals(color)) {
                setIcon(selected ? selectedBlackPiece : blackPiece);
                CheckerBoardPanel.status = "Black piece selected for movement";
                //CheckerBoardPanel.updateStatus();
            } else {
                setIcon(selected ? selectedRedPiece : redPiece);
            }
        }
    }

    public CheckerPiece removePiece() {
        CheckerPiece removed = piece;
        piece = null;
        setIcon(null);
        return removed;
    }
}
