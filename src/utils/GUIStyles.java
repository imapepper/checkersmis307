package utils;

import components.Space;
import main.Main;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class to manage image file locations
 *
 * @author Chase Erickson
 * @author Joseph Hage
 * @author Anthony Tuel
 * 2017-03-18
 */
public abstract class GUIStyles {

    private static boolean blackLightModeEnabled;

    public static void setBlackLightModeEnabled(boolean blackLightModeEnabled) {
        Main.checkerBoard.setVisible(false);
        GUIStyles.blackLightModeEnabled = blackLightModeEnabled;
        Space[][] spaces;
        if ((spaces = Main.checkerBoard.getSpaces()) != null) {
            for (Space[] rowSpaces : spaces) {
                for (Space space : rowSpaces) {
                    space.setBackground(chooseSpaceBackgroundColor(space.isPlayable()));
                    if (space.getPiece() != null) {
                        space.changeIcon(false);
                    }
                }
            }
        }
        Main.checkerBoard.setVisible(true);
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
