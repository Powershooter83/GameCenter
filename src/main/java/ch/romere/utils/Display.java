package ch.romere.utils;

public interface Display {

    void printBoard(final boolean horizontalLabeling, final boolean verticalLabeling);
    void printTitle();
    void updateBoard();
    void printStartingPlayer();
    void printDescription();

    void printSpacer();
    void clearScreen();

}
