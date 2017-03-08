package edu.iastate.mis307.components;

import edu.iastate.mis307.objects.CheckerPiece;

import javax.swing.*;
import java.awt.*;

public class Space extends JLabel {

    public final static ImageIcon blackPiece = new ImageIcon(ClassLoader.getSystemResource("img/blackCheckerPiece80x80.png"));
    public final static ImageIcon blackKing = new ImageIcon(ClassLoader.getSystemResource("img/blackCheckerPieceKing80x80.png"));
    public final static ImageIcon redPiece = new ImageIcon(ClassLoader.getSystemResource("img/redCheckerPiece80x80.png"));
    public final static ImageIcon redKing = new ImageIcon(ClassLoader.getSystemResource("img/redCheckerPieceKing80x80.png"));

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
        if(piece.isKing()) {
            if ("black".equals(piece.getColor())) {
                setIcon(blackKing);
            } else {
                setIcon(redKing);
            }
        } else {
            if ("black".equals(piece.getColor())) {
                setIcon(blackPiece);
            } else {
                setIcon(redPiece);
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
