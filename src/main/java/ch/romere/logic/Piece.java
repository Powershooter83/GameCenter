package ch.romere.logic;

import ch.romere.board.Position;
import ch.romere.player.Player;

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
