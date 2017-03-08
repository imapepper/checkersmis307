package edu.iastate.mis307.objects;

public class Location {

    private int xCoordinate;
    private int yCoordinate;
    private boolean isPlayable;
    private CheckerPiece piece;

    Location(int xCoordinate, int yCoordinate, boolean isPlayable) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.isPlayable = isPlayable;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public boolean isPlayable() {
        return isPlayable;
    }

    public CheckerPiece getPiece() {
        return piece;
    }

    public void setPiece(CheckerPiece piece) {
        this.piece = piece;
    }

    public void removePiece() {
        piece = null;
    }
}
