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
        if(validMoves != null) {
            for (Space validMove : validMoves) {
                if (space.equals(validMove)) {
                    int selectedY = selected.getYCoordinate();
                    int selectedX = selected.getXCoordinate();
                    int moveY = validMove.getYCoordinate();
                    int moveX = validMove.getXCoordinate();
                    if(Math.abs(moveY - selectedY) == 2 && Math.abs(moveX - selectedX) == 2) {
                        CheckerBoardPanel.removePiece((moveY + selectedY) / 2, (moveX + selectedX) / 2);
                    }
                    CheckerBoardPanel.movePiece(selectedY, selectedX, moveY, moveX);
                    resetSelected();
                    CheckerBoardPanel.changePlayer();
                    return;
                }
            }
        }
        CheckerPiece piece;
        if((piece = space.getPiece()) != null && piece.getColor().equals(CheckerBoardPanel.currentPlayer)) {
            if(!space.equals(selected)) {
                resetSelected();
                space.changeIcon(true);
                selected = space;
                Space[] newValidMoves = CheckerBoardPanel.getValidMoves((Space) e.getComponent());
                highlightValidMoveSpaces(newValidMoves, new Color(255, 255, 255));
                validMoves = newValidMoves;
            }
        } else {
            resetSelected();
        }
    }

    private void highlightValidMoveSpaces(Space[] validMoves, Color color) {
        for (Space validMove : validMoves) {
            validMove.setBackground(color);
        }
    }

    private void resetSelected() {
        if(selected != null && selected.getPiece() != null) {
            selected.changeIcon(false);
            selected = null;
        }
        if(validMoves != null) {
            highlightValidMoveSpaces(validMoves, new Color(78, 49, 36));
            validMoves = null;
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
