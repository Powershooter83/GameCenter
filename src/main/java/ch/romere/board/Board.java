package ch.romere.board;

import ch.romere.logic.Piece;
import ch.romere.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final ArrayList<Piece> pieces = new ArrayList<>();

    /**
     * Diese Methode prüft, ob eine Position mit einem "Piece" besetzt ist.
     * @param position Die Koordinaten des zu prüfenden Feldes.
     * @return boolean Falls es besetzt ist wird True (sonst False) zurückgegeben.
     */
    public boolean isPositionOccupied(final Position position) {
        return pieces.stream().anyMatch(piece -> piece.getPosition().equals(position));
    }

    /**
     * Diese Methode fügt ein Piece der "pieces" ArrayList hinzu.
     * @param Piece Das zu hinzufügende "Piece"
     */
    public void addPiece(final Piece piece) {
        pieces.add(piece);
    }

    /**
     * Diese Methode gibt ein "Piece" an einer bestimmten Koordinaten zurück
     * Sollte kein "Piece" an diesem Ort sein, wird null zurückgegeben.
     * @param position Die Koordinaten des "Piece"
     * @return Das gefundene "Piece" wird zurückgegeben.
     */
    public Piece getPieceAtPosition(final Position position) {
        return pieces.stream().filter(piece -> piece.getPosition().equals(position)).findFirst().orElse(null);
    }

    /**
     * Diese Methode gibt den Spieler von einem "Piece" an einer bestimmten Position zurück.
     * Sollte kein "Piece" an diesem Ort sein, wird null zurückgegeben.
     * @param position Die Koordinaten des "Piece"
     * @return Der gefundene Spieler wird zurückgegeben.
     */
    public Player getPlayerOfPieceAtPosition(final Position position) {
        Piece pieceOfPlayer = pieces.stream().filter(piece -> piece.getPosition().equals(position)).findFirst().orElse(null);
        return pieceOfPlayer != null ? pieceOfPlayer.getPlayer() : null;
    }

    /**
     * Diese Methode gibt alle Pieces zurück.
     * @return Eine Liste von "Piece" wird zurükgegeben.
     */
    public List<Piece> getPieces() {
        return pieces;
    }

}
