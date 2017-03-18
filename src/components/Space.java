package components;

import main.Main;
import objects.CheckerPiece;
import utils.ImageIcons;
import utils.SoundPlayer;

import javax.swing.*;
import java.awt.*;

/**
 * Class extending JLabel to act as a GUI component.
 * Contains variables for the space's coordinates and a CheckerPiece object if it has one
 *
 * @author Chase Erickson
 * @author Joseph Hage
 * @author Anthony Tuel
 * 2017-03-06
 */
public class Space extends JLabel {

    private int xCoordinate;
    private int yCoordinate;
    private CheckerPiece piece;

    Space(int yCoordinate, int xCoordinate, boolean isPlayable) {
        this.yCoordinate = yCoordinate;
        this.xCoordinate = xCoordinate;
        initializeLabel(isPlayable);
    }

    private void initializeLabel(boolean isPlayable) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(20, 20));
        if(isPlayable) {
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

    public CheckerPiece getPiece() {
        return piece;
    }

    void setPiece(CheckerPiece piece) {
        this.piece = piece;
        String color = piece.getColor();
        if(!piece.isKing() && (("black".equals(color) && this.yCoordinate == 8) ||
                ("red".equals(color) && this.yCoordinate == 1))) {
            piece.setKing();
            SoundPlayer.kingSoundEffect();
        }
        changeIcon(false);
    }

    public void changeIcon(boolean selected) {
        String color = piece.getColor();
        if(piece.isKing()) {
            if ("black".equals(color)) {
                setIcon(selected ? ImageIcons.BLACK_KING_SELECTED : ImageIcons.BLACK_KING);
                if(Main.checkerBoard.statusLabel != null) {
                    Main.checkerBoard.statusLabel.setText("Black king piece selected for movement");
                }
            } else {
                setIcon(selected ? ImageIcons.RED_KING_SELECTED : ImageIcons.RED_KING);
                if(Main.checkerBoard.statusLabel != null) {
                    Main.checkerBoard.statusLabel.setText("Red king piece selected for movement");
                }
            }
        } else {
            if ("black".equals(color)) {
                setIcon(selected ? ImageIcons.BLACK_PIECE_SELECTED : ImageIcons.BLACK_PIECE);
                if(Main.checkerBoard.statusLabel != null) {
                    Main.checkerBoard.statusLabel.setText("Black piece selected for movement");
                }
            } else {
                setIcon(selected ? ImageIcons.RED_PIECE_SELECTED : ImageIcons.RED_PIECE);
                if(Main.checkerBoard.statusLabel != null) {
                    Main.checkerBoard.statusLabel.setText("Red piece selected for movement");
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
