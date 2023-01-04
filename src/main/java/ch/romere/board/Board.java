package ch.romere.board;

import ch.romere.logic.Piece;

import java.util.ArrayList;
import java.util.List;

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

    public List<Piece> getPiecesAtPositions(final List<Position> positions) {
        final ArrayList<Piece> pieces = new ArrayList<>();
        positions.forEach(position -> pieces.add(getPieceAtPosition(position)));
        return pieces;

    }


}