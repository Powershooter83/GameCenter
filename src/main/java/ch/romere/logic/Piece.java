package ch.romere.logic;

import ch.romere.player.Player;
import ch.romere.board.Position;

public abstract class Piece {
    protected Player player;
    protected Position position;

    public Position getPosition() {
        return position;
    }
    public Player getPlayer() {
        return player;
    }
}
