package edu.iastate.mis307;

import edu.iastate.mis307.objects.Board;
import edu.iastate.mis307.objects.CheckerPiece;
import edu.iastate.mis307.objects.Location;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        ImageIcon blackPiece = new ImageIcon(ClassLoader.getSystemResource("img/blackCheckerPiece80x80.png"));
        ImageIcon redPiece = new ImageIcon(ClassLoader.getSystemResource("img/redCheckerPiece80x80.png"));
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 8));

        Board board = new Board();
        Location[][] locations = board.getSpaces();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Location location = locations[i][j];
                CheckerPiece piece;
                JLabel label = new JLabel();
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                label.setPreferredSize(new Dimension(50, 50));

                if(location.isPlayable()) {
                    label.setBackground(new Color(78, 49, 36));
                } else {
                    label.setBackground(new Color(212, 164, 114));
                }
                if((piece = location.getPiece()) != null) {
                    if("black".equals(piece.getColor())) {
                        label.setIcon(blackPiece);
                    } else {
                        label.setIcon(redPiece);
                    }
                }

                label.setOpaque(true);
                label.setHorizontalAlignment(JLabel.CENTER);
                panel.add(label);
            }
        }
        frame.add(panel);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
