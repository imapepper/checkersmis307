package edu.iastate.mis307;

import edu.iastate.mis307.components.CheckerBoardPanel;
import edu.iastate.mis307.objects.Board;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Board board = new Board();
        CheckerBoardPanel panel = new CheckerBoardPanel(board);

        frame.add(panel);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
