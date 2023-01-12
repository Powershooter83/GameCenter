package ch.romere.logic;

import ch.romere.Menu;
import ch.romere.board.Board;
import ch.romere.board.Position;
import ch.romere.player.Player;
import ch.romere.player.PlayerInput;
import ch.romere.ticTacToe.GameObjectType;
import ch.romere.utils.ASCIIArtGenerator;
import ch.romere.utils.Display;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public abstract class Game implements Display, GameLogic {
    protected final PlayerInput playerInput = new PlayerInput();

    protected final String gameName;
    protected final String gameDescription;
    protected int BOARD_HEIGHT;
    protected int BOARD_WIDTH;
    protected int BOARD_CELL_WIDTH;
    protected int BOARD_CELL_HEIGHT;
    protected Board board;
    protected List<Player> players;
    protected Player currentPlayer;
    protected GameState gameState = GameState.START;

    protected boolean hasHorizontalLabeling = true;
    protected boolean hasVerticalLabeling = true;

    protected Game(final String gameName, final String gameDescription) {
        this.gameName = gameName;
        this.gameDescription = gameDescription;
    }

    public void printVictory(Player victoryPlayer) {
        System.out.println(System.lineSeparator());
        try {
            ASCIIArtGenerator.printTextArt(victoryPlayer.getName(), 18, ASCIIArtGenerator.ASCIIArtFont.ART_FONT_MONO, victoryPlayer.getPiece());
            ASCIIArtGenerator.printTextArt("hat gewonnen!", 12, ASCIIArtGenerator.ASCIIArtFont.ART_FONT_MONO, victoryPlayer.getPiece());
        } catch (Exception ignored) {

        }
        System.out.println("Herzliche Gratulation!");
        System.out.println(System.lineSeparator());
        System.out.println("========================================");
        System.out.println("= 'menu' gehe zum Menu                 =");
        System.out.println("= 'rematch' fuer eine Revanche         =");
        System.out.println("= 'exit' um das Spiel zu verlassen.    =");
        System.out.println("========================================");

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

    @Override
    public Player getOpponent(final Player player) {
        return players.stream().filter(p -> !p.equals(player)).findFirst().orElse(null);
    }


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

    @Override
    public void printSpacer() {
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
    }

    @Override
    public void restart() {
        currentPlayer = null;
        board.getPieces().clear();
        start();
    }

    @Override
    public void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }


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

    private String getLetter(int value) {
        char letter = (char) ('A' - 1 + value);
        return Character.toString(letter);
    }

    @Override
    public void printStartingPlayer() {
        System.out.println("  -> Der Spieler " + currentPlayer.getName() + " beginnt die Partie!");
        printSpacer();
    }

    @Override
    public void start() {
        printTitle();
        printDescription();

        currentPlayer = getRandomPlayer();
        currentPlayer.setPiece(GameObjectType.X.toString());
        players.get(1).setPiece(GameObjectType.O.toString());
        printStartingPlayer();

        printBoard(hasHorizontalLabeling, hasVerticalLabeling);

        gameState = GameState.RUNNING;
        eventHandler();
    }


    public Player getRandomPlayer() {
        Collections.shuffle(players);
        return players.get(0);
    }

    public void swapCurrentPlayer() {
        this.currentPlayer = this.players.stream().filter(player -> player != currentPlayer).toList().get(0);
    }

    @Override
    public void printTitle() {
        clearScreen();
        try {
            ASCIIArtGenerator.printTextArt(this.gameName, ASCIIArtGenerator.ART_SIZE_SMALL,
                    ASCIIArtGenerator.ASCIIArtFont.ART_FONT_SANS_SERIF, "$");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        printSpacer();
    }

    @Override
    public void printDescription() {
        System.out.println(gameDescription);
        printSpacer();
    }

}
