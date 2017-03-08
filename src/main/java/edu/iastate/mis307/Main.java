package edu.iastate.mis307;

import edu.iastate.mis307.objects.Board;
import edu.iastate.mis307.objects.CheckerPiece;
import edu.iastate.mis307.objects.Location;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        ImageIcon blackPiece = new ImageIcon(ClassLoader.getSystemResource("img/blackCheckerPiece50x50.png"));
        ImageIcon redPiece = new ImageIcon(ClassLoader.getSystemResource("img/redCheckerPiece50x50.png"));
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 8));

        Board board = new Board();
        Location[][] locations = board.getSpaces();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Location location = locations[i][j];
                CheckerPiece piece;
                if((piece = location.getPiece()) != null) {
                    if("black".equals(piece.getColor())) {
                        panel.add(new JLabel(blackPiece));
                    } else {
                        panel.add(new JLabel(redPiece));
                    }
                } else {
                    panel.add(new JLabel("-----X-----"));
                }
            }
        }

        frame.add(panel);
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
