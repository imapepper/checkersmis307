package edu.iastate.mis307;

import edu.iastate.mis307.objects.Board;
import edu.iastate.mis307.objects.CheckerPiece;
import edu.iastate.mis307.objects.Location;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        Vector<JLabel> jLabels = new Vector<>(64, 64);
        panel.setLayout(new GridBagLayout());

        ImageIcon blackPiece = new ImageIcon(ClassLoader.getSystemResource("img/blackCheckerPiece80x80.png"));
        ImageIcon redPiece = new ImageIcon(ClassLoader.getSystemResource("img/redCheckerPiece80x80.png"));

        Board board = new Board();
        Location[][] locations = board.getSpaces();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Location location = locations[i][j];
                CheckerPiece piece;
                GridBagConstraints constraints = new GridBagConstraints(j, i, 1, 1,
                        0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 40, 40);
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
                panel.add(label, constraints);
                jLabels.addElement(label);
            }
        }
        frame.add(panel);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
