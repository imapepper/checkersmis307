package utils;

import components.Space;
import objects.CheckerPiece;

import java.util.Arrays;

import static utils.GameVariables.checkerBoard;
import static utils.GameVariables.forceJumpEnabled;

/**
 * Utility class to determine moves that a player is allowed to make
 * during their current turn.
 *
 * @author Chase Erickson
 * @author Joseph Hage
 * @author Anthony Tuel
 * 2017-03-18
 */
public abstract class Moves {

    public static Space[] jumpMoves;

    /**
     * Large method to discover all the available moves for a player. Checks to make sure
     * that a piece is not null and that the piece being selected to move is for the correct
     * player.
     * 
     * Adds the valid moves to an array to assist with our "no moves left" forfeit function.
     * @param player - Ensure we are finding the moves for the correct player.
     * @return
     */
    public static Space[] findAllMovesForPlayer(int player) {
        Space[] movesForPlayer;
        if (forceJumpEnabled) {
            jumpMoves = new Space[0];
        }
        Space[][] spaces = checkerBoard.getSpaces();
        movesForPlayer = new Space[0];
        for (Space[] rowSpaces : spaces) {
            for (Space space : rowSpaces) {
                CheckerPiece piece;
                if ((piece = space.getPiece()) != null && piece.getPlayer() == player) {
                    Space[] movesForPiece = getValidMoves(spaces, space);
                    int currentNumMoves = movesForPlayer.length;
                    movesForPlayer = Arrays.copyOf(movesForPlayer, currentNumMoves + movesForPiece.length);
                    System.arraycopy(movesForPiece, 0, movesForPlayer, currentNumMoves, movesForPiece.length);
                }
            }
        }
        return movesForPlayer;
    }

    /**
     * Methodology to ensure that the moves able to be made are "valid" moves.
     * If the piece is a king, then it can move all sorts of directions. We add the 
     * open spaces to an array. 
     * @param spaces
     * @param space
     * @return
     */
    public static Space[] getValidMoves(Space[][] spaces, Space space) {
        CheckerPiece piece = space.getPiece();
        int player = piece.getPlayer();
        boolean isKing = piece.isKing();
        Space[] openSpaces = new Space[4];
        int i = 0;
        if (forceJumpEnabled) piece.resetJumpMoves();

        if (player == 2 || isKing) {
            Space aboveLeft = isValidMove(spaces, space, -1, -1);
            if(aboveLeft != null) {
                openSpaces[i] = aboveLeft;
                i++;
            }
            Space aboveRight = isValidMove(spaces, space, -1, 1);
            if (aboveRight != null) {
                openSpaces[i] = aboveRight;
                i++;
            }
        }
        if (player == 1 || isKing) {
            Space belowLeft = isValidMove(spaces, space, 1, -1);
            if (belowLeft != null) {
                openSpaces[i] = belowLeft;
                i++;
            }
            Space belowRight = isValidMove(spaces, space, 1, 1);
            if (belowRight != null) {
                openSpaces[i] = belowRight;
                i++;
            }
        }
        openSpaces = Arrays.copyOf(openSpaces, i);
        piece.setValidMoves(openSpaces);
        return openSpaces;
    }

    /**
     * Method to do the math for whether or not a move is a jump. 
     * @param fromY - The Y coordinate of where the piece is moving from
     * @param fromX - The X coordinate of where the piece is moving from
     * @param toY - The Y coordinate of where the piece is moving to
     * @param toX - The X coordinate of where the piece is moving to
     * @return
     */
    public static boolean isMoveAJump(int fromY, int fromX, int toY, int toX) {
        return Math.abs(toY - fromY) == 2 && Math.abs(toX - fromX) == 2;
    }

    /**
     * Math to check if a piece is able to moved in a valid way.
     * @param spaces - Array of the spaces on the board.
     * @param space - Space that the piece is currently on.
     * @param changeY - Making sure that we cannot change the Y more than the board's max Y.
     * @param changeX - Making sure that we cannot change the X more than the board's max X.
     * @return
     */
    private static Space isValidMove(Space[][] spaces, Space space, int changeY, int changeX) {
        int yCoordinate = space.getYCoordinate() - 1;
        int xCoordinate = space.getXCoordinate() - 1;
        CheckerPiece piece = space.getPiece();
        int player = piece.getPlayer();

        if (yCoordinate + changeY >= 0 && yCoordinate + changeY < 8 &&
                xCoordinate + changeX >= 0 && xCoordinate + changeX < 8) {
            Space newSpace = spaces[yCoordinate + changeY][xCoordinate + changeX];
            CheckerPiece newPiece;
            if ((newPiece = newSpace.getPiece()) != null) {
                if (player != newPiece.getPlayer() && Math.abs(changeY) < 2 && Math.abs(changeX) < 2) {
                    return isValidMove(spaces, space, changeY * 2, changeX * 2);
                }
            } else {
                if (forceJumpEnabled && Math.abs(changeX) == 2 && Math.abs(changeY) == 2) {
                    piece.addJumpMove(newSpace);
                    if (!Arrays.asList(jumpMoves).contains(space)) {
                        jumpMoves = Arrays.copyOf(jumpMoves, jumpMoves.length + 1);
                        jumpMoves[jumpMoves.length - 1] = space;
                    }
                }
                return newSpace;
            }
        }
        return null;
    }
}