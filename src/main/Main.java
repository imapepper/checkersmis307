package main;

import components.CheckerBoardPanel;

import javax.swing.*;

public class Main {

    public static JFrame frame;
    public static CheckerBoardPanel checkerBoard;

    public static void main(String[] args) {
        frame = new JFrame();
        checkerBoard = new CheckerBoardPanel();
        checkerBoard.createNewBoard();

        frame.add(checkerBoard);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
