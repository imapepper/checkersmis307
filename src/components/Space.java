package components;

import objects.CheckerPiece;
import utils.GUIStyles;
import utils.SoundPlayer;

import javax.swing.*;
import java.awt.*;

import static utils.GameVariables.checkerBoard;

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

    /**
     * Constructs the spaces on the board. 
     * @param yCoordinate - Sets the Y coordinate of each piece. 
     * @param xCoordinate - Sets the X coordinate of each piece.
     * @param isPlayable - Sets the board to playable. 
     */
    public Space(int yCoordinate, int xCoordinate, boolean isPlayable) {
        this.yCoordinate = yCoordinate;
        this.xCoordinate = xCoordinate;
        this.isPlayable = isPlayable;
        initializeLabel();
    }

    /**
     * Initializes our game board labels. 
     */
    private void initializeLabel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(20, 20));
        setBackground(GUIStyles.chooseSpaceBackgroundColor(isPlayable));
        setOpaque(true);
        setHorizontalAlignment(JLabel.CENTER);
    }

    /**
     * 
     * @return the xCoordinate when requested
     */
    public int getXCoordinate() {
        return xCoordinate;
    }

    /**
     * 
     * @return the yCoordinate when requested
     */
    public int getYCoordinate() {
        return yCoordinate;
    }

    /**
     * 
     * @return whether the board is playable or not
     */
    public boolean isPlayable() {
        return isPlayable;
    }

    /**
     * 
     * @return the piece that is being requested
     */
    public CheckerPiece getPiece() {
        return piece;
    }

    /**
     * Just sets so that the space "contains" the piece
     * @param piece - The game piece that "exists"
     */
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

    /**
     * Change the icon if the piece becomes a king
     * Also handles the setting of the status label when a player selects a checker piece
     * @param selected - Checks whether or not the piece is selected
     */
    public void changeIcon(boolean selected) {
        int player = piece.getPlayer();
        boolean isKing = piece.isKing();
        setIcon(GUIStyles.choosePieceIcon(player, selected, isKing));
        if(checkerBoard.statusLabel != null) {
            if(selected) {
                checkerBoard.statusLabel.setText("Player " + player + " selected a" + (isKing ? " king " : " ")
                        + "piece for movement");
            } else {
                checkerBoard.statusLabel.setText("Player " + player + "\'s turn!");
            }
        }
    }

    /**
     * Remove the piece by making it a null
     * @return the removed piece, making it non-existent 
     */
    CheckerPiece removePiece() {
        CheckerPiece removed = piece;
        piece = null;
        setIcon(null);
        return removed;
    }
}
