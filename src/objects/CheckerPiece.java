package objects;

/**
 * Class that represents a CheckerPiece object.
 * Each CheckerPiece has a String color and a boolean king status
 *
 * @author Chase Erickson
 * @author Joseph Hage
 * @author Anthony Tuel
 * 2017-03-06
 */
public class CheckerPiece {
    private String color;
    private boolean isKing;

    /**
     * CheckerPiece constructor; isKing is always false initially
     *
     * @param color String parameter to set the initial color of a piece
     *              Color can be "black" or "red" and cannot be changed after set by constructor
     */
    public CheckerPiece(String color) {
        this.color = color;
        isKing = false;
    }

    /**
     * Get method to retrieve CheckerPiece color
     *
     * @return String color
     */
    public String getColor() {
        return color;
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
