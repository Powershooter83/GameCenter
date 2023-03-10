package ch.romere.logic;

import ch.romere.Menu;
import ch.romere.board.Board;
import ch.romere.board.Position;
import ch.romere.player.Player;
import ch.romere.player.PlayerInput;
import ch.romere.utils.ASCIIArtGenerator;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public abstract class Game implements Display, GameLogic {
    protected final PlayerInput playerInput = new PlayerInput();

    protected final String gameName, gameDescription;
    protected int BOARD_HEIGHT, BOARD_WIDTH, BOARD_CELL_WIDTH, BOARD_CELL_HEIGHT;
    protected Board board;
    protected List<Player> players;
    protected Player currentPlayer;

    protected boolean hasHorizontalLabeling;
    protected boolean hasVerticalLabeling;

    protected Game(final String gameName, final String gameDescription, final int BOARD_HEIGHT, final int BOARD_WIDTH, final int BOARD_CELL_WIDTH, final int BOARD_CELL_HEIGHT, final boolean hasHorizontalLabeling, final boolean hasVerticalLabeling, final List<Player> players) {
        this.gameName = gameName;
        this.gameDescription = gameDescription;
        this.BOARD_HEIGHT = BOARD_HEIGHT;
        this.BOARD_WIDTH = BOARD_WIDTH;
        this.BOARD_CELL_WIDTH = BOARD_CELL_WIDTH;
        this.BOARD_CELL_HEIGHT = BOARD_CELL_HEIGHT;
        this.hasHorizontalLabeling = hasHorizontalLabeling;
        this.hasVerticalLabeling = hasVerticalLabeling;
        this.players = players;
        board = new Board();

    }

    /**
     * Diese Methode gibt ein Unentschieden in die Konsole aus.
     * Mit Hilfe vom ASCIIArtGenerator.
     */
    public void printDraw() {
        System.out.println(System.lineSeparator());
        try {
            ASCIIArtGenerator.printTextArt("UNENTSCHIEDEN!", 18, ASCIIArtGenerator.ASCIIArtFont.ART_FONT_MONO, "$");
        } catch (Exception ignored) {

        }
        System.out.println(System.lineSeparator());
        printMenu();
        menuInput();
    }

    /**
     * Diese Methode gibt eine Art Untermenu in die Konsole aus.
     */
    protected void printMenu() {
        System.out.println("========================================");
        System.out.println("= 'menu' gehe zum Menu                 =");
        System.out.println("= 'rematch' fuer eine Revanche         =");
        System.out.println("= 'exit' um das Spiel zu verlassen.    =");
        System.out.println("========================================");
    }

    /**
     * Hier wird gepr??ft, welcher Men??punkt der Spieler ausgew??hlt hat.
     */
    protected void menuInput() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            switch (scanner.nextLine()) {
                case "menu" -> {
                    clearScreen();
                    new Menu(players);
                }
                case "rematch" -> restart();
                case "exit" -> System.exit(0);
            }
        }
    }

    /**
     * Hier wird der Gegner eines Spieles zur??ckgegeben.
     * @param Der Spieler von dem man den Gegner haben m??chte.
     * @return Der Gegner wird hier als player zur??ckgegeben.
     */
    @Override
    public Player getOpponent(final Player player) {
        return players.stream().filter(p -> !p.equals(player)).findFirst().orElse(null);
    }

    /**
     * Das Spielbrett wird durch diese Methode aktualisiert.
     * Dabei wird der Title und die Description auch ausgegeben.
     * @param Der Spieler, welcher gerade am Zug ist.
     */
    @Override
    public void updateBoard(final Player player) {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
        printTitle();
        printDescription();
        System.out.println("  -> Der Spieler " + player.getName() + " ist am Zug!");
        printSpacer();
        printBoard(hasHorizontalLabeling, hasVerticalLabeling);
    }

    /**
     * Diese Methode gibt einen Sieger in der Konsole aus.
     * Mit Hilfe vom ASCIIArtGenerator.
     */
    public void printVictory(Player victoryPlayer) {
        System.out.println(System.lineSeparator());
        try {
            ASCIIArtGenerator.printTextArt(victoryPlayer.getName(), 18, ASCIIArtGenerator.ASCIIArtFont.ART_FONT_MONO, "#");
            ASCIIArtGenerator.printTextArt("hat gewonnen!", 12, ASCIIArtGenerator.ASCIIArtFont.ART_FONT_MONO, "#");
        } catch (Exception ignored) {

        }
        System.out.println("Herzliche Gratulation!");
        System.out.println(System.lineSeparator());
        printMenu();
        menuInput();
    }

    /**
     * Diese Methode gibt ein paar leere Zeilen in der Konsole aus.
     * Dies erzeugt eine Abgrenzung.
     */
    @Override
    public void printSpacer() {
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
    }

    /**
     * Durch diese Methode kann ein Spieler neugestartet werden.
     */
    @Override
    public void restart() {
        currentPlayer = null;
        board.getPieces().clear();
        start();
    }

    /**
     * Diese Methode "l??scht" die Konsole. Dazu werden einfach leere Zeilen in die Konsole gedruckt.
     */
    @Override
    public void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }


    /**
     * Diese Methode gibt das Spielbrett in die Konsole aus.
     * Dabei kann so gut wie alles angepasst werde.
     * Anzahl Zeilen, Spalten, die Zellenh??he und Zellenbreite kann angepasst werden.
     */
    @Override
    public void printBoard(final boolean horizontalLabeling, final boolean verticalLabeling) {
        StringBuilder board = new StringBuilder();

        for (int yAxis = BOARD_HEIGHT - 1; yAxis >= 0; yAxis--) {
            StringBuilder sb = new StringBuilder();
            StringBuilder horizontalSpacer = new StringBuilder();

            for (int cellHeight = 0; cellHeight < BOARD_CELL_HEIGHT; cellHeight++) {
                if (cellHeight == BOARD_CELL_HEIGHT / 2 && verticalLabeling) {
                    sb.append(StringUtils.center(getLetter(yAxis + 1), 9)).append("| ");

                } else if (verticalLabeling) {
                    sb.append("         | ");
                }
                for (int xAxis = 0; xAxis <= BOARD_WIDTH; xAxis++) {
                    if (xAxis != 0 || !verticalLabeling) {
                        sb.append("| ");
                    }
                    if (this.board.getPieceAtPosition(new Position(xAxis, yAxis)) != null && cellHeight == BOARD_CELL_HEIGHT / 2) {
                        String text = this.board.getPieceAtPosition(new Position(xAxis, yAxis)).toString();

                        if (text == null) {
                            continue;
                        }
                        if (text.length() > BOARD_CELL_WIDTH) {
                            throw new StringIndexOutOfBoundsException("Text is too long for the cell");
                        }
                        sb.append(StringUtils.center(text, BOARD_CELL_WIDTH - 1));
                    } else {
                        sb.append(" ".repeat(Math.max(0, BOARD_CELL_WIDTH - 1)));
                    }
                }
                sb.append(System.lineSeparator());
            }


            for (int xAxis = 0; xAxis <= BOARD_WIDTH; xAxis++) {
                if (xAxis == BOARD_WIDTH) {
                    horizontalSpacer.append("+");
                    continue;
                }

                if (verticalLabeling && xAxis == 0) {
                    horizontalSpacer.append(" ".repeat(Math.max(0, 9)));
                }

                horizontalSpacer.append("+").append("-".repeat(Math.max(0, BOARD_CELL_WIDTH)));
            }


            board.append(horizontalSpacer).append(System.lineSeparator()).append(sb);

            if (yAxis == 0) {
                board.append(horizontalSpacer).append(System.lineSeparator());

                if (horizontalLabeling) {
                    StringBuilder labeling = new StringBuilder();

                    for (int i = 1; i <= BOARD_WIDTH; i++) {
                        if (i == 1) {
                            if (verticalLabeling) {
                                labeling.append(" ".repeat(9));
                            }
                            labeling.append(StringUtils.center(String.valueOf(i), BOARD_CELL_WIDTH + 2));
                        } else {
                            labeling.append(StringUtils.center(String.valueOf(i), BOARD_CELL_WIDTH + 1));
                        }
                    }

                    board.append(labeling);
                }
            }
        }
        System.out.println(board);

    }

    /**
     * Diese Methode wandelt einen int zu einem Buchstaben um. (a = 0)
     * @return Gibt den Buchstaben als String zur??ck.
     */
    private String getLetter(int value) {
        return Character.toString((char) ('A' - 1 + value));
    }

    /**
     * Diese Methode druckt den zu startenden Spieler in die Konsole.
     */
    @Override
    public void printStartingPlayer() {
        System.out.println("  -> Der Spieler " + currentPlayer.getName() + " beginnt die Partie!");
        printSpacer();
    }

    /**
     * Diese Methode holt sich einen Zuf??lligen Spieler aus der players "list"
     * @return gibt einen Zuf??lligen Spieler zur??ck.
     */
    public Player getRandomPlayer() {
        Collections.shuffle(players);
        return players.get(0);
    }

    /**
     * Mit dieser Methode wird der aktuelle Spieler gewechselt.
     * Dazu wird die players liste durch laufen und geschaut, welcher spieler nicht der aktuelle spieler ist.
     */
    public void swapCurrentPlayer() {
        this.currentPlayer = this.players.stream().filter(player -> player != currentPlayer).toList().get(0);
    }

    /**
     * Diese Methode druckt den Title des Spieles mit Hilfe des ASCIIArtGenerator in die Konsole.
     */
    @Override
    public void printTitle() {
        clearScreen();
        try {
            ASCIIArtGenerator.printTextArt(this.gameName, ASCIIArtGenerator.ART_SIZE_SMALL, ASCIIArtGenerator.ASCIIArtFont.ART_FONT_SANS_SERIF, "$");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        printSpacer();
    }

    /**
     * Diese Methode druckt die Spielbeschreibung in die Konsole.
     */
    @Override
    public void printDescription() {
        System.out.println(gameDescription);
        printSpacer();
    }

    /**
     * Diese Methode pr??ft, ob das Spielbrett voll ist.
     * Falls das Brett voll ist, wird true zur??ckgegeben, sonst false.
     * @return Gibt einen boolean zur??ck.
     */
    public boolean boardIsFull() {
        return board.getPieces().size() == BOARD_WIDTH * BOARD_HEIGHT;
    }


}
