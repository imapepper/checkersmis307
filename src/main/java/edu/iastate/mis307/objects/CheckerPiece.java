package edu.iastate.mis307.objects;

public class CheckerPiece {
    private Location location;
    private String color;
    private boolean isKing;

    CheckerPiece(Location location, String color) {
        this.location = location;
        this.color = color;
        isKing = false;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getColor() {
        return color;
    }

    public boolean isKing() {
        return isKing;
    }

    public void setKing(boolean king) {
        isKing = king;
    }

    @Override
    public String toString() {
        return "CheckerPiece{" +
                "location=" + location +
                ", color='" + color + '\'' +
                ", isKing=" + isKing +
                '}';
    }
}
