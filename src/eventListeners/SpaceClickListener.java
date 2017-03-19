package eventListeners;

import components.Space;
import main.Main;
import objects.CheckerPiece;
import utils.GUIStyles;
import utils.Moves;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

/**
 * Event listener class that extends MouseListener to interact with GUI Space components.
 * Methods are used to move a CheckerPiece between Space components.
 *
 * @author Chase Erickson
 * @author Joseph Hage
 * @author Anthony Tuel
 * 2017-03-10
 */
public class SpaceClickListener implements MouseListener {

    private static Space selected;
    private static Space[] validMoves;
    private static boolean doubleJump;

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!Main.checkerBoard.gameOver) {
            Space space = (Space) e.getComponent();
            if (validMoves != null) {
                for (Space validMove : validMoves) {
                    if (space.equals(validMove)) {
                        int fromY = selected.getYCoordinate();
                        int fromX = selected.getXCoordinate();
                        int toY = validMove.getYCoordinate();
                        int toX = validMove.getXCoordinate();
                        if (Moves.isMoveAJump(fromY, fromX, toY, toX)) {
                            jumpPiece(fromY, fromX, toY, toX);
                        } else {
                            Main.checkerBoard.movePiece(fromY, fromX, toY, toX);
                            changePlayer();
                        }
                        return;
                    }
                }
            }
            if (!doubleJump) {
                CheckerPiece piece;
                if ((piece = space.getPiece()) != null && piece.getPlayer() == Main.checkerBoard.currentPlayer) {
                    if (!space.equals(selected)) {
                        resetSelected();
                        space.changeIcon(true);
                        selected = space;
                        Space[] newValidMoves = Moves.getValidMoves((Space) e.getComponent());
                        highlightValidMoveSpaces(newValidMoves, true);
                        validMoves = newValidMoves;
                    }
                } else {
                    resetSelected();
                }
            }
        }
    }

    private static void highlightValidMoveSpaces(Space[] validMoves, boolean highlighted) {
        for (Space validMove : validMoves) {
            validMove.setBackground(highlighted ? GUIStyles.chooseSpaceHighlightColor() :
                    GUIStyles.chooseSpaceBackgroundColor(true));
        }
    }

    private static void resetSelected() {
        if (selected != null && selected.getPiece() != null) {
            selected.changeIcon(false);
            selected = null;
        }
        if (validMoves != null) {
            highlightValidMoveSpaces(validMoves, false);
            validMoves = null;
        }
    }

    private static void jumpPiece(int fromY, int fromX, int toY, int toX) {
        Main.checkerBoard.removePiece((toY + fromY) / 2, (toX + fromX) / 2);
        boolean isKing = selected.getPiece().isKing();
        resetSelected();
        selected = Main.checkerBoard.movePiece(fromY, fromX, toY, toX);
        if (!isKing && selected.getPiece().isKing()) {
            changePlayer();
        } else {
            selected.changeIcon(true);
            Space[] newMoves = Moves.getValidMoves(selected);
            Space[] newValidMoves = new Space[4];
            int i = 0;
            for (Space newMove : newMoves) {
                if (Moves.isMoveAJump(selected.getYCoordinate(), selected.getXCoordinate(),
                        newMove.getYCoordinate(), newMove.getXCoordinate())) {
                    newValidMoves[i] = newMove;
                    i++;
                }
            }
            newValidMoves = Arrays.copyOf(newValidMoves, i);
            if (newValidMoves.length > 0) {
                highlightValidMoveSpaces(newValidMoves, true);
                validMoves = newValidMoves;
                doubleJump = true;
            } else {
                changePlayer();
            }
        }
        Main.checkerBoard.checkGameOver();
    }

    private static void changePlayer() {
        resetSelected();
        doubleJump = false;
        Main.checkerBoard.changePlayer();
    }

    /**
     * Required implementation method. Not used
     *
     * @param e MouseEvent object. Not used
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Required implementation method. Not used
     *
     * @param e MouseEvent object. Not used
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Required implementation method. Not used
     *
     * @param e MouseEvent object. Not used
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Required implementation method. Not used
     *
     * @param e MouseEvent object. Not used
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
