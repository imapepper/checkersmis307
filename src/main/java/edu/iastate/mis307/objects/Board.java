package edu.iastate.mis307.objects;

import edu.iastate.mis307.components.Location;

public class Board {

    private Location[][] spaces;

    public Board() {
        spaces = new Location[8][8];
        initializeSpaces();
    }

    private void initializeSpaces() {
        int index = 0;
        while (index < 32) {
            for (int i = 0; i < 8; i++) {
                int start;
                if (i % 2 == 1) {
                    start = 0;
                } else {
                    start = 1;
                }
                for (int j = start; j < 8; j += 2) {
                    spaces[i][j] = new Location(j + 1, i + 1, true);
                    if(i < 3) {
                        spaces[i][j].setPiece(new CheckerPiece("black"));
                    } else if(i > 4) {
                        spaces[i][j].setPiece(new CheckerPiece("red"));
                    }
                    if(start == 0) {
                        spaces[i][j + 1] = new Location(j + 2, i + 1, false);
                    } else {
                        spaces[i][j - 1] = new Location(j, i + 1, false);
                    }
                    index++;
                }
            }
        }
    }

    public Location[][] getSpaces() {
        return spaces;
    }
}
