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

    public static Space selected;
    private static boolean multipleJumps;
    public static Space[] highlightedSpaces;

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!Main.checkerBoard.gameOver) {
            Space space = (Space) e.getComponent();
            CheckerPiece piece;
            if ((piece = space.getPiece()) != null && piece.getPlayer() == Main.checkerBoard.currentPlayer && !multipleJumps) {
                if (!space.equals(selected)) {
                    resetSelected();
                    if(piece.getValidMoves() != null && piece.getValidMoves().length > 0) {
                        space.changeIcon(true);
                        highlightValidMoveSpaces(space, true);
                        selected = space;
                    }
                }
            } else if (space.getPiece() == null && selected != null && selected.getPiece() != null) {
                Space[] validMoves = selected.getPiece().getValidMoves();
                if(Arrays.asList(validMoves).contains(space)) {
                    int fromY = selected.getYCoordinate();
                    int fromX = selected.getXCoordinate();
                    int toY = space.getYCoordinate();
                    int toX = space.getXCoordinate();
                    if (Moves.isMoveAJump(fromY, fromX, toY, toX)) {
                        jumpPiece(fromY, fromX, toY, toX);
                        return;
                    } else {
                        Main.checkerBoard.movePiece(fromY, fromX, toY, toX);
                        changePlayer();
                        return;
                    }
                }
                if(!multipleJumps) {
                    resetSelected();
                }
            } else if (!multipleJumps) {
                resetSelected();
            }
        }
    }

    private static void highlightValidMoveSpaces(Space space, boolean highlighted) {
        Space[] validMoves = highlighted ? space.getPiece().getValidMoves() : highlightedSpaces;
        for (Space validMove : validMoves) {
            validMove.setBackground(highlighted ? GUIStyles.chooseSpaceHighlightColor() :
                    GUIStyles.chooseSpaceBackgroundColor(true));
        }
        highlightedSpaces = highlighted ? validMoves : null;
    }

    private static void resetSelected() {
        if (selected != null && selected.getPiece() != null) {
            selected.changeIcon(false);
            selected = null;
        }
        if(highlightedSpaces != null) {
            highlightValidMoveSpaces(selected, false);
        }
    }

    private static void jumpPiece(int fromY, int fromX, int toY, int toX) {
        Main.checkerBoard.removePiece((toY + fromY) / 2, (toX + fromX) / 2);
        boolean isKing = selected.getPiece().isKing();
        resetSelected();
        selected = Main.checkerBoard.movePiece(fromY, fromX, toY, toX);
        CheckerPiece piece = selected.getPiece();
        if (!isKing && piece.isKing()) {
            changePlayer();
        } else {
            selected.changeIcon(true);
            Space[] newValidMoves = new Space[4];
            int i = 0;
            Moves.getValidMoves(Main.checkerBoard.getSpaces(), selected);
            for (Space newMove : piece.getValidMoves()) {
                if (Moves.isMoveAJump(selected.getYCoordinate(), selected.getXCoordinate(),
                        newMove.getYCoordinate(), newMove.getXCoordinate())) {
                    newValidMoves[i] = newMove;
                    i++;
                }
            }
            newValidMoves = Arrays.copyOf(newValidMoves, i);
            piece.setValidMoves(newValidMoves);
            if (newValidMoves.length > 0) {
                highlightValidMoveSpaces(selected, true);
                multipleJumps = true;
            } else {
                changePlayer();
            }
        }
    }

    private static void changePlayer() {
        resetSelected();
        multipleJumps = false;
        Main.checkerBoard.changePlayer(false);
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
