import components.CheckerBoardPanel;
import components.Space;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        CheckerBoardPanel panel = new CheckerBoardPanel();

        frame.add(panel);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Space[][] spaces = panel.getSpaces();
        Space[] openSpaces = panel.findOpenSpaces(spaces[3][3]);
        for (Space space : openSpaces) {
            System.out.println(space.getyCoordinate() + " " + space.getxCoordinate());
        }
    }
}
