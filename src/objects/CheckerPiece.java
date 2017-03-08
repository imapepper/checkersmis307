package objects;

public class CheckerPiece {
    private String color;
    private boolean isKing;

    public CheckerPiece(String color) {
        this.color = color;
        isKing = false;
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
}
