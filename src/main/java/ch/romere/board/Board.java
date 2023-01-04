package ch.romere.board;

import ch.romere.logic.Piece;

import java.util.ArrayList;

public class Board {

    private final ArrayList<Piece> pieces = new ArrayList<>();

    public boolean isPositionEmpty(final Position position) {
        return pieces.stream().noneMatch(piece -> piece.getPosition().equals(position));
    }

    public void addPiece(Piece piece){
        pieces.add(piece);
    }

    public Piece getPieceAtPosition(final Position position){
        return pieces.stream().filter(piece -> piece.getPosition().equals(position)).findFirst().orElse(null);
    }

}
