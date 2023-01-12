package ch.romere.logic;

import ch.romere.player.Player;

public interface Display {

    void printBoard(final boolean horizontalLabeling, final boolean verticalLabeling);

    void printTitle();

    void updateBoard(final Player player);

    void printStartingPlayer();

    void printDescription();

    void printSpacer();

    void clearScreen();

}
