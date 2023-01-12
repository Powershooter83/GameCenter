package ch.romere.ticTacToe;

import ch.romere.board.Position;
import ch.romere.logic.Piece;
import ch.romere.player.Player;

public class GameObject extends Piece {

    private final GameObjectType type;

    public GameObject(final Player player,
                      final GameObjectType type,
                      final Position position) {
        this.type = type;
        this.position = position;
        this.player = player;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
