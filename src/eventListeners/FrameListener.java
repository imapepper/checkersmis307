package eventListeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static utils.GameVariables.checkerBoard;

public class FrameListener implements WindowListener {

    @Override
    /**
     * Required implementation. Not used.
     */
    public void windowOpened(WindowEvent e) {

    }
    /**
     * Required implementation. Not used.
     */
    @Override
    public void windowClosing(WindowEvent e) {

    }
    /**
     * Required implementation. Not used.
     */
    @Override
    public void windowClosed(WindowEvent e) {

    }
    /**
     * Required implementation. Not used.
     */
    @Override
    public void windowIconified(WindowEvent e) {

    }
    /**
     * Required implementation. Not used.
     */
    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    /**
     * If the Checkerboard window is activated, then start the gameboard timers
     */
    @Override
    public void windowActivated(WindowEvent e) {
        checkerBoard.startTimers();
    }
    /**
     * Required implementation. Not used.
     */
    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
