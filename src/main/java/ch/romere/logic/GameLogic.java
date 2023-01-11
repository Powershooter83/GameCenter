package ch.romere.logic;

import ch.romere.player.Player;

public interface GameLogic {

    void start();

    void restart();

    void eventHandler();

    Player getOpponent(final Player player);
}
