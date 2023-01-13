package ch.romere.games.linegames;

import ch.romere.board.Position;
import ch.romere.logic.Game;
import ch.romere.player.Player;
import ch.romere.utils.ASCIIArtGenerator;

import java.util.List;

public abstract class LineGame extends Game {

    private final int lineLength;

    protected LineGame(final int lineLength, final String gameName, final String gameDescription, final int BOARD_HEIGHT, final int BOARD_WIDTH, final int BOARD_CELL_WIDTH, final int BOARD_CELL_HEIGHT, final boolean hasHorizontalLabeling, final boolean hasVerticalLabeling, final List<Player> players) {
        super(gameName, gameDescription, BOARD_HEIGHT, BOARD_WIDTH, BOARD_CELL_WIDTH, BOARD_CELL_HEIGHT, hasHorizontalLabeling, hasVerticalLabeling, players);
        this.lineLength = lineLength;
    }

    /**
     * Diese Methode prüft, ob eine Linie (Horizontal, Vertikal oder Diagonal) mit der länge von lineLength
     * existiert. Sollte es eine Linie von Pieces des selben Spielers geben, wird true zurückgegeben. Sonst natürlich false.
     * @return Gibt ein boolean zurück.
     */
    private boolean hasLine() {
        boolean hasLine = false;
        for (int yAxis = 0; yAxis < BOARD_HEIGHT; yAxis++) {
            for (int xAxis = 0; xAxis < BOARD_WIDTH; xAxis++) {
                boolean hasHorizontalLine = true, hasVerticalLine = true, hasDiagonalLineToRight = true, hasDiagonalLineToLeft = true;
                for (int addAxis = 0; addAxis < lineLength; addAxis++) {
                    hasDiagonalLineToLeft = currentPlayer.equals(board.getPlayerOfPieceAtPosition(new Position(xAxis - addAxis, yAxis + addAxis))) && hasDiagonalLineToLeft;
                    hasDiagonalLineToRight = currentPlayer.equals(board.getPlayerOfPieceAtPosition(new Position(xAxis + addAxis, yAxis + addAxis))) && hasDiagonalLineToRight;
                    hasHorizontalLine = currentPlayer.equals(board.getPlayerOfPieceAtPosition(new Position(xAxis + addAxis, yAxis))) && hasHorizontalLine;
                    hasVerticalLine = currentPlayer.equals(board.getPlayerOfPieceAtPosition(new Position(yAxis, xAxis + addAxis))) && hasVerticalLine;
                }
                hasLine = hasLine ^ hasHorizontalLine || hasVerticalLine || hasDiagonalLineToRight || hasDiagonalLineToLeft;
            }
        }
        return hasLine;
    }

    /**
     * Diese Methode "druckt" eine Sieger meldung aus. Dabei wird der Spielernamen in einem speziellen ASCII muster gedrckt.
     * Dann wird noch die PrintMenu sowie die menuInput Methode geöffnet.
     * @param Als Input wird der Sieger als Player Object benötigt.
     */
    public void printVictory(Player victoryPlayer) {
        System.out.println(System.lineSeparator());
        try {
            ASCIIArtGenerator.printTextArt(victoryPlayer.getName(), 18, ASCIIArtGenerator.ASCIIArtFont.ART_FONT_MONO, victoryPlayer.getPiece());
            ASCIIArtGenerator.printTextArt("hat gewonnen!", 12, ASCIIArtGenerator.ASCIIArtFont.ART_FONT_MONO, victoryPlayer.getPiece());
        } catch (Exception ignored) {

        }
        System.out.println("Herzliche Gratulation!");
        System.out.println(System.lineSeparator());
        printMenu();
        menuInput();
    }

    /**
     * Diese Methode prüft auf einen Sieg oder ein Unentschied.
     * Bei einem Sieger oder einem Unentschieden wird true zurückgegeben, ansonsten False.
     * @return Gibt ein boolean zurück
     */
    public boolean checkForWin() {
        if (!hasLine() && !boardIsFull()) {
            return false;
        }
        updateBoard(currentPlayer);
        if (boardIsFull()) {
            printDraw();
            return true;
        }
        printVictory(currentPlayer);
        return true;
    }

    /**
     * Diese Methode fügt eine neues "Piece" dem Spielbrett hinzu.
     * @param Die Koordinaten des zu hinzufügenden Pieces.
     */
    protected void addPiece(final Position position) {
        board.addPiece(new GameObject(currentPlayer, GameObjectType.valueOf(currentPlayer.getPiece()), position));
    }

    /**
     * Das ist die Hauptmethode, welches jedes mal beim start aufgerufen wird.
     * Sie gibt verschiedene Informationen in der Konsole aus und legt fest, welcher
     * Spieler beginnen sollte.
     */
    @Override
    public void start() {
        printTitle();
        printDescription();

        currentPlayer = getRandomPlayer();
        currentPlayer.setPiece(GameObjectType.X.toString());
        players.get(1).setPiece(GameObjectType.O.toString());
        printStartingPlayer();

        printBoard(hasHorizontalLabeling, hasVerticalLabeling);

        eventHandler();
    }

}
