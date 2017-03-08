package edu.iastate.mis307.objects;

public class Board {

    private Location[][] spaces;
    private Location[] playableSpace;
    private CheckerPiece[] pieces;

    public Board() {
        spaces = new Location[8][8];
        initializeSpaces();
        initializePieces();
    }

    private void initializeSpaces() {
        playableSpace = new Location[32];
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
                    playableSpace[index] = new Location(j + 1, i + 1, true);
                    spaces[i][j] = playableSpace[index];
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

    private void initializePieces() {
        pieces = new CheckerPiece[24];
        for (int i = 0; i < 24; i++) {
            if(i < 12) {
                CheckerPiece piece = new CheckerPiece(playableSpace[i], "black");
                pieces[i] = piece;
                playableSpace[i].setPiece(piece);
            } else {
                CheckerPiece piece = new CheckerPiece(playableSpace[i + 8], "red");
                pieces[i] = piece;
                playableSpace[i + 8].setPiece(piece);
            }
        }
    }

    public Location[][] getSpaces() {
        return spaces;
    }

    public Location[] getPlayableSpace() {
        return playableSpace;
    }

    public CheckerPiece[] getPieces() {
        return pieces;
    }
}
