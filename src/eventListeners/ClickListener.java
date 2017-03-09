package eventListeners;

import components.CheckerBoardPanel;
import components.Space;
import objects.CheckerPiece;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickListener implements MouseListener {

    private static Space selected;
    private static Space[] validMoves;

    @Override
    public void mouseClicked(MouseEvent e) {
        Space space = (Space) e.getComponent();
        CheckerPiece piece;
        if((piece = space.getPiece()) != null) {
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
