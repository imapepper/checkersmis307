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
                        space.setIcon(choosePieceIcon(piece.getPlayer(), piece.isKing(),
                                space.equals(SpaceClickListener.selected)));
                    }
                }
            }
        }
        Main.frame.repaint();
    }

    public static Color chooseSpaceBackgroundColor(boolean isPlayable) {
        if(!blackLightModeEnabled) {
            return isPlayable ? new Color(78, 49, 36) : new Color(212, 164, 114);
        } else {
            return isPlayable ? new Color(0, 0, 0) : new Color(255, 255, 255); // TODO choose black light background colors
        }
    }

    public static Color chooseSpaceHighlightColor() {
        return !blackLightModeEnabled ? new Color(255, 255, 255) : new Color(170, 255, 1);
    }

    public static ImageIcon choosePieceIcon(int player, boolean isKing, boolean selected) {
        if(!blackLightModeEnabled) {
            return new ImageIcon("resources/img/player"+player+"CheckerPiece" + (isKing ? "King" : "") +
                    (selected ? "Selected" : "") + ".png");
        } else {
            return new ImageIcon(""); // TODO choose black light piece images
        }
    }
}
