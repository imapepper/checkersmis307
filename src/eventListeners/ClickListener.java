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
            space.changeIcon(true);
            selected = space;
            if(validMoves != null) {
                for (Space validMove : validMoves) {
                    validMove.setBackground(new Color(78, 49, 36));
                }
            }
            Space[] newValidMoves = CheckerBoardPanel.getValidMoves((Space) e.getComponent());
            for(Space validMove : newValidMoves) {
                validMove.setBackground(new Color(255, 255, 255));
            }
            validMoves = newValidMoves;
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
