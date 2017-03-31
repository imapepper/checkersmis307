package eventListeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static utils.GameVariables.checkerBoard;

public class FrameListener implements WindowListener {

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {
        checkerBoard.startTimers();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
