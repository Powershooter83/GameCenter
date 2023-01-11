package ch.romere.utils;

import ch.romere.player.Player;

public interface Display {

    void printBoard(final boolean horizontalLabeling, final boolean verticalLabeling);
    void printTitle();
    void updateBoard();
    void printStartingPlayer();

}
