package eventListeners;

import components.CheckerBoardPanel;
import components.Space;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickListener implements MouseListener {

    private static Space selected;
    private static Space[] validMoves;

    public static Space getSelected() {
        return selected;
    }

    public static void setSelected(Space selected) {
        ClickListener.selected = selected;
    }

    public static Space[] getValidMoves() {
        return validMoves;
    }

    public static void setValidMoves(Space[] validMoves) {
        ClickListener.validMoves = validMoves;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Space space = (Space) e.getComponent();
        if(space.getPiece() != null) {
            if(selected != null) {
                selected.changeIcon(false);
            }
            if(space.equals(selected)) {
                selected.changeIcon(false);
                selected = null;
                highlightValidMoveSpaces(validMoves, new Color(78, 49, 36));
                validMoves = null;
            } else {
                space.changeIcon(true);
                selected = space;
                if(validMoves != null) {
                    highlightValidMoveSpaces(validMoves, new Color(78, 49, 36));
                }
                Space[] newValidMoves = CheckerBoardPanel.getValidMoves((Space) e.getComponent());
                highlightValidMoveSpaces(newValidMoves, new Color(255, 255, 255));
                validMoves = newValidMoves;
            }
        }
    }

    private void highlightValidMoveSpaces(Space[] validMoves, Color color) {
        for (Space validMove : validMoves) {
            validMove.setBackground(color);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
