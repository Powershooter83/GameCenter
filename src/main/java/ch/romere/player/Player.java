package ch.romere.player;

public class Player {

    private final String name;
    private String piece;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }
}
