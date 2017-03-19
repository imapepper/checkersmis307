package objects;

/**
 * Class that represents a CheckerPiece object.
 * Each CheckerPiece has a String player and a boolean king status
 *
 * @author Chase Erickson
 * @author Joseph Hage
 * @author Anthony Tuel
 * 2017-03-06
 */
public class CheckerPiece {
    private int player;
    private boolean isKing;

    /**
     * CheckerPiece constructor; isKing is always false initially
     *
     * @param player String parameter to set the initial player of a piece
     *              Player can be 1 or 2 and cannot be changed after set by constructor
     */
    public CheckerPiece(int player) {
        this.player = player;
        isKing = false;
    }

    /**
     * Get method to retrieve CheckerPiece player
     *
     * @return String player
     */
    public int getPlayer() {
        return player;
    }

    /**
     * Get method to check if CheckerPiece is a regular or king piece
     *
     * @return boolean isKing
     */
    public boolean isKing() {
        return isKing;
    }

    /**
     * Set method for when a CheckerPiece becomes a king
     * Once a piece is a king, it cannot be set to a regular piece again
     */
    public void setKing() {
        isKing = true;
    }
}
