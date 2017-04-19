package objects;

import components.Space;

import java.util.Arrays;

import static utils.GameVariables.forceJumpEnabled;

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
    private Space[] validMoves;
    private Space[] jumpMoves;

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

    /**
     * Get all valid moves for the selected piece
     * @return the jumpMoves if force enabled, otherwise return validMoves
     */
    public Space[] getValidMoves() {
        if(forceJumpEnabled && jumpMoves != null) {
            return jumpMoves;
        } else {
            return validMoves;
        }
    }

    /**
     * Sets all the valid moves that were discovered by getValidMoves
     * @param validMoves validMoves stored in the Space[] array
     */
    public void setValidMoves(Space[] validMoves) {
        this.validMoves = validMoves;
    }

    /**
     * Sets jumpMoves to null after the players turn has ended
     */
    public void resetJumpMoves() {
        jumpMoves = null;
    }

    /**
     * If there were no jump moves, add a jump mpve Space to the jumpMoves Space[]
     * Otherwise, copy jumpMoves array and the jump length + 1
     * Lastly, set the local jumpMpve equal to jumpMoves array with jumpMoves length - 1
     * @param jumpMove parameter for whether the space is a jump move or not
     */
    public void addJumpMove(Space jumpMove) {
        if(jumpMoves == null) {
            jumpMoves = new Space[1];
        } else {
            jumpMoves = Arrays.copyOf(jumpMoves, jumpMoves.length + 1);
        }
        jumpMoves[jumpMoves.length - 1] = jumpMove;
    }
}
