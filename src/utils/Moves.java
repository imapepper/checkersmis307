package utils;

import components.Space;
import main.Main;
import objects.CheckerPiece;

import java.util.Arrays;

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

    public static boolean forceJumpEnabled;

    private static Space isValidMove(Space space, int changeY, int changeX) {
        int yCoordinate = space.getYCoordinate() - 1;
        int xCoordinate = space.getXCoordinate() - 1;
        CheckerPiece piece = space.getPiece();
        int player = piece.getPlayer();

        if(yCoordinate + changeY >= 0 && yCoordinate + changeY < 8 &&
                xCoordinate + changeX >= 0 && xCoordinate + changeX < 8) {
            Space newSpace = Main.checkerBoard.getSpaces()[yCoordinate + changeY][xCoordinate + changeX];
            CheckerPiece newPiece;
            if((newPiece = newSpace.getPiece()) == null) {
                return newSpace;
            } else if(player != newPiece.getPlayer() && Math.abs(changeY) < 2 && Math.abs(changeX) < 2) {
                return isValidMove(space, changeY * 2, changeX * 2);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static Space[] getValidMoves(Space space) {
        if(space.getPiece() == null) {
            throw new IllegalArgumentException("There is no piece to move on that space");
        }

        CheckerPiece piece = space.getPiece();
        int player = piece.getPlayer();
        boolean isKing = piece.isKing();
        Space[] openSpaces = new Space[4];
        int i = 0;

        if(player == 2 || isKing) {
            Space aboveLeft = isValidMove(space, -1, -1);
            if(aboveLeft != null) {
                openSpaces[i] = aboveLeft;
                i++;
            }
            Space aboveRight = isValidMove(space, -1, 1);
            if(aboveRight != null) {
                openSpaces[i] = aboveRight;
                i++;
            }
        }
        if(player == 1 || isKing) {
            Space belowLeft = isValidMove(space, 1, -1);
            if(belowLeft != null) {
                openSpaces[i] = belowLeft;
                i++;
            }
            Space belowRight = isValidMove(space, 1, 1);
            if(belowRight != null) {
                openSpaces[i] = belowRight;
                i++;
            }
        }
        return Arrays.copyOf(openSpaces, i);
    }

    public static boolean isMoveAJump(int fromY, int fromX, int toY, int toX) {
        return Math.abs(toY - fromY) == 2 && Math.abs(toX - fromX) == 2;
    }
}