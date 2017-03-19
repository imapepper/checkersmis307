package components;

import main.Main;
import objects.CheckerPiece;
import utils.GUIStyles;
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
        setBackground(GUIStyles.chooseSpaceBackgroundColor(isPlayable));
        setOpaque(true);
        setHorizontalAlignment(JLabel.CENTER);
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public boolean isPlayable() {
        return isPlayable;
    }

    public CheckerPiece getPiece() {
        return piece;
    }

    void setPiece(CheckerPiece piece) {
        this.piece = piece;
        int player = piece.getPlayer();
        if(!piece.isKing() && ((player == 1 && this.yCoordinate == 8) ||
                (player == 2 && this.yCoordinate == 1))) {
            piece.setKing();
            SoundPlayer.kingSoundEffect();
        }
        changeIcon(false);
    }

    public void changeIcon(boolean selected) {
        int player = piece.getPlayer();
        boolean isKing = piece.isKing();
        setIcon(GUIStyles.choosePieceIcon(player, isKing, selected));
        if(Main.checkerBoard.statusLabel != null) {
            Main.checkerBoard.statusLabel.setText("Player " + player + (isKing ? " king ": " ")
                    + "piece selected for movement");
        }
    }

    CheckerPiece removePiece() {
        CheckerPiece removed = piece;
        piece = null;
        setIcon(null);
        return removed;
    }
}
