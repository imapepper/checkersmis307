package edu.iastate.mis307.components;

import edu.iastate.mis307.objects.Board;

import javax.swing.*;
import java.awt.*;

public class CheckerBoardPanel extends JPanel {

    private Location[][] locations;

    public CheckerBoardPanel(Board board) {
        locations = board.getSpaces();
        setLayout(new GridBagLayout());
        initializeBoardGUI(board);
    }

    private void initializeBoardGUI(Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Location location = locations[i][j];
                this.add(location, createConstraints(j, i));
            }
        }
    }

    public GridBagConstraints createConstraints(int gridx, int gridy) {
        return new GridBagConstraints(gridx, gridy, 1, 1,
                0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 40, 40);
    }

    public Location[][] getLocations() {
        return locations;
    }

    public void setLocations(Location[][] locations) {
        this.locations = locations;
    }
}