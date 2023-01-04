package ch.romere.logic;

import ch.romere.board.Board;
import ch.romere.player.Player;
import ch.romere.board.Position;

public abstract class Piece {

    protected Player player;
    protected Board board;
    protected Position position;

    public Position getPosition() {
        return position;
    }
}
