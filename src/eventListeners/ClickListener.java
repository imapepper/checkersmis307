package eventListeners;

import components.CheckerBoardPanel;
import components.Space;
import objects.CheckerPiece;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class ClickListener implements MouseListener {

    private static Space selected;
    private static Space[] validMoves;
    private static boolean doubleJump;

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!CheckerBoardPanel.gameOver) {
            Space space = (Space) e.getComponent();
            if (validMoves != null) {
                for (Space validMove : validMoves) {
                    if (space.equals(validMove)) {
                        int fromY = selected.getYCoordinate();
                        int fromX = selected.getXCoordinate();
                        int toY = validMove.getYCoordinate();
                        int toX = validMove.getXCoordinate();
                        if (CheckerBoardPanel.isMoveAJump(fromY, fromX, toY, toX)) {
                            jumpPiece(fromY, fromX, toY, toX);
                        } else {
                            CheckerBoardPanel.movePiece(fromY, fromX, toY, toX);
                            resetSelected();
                            CheckerBoardPanel.changePlayer();
                            doubleJump = false;
                        }
                        return;
                    }
                }
            }
            if (!doubleJump) {
                CheckerPiece piece;
                if ((piece = space.getPiece()) != null && piece.getColor().equals(CheckerBoardPanel.currentPlayer)) {
                    if (!space.equals(selected)) {
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

    private void jumpPiece(int fromY, int fromX, int toY, int toX) {
        CheckerBoardPanel.removePiece((toY + fromY) / 2, (toX + fromX) / 2);
        resetSelected();
        selected = CheckerBoardPanel.movePiece(fromY, fromX, toY, toX);
        selected.changeIcon(true);
        Space[] newMoves = CheckerBoardPanel.getValidMoves(selected);
        Space[] newValidMoves = new Space[4];
        int i = 0;
        for (Space newMove : newMoves) {
            if(CheckerBoardPanel.isMoveAJump(selected.getYCoordinate(), selected.getXCoordinate(),
                    newMove.getYCoordinate(), newMove.getXCoordinate())) {
                newValidMoves[i] = newMove;
                i++;
            }
        }
        newValidMoves = Arrays.copyOf(newValidMoves, i);
        if(newValidMoves.length > 0) {
            highlightValidMoveSpaces(newValidMoves, new Color(255, 255, 255));
            validMoves = newValidMoves;
            doubleJump = true;
        } else {
            resetSelected();
            CheckerBoardPanel.changePlayer();
            doubleJump = false;
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
