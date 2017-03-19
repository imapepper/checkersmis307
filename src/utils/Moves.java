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

    public static void findAllMovesForPlayer(int player) {
        Space[][] spaces = Main.checkerBoard.getSpaces();
        for (Space[] rowSpaces : spaces) {
            for (Space space : rowSpaces) {
                CheckerPiece piece;
                if ((piece = space.getPiece()) != null && piece.getPlayer() == player) {
                    getValidMoves(spaces, space);
                }
            }
        }
    }

    public static void getValidMoves(Space[][] spaces, Space space) {
        CheckerPiece piece = space.getPiece();
        int player = piece.getPlayer();
        boolean isKing = piece.isKing();
        Space[] openSpaces = new Space[4];
        int i = 0;

        if(player == 2 || isKing) {
            Space aboveLeft = isValidMove(spaces, space, -1, -1);
            if(aboveLeft != null) {
                openSpaces[i] = aboveLeft;
                i++;
            }
            Space aboveRight = isValidMove(spaces, space, -1, 1);
            if(aboveRight != null) {
                openSpaces[i] = aboveRight;
                i++;
            }
        }
        if(player == 1 || isKing) {
            Space belowLeft = isValidMove(spaces, space, 1, -1);
            if(belowLeft != null) {
                openSpaces[i] = belowLeft;
                i++;
            }
            Space belowRight = isValidMove(spaces, space, 1, 1);
            if(belowRight != null) {
                openSpaces[i] = belowRight;
                i++;
            }
        }
        piece.setValidMoves(Arrays.copyOf(openSpaces, i));
    }

    public static boolean isMoveAJump(int fromY, int fromX, int toY, int toX) {
        return Math.abs(toY - fromY) == 2 && Math.abs(toX - fromX) == 2;
    }

    private static Space isValidMove(Space[][] spaces, Space space, int changeY, int changeX) {
        int yCoordinate = space.getYCoordinate() - 1;
        int xCoordinate = space.getXCoordinate() - 1;
        CheckerPiece piece = space.getPiece();
        int player = piece.getPlayer();

        if(yCoordinate + changeY >= 0 && yCoordinate + changeY < 8 &&
                xCoordinate + changeX >= 0 && xCoordinate + changeX < 8) {
            Space newSpace = spaces[yCoordinate + changeY][xCoordinate + changeX];
            CheckerPiece newPiece;
            if((newPiece = newSpace.getPiece()) == null) {
                return newSpace;
            } else if(player != newPiece.getPlayer() && Math.abs(changeY) < 2 && Math.abs(changeX) < 2) {
                return isValidMove(spaces, space, changeY * 2, changeX * 2);
            }
        }
        return null;
    }
}