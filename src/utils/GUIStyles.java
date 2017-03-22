package utils;

import components.Space;
import eventListeners.SpaceClickListener;
import main.Main;
import objects.CheckerPiece;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Utility class to manage image file locations and background colors
 *
 * @author Chase Erickson
 * @author Joseph Hage
 * @author Anthony Tuel
 * 2017-03-18
 */
public abstract class GUIStyles {

    private static boolean blackLightModeEnabled;

    /**
     *
     * @param blackLightModeEnabled
     */
    public static void setBlackLightModeEnabled(boolean blackLightModeEnabled) {
        GUIStyles.blackLightModeEnabled = blackLightModeEnabled;
        Space[][] spaces;
        if ((spaces = Main.checkerBoard.getSpaces()) != null) {
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
        Main.frame.repaint();
    }

    /**
     *
     * @param isPlayable
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
     *
     * @param player
     * @param selected
     * @param isKing
     * @return
     */
    public static ImageIcon choosePieceIcon(int player, boolean selected, boolean isKing) {
        return new ImageIcon("resources/img/p" + player + (selected ? "Selected" : "") +
                (isKing ? "King" : "") + "Piece" + (blackLightModeEnabled ? "BL" : "") + ".png");
    }
}
