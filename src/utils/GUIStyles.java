package utils;

import components.Space;
import eventListeners.SpaceClickListener;
import objects.CheckerPiece;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static utils.GameVariables.blackLightModeEnabled;
import static utils.GameVariables.checkerBoard;
import static utils.GameVariables.gameFrame;

/**
 * Utility class to manage image file locations and background colors
 *
 * @author Chase Erickson
 * @author Joseph Hage
 * @author Anthony Tuel
 * 2017-03-18
 */
public abstract class GUIStyles {

    /**
     * Method to check whether or not blackLightMode is enabled on, and if so, re-configures the pieces
     * accordingly to the colors we configured for black light mode.
     * @param blackLightMode - Toggle for whether the black light mode is enabled or not.
     */
    public static void setBlackLightModeEnabled(boolean blackLightMode) {
        blackLightModeEnabled = blackLightMode;
        Space[][] spaces;
        if ((spaces = checkerBoard.getSpaces()) != null) {
            for (Space[] rowSpaces : spaces) {
                for (Space space : rowSpaces) {
                    if(SpaceClickListener.highlightedSpaces != null &&
                            Arrays.asList(SpaceClickListener.highlightedSpaces).contains(space)) {
                        space.setBackground(chooseSpaceHighlightColor());
                    } else {
                        space.setBackground(chooseSpaceBackgroundColor(space.isPlayable()));
                    }
                    CheckerPiece piece;
                    if ((piece = space.getPiece()) != null) {
                        space.setIcon(choosePieceIcon(piece.getPlayer(), space.equals(SpaceClickListener.selected),
                                piece.isKing()));
                    }
                }
            }
        }
        gameFrame.repaint();
    }

    /**
     * If the blackLightMode isn't enabled, then set the blackground color to the default.
     * Otherwise, configure it so that the background color matches our blacklight scheme.
     * @param isPlayable - Checking to see if the space is a moveable space.
     * @return
     */
    public static Color chooseSpaceBackgroundColor(boolean isPlayable) {
        if(!blackLightModeEnabled) {
            return isPlayable ? new Color(78, 49, 36) : new Color(212, 164, 114);
        } else {
            return isPlayable ? new Color(0, 0, 0) : new Color(28, 232, 255);
        }
    }

    /**
     *
     * @return Color that corresponds to the current mode the game is in, regular or black light mode.
     */
    public static Color chooseSpaceHighlightColor() {
        return !blackLightModeEnabled ? new Color(255, 255, 255) : new Color(26, 255, 22);
    }

    /**
     * Method for choosing the correct piece icon for the gameboard.
     * @param player - Checking for the correct player (Either 1 or 2)
     * @param selected - If the piece if selected, we add a border around the piece
     * @param isKing - If the piece is a king, we have a unique piece icon for kings
     * @return
     */
    public static ImageIcon choosePieceIcon(int player, boolean selected, boolean isKing) {
        return new ImageIcon("resources/img/p" + player + (selected ? "Selected" : "") +
                (isKing ? "King" : "") + "Piece" + (blackLightModeEnabled ? "BL" : "") + ".png");
    }
}
