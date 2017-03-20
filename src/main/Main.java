package main;

import components.CheckerBoardPanel;
import eventListeners.FrameListener;

import javax.swing.*;
import java.awt.*;

/**
 * Runnable class for program and GUI
 *
 * @author Chase Erickson
 * @author Joseph Hage
 * @author Anthony Tuel
 * 2017-03-06
 */
public class Main {

    public static JFrame frame;
    public static CheckerBoardPanel checkerBoard;

    /**
     * Main method for program and GUI
     *
     * @param args arguments to run main with
     */
    public static void main(String[] args) {
        frame = new JFrame();
        frame.addWindowListener(new FrameListener());
        checkerBoard = new CheckerBoardPanel();
        checkerBoard.createNewBoard();

        frame.add(checkerBoard);
        frame.setSize(800, 800);
        frame.setMinimumSize(new Dimension(800, 800));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    	frame.setJMenuBar(checkerBoard.menuBar);
    }
}
