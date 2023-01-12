package ch.romere.board;

import ch.romere.logic.Piece;

import java.util.ArrayList;

public class Board {

    private final ArrayList<Piece> pieces = new ArrayList<>();

    public boolean isPositionOccupied(final Position position) {
        return pieces.stream().anyMatch(piece -> piece.getPosition().equals(position));
    }

    public void addPiece(final Piece piece) {
        pieces.add(piece);
    }

    public Piece getPieceAtPosition(final Position position) {
        return pieces.stream().filter(piece -> piece.getPosition().equals(position)).findFirst().orElse(null);
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

}
