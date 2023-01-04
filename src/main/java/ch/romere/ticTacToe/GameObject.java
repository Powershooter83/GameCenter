package ch.romere.ticTacToe;

import ch.romere.logic.Piece;
import ch.romere.player.Player;
import ch.romere.board.Position;
import lombok.NonNull;

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

    @NonNull
    public GameObjectType getType() {
        return type;
    }
}
