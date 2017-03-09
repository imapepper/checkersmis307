package eventListeners;

import components.CheckerBoardPanel;
import components.Space;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        Space[] spaces = CheckerBoardPanel.getValidMoves((Space) e.getComponent());
        for (Space space : spaces) {
            System.out.println("Y - " + space.getyCoordinate() + " X - " + space.getxCoordinate());
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
