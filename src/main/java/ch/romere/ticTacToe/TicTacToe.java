package ch.romere.ticTacToe;

import ch.romere.board.Board;
import ch.romere.board.Position;
import ch.romere.logic.Game;
import ch.romere.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicTacToe extends Game {

    private final String HORIZONTAL_LINE = "  ---+---+---";
    private final Character VERTICAL_LINE = '|';
    private final Character SPACE = ' ';

    public TicTacToe(ArrayList<Player> players) {
        BOARD_WIDTH = 3;
        BOARD_HEIGHT = 3;
        this.players = players;
        board = new Board();
        start();
    }


    @Override
    public void printBoard() {
        String sb = getLine(2) + System.lineSeparator() +
                HORIZONTAL_LINE + System.lineSeparator() +
                getLine(1) + System.lineSeparator() +
                HORIZONTAL_LINE + System.lineSeparator() +
                getLine(0) + System.lineSeparator() + "   1   2   3";
        System.out.println(sb);
    }


    private String getLine(int line) {
        final StringBuilder sb = new StringBuilder();
        sb.append(line + 1).append(SPACE);
        for (int i = 0; i < 3; i++) {
            sb.append(SPACE);
            if (board.getPieceAtPosition(new Position(i, line)) != null) {
                sb.append(board.getPieceAtPosition(new Position(i, line)).toString()).append(SPACE);
                if (i != 2) {
                    sb.append(VERTICAL_LINE);
                }

                continue;
            }
            sb.append(SPACE);
            if (i == 2) {
                sb.append(SPACE);
                continue;
            }
            sb.append(SPACE).append(VERTICAL_LINE);
        }
        return sb.toString();
    }


    @Override
    public void printTitle() {
        System.out.println(" ________  __                  ________                         ________                  \n" +
                "/        |/  |                /        |                       /        |                 \n" +
                "$$$$$$$$/ $$/   _______       $$$$$$$$/______    _______       $$$$$$$$/______    ______  \n" +
                "   $$ |   /  | /       |         $$ | /      \\  /       |         $$ | /      \\  /      \\ \n" +
                "   $$ |   $$ |/$$$$$$$/          $$ | $$$$$$  |/$$$$$$$/          $$ |/$$$$$$  |/$$$$$$  |\n" +
                "   $$ |   $$ |$$ |               $$ | /    $$ |$$ |               $$ |$$ |  $$ |$$    $$ |\n" +
                "   $$ |   $$ |$$ \\_____          $$ |/$$$$$$$ |$$ \\_____          $$ |$$ \\__$$ |$$$$$$$$/ \n" +
                "   $$ |   $$ |$$       |         $$ |$$    $$ |$$       |         $$ |$$    $$/ $$       |\n" +
                "   $$/    $$/  $$$$$$$/          $$/  $$$$$$$/  $$$$$$$/          $$/  $$$$$$/   $$$$$$$/ \n" +
                "                                                                                          \n" +
                "                                                                                          \n" +
                "                                                                                          ");
    }

    @Override
    public void start() {
        printTitle();
        printBoard();


        Collections.shuffle(players);
        currentPlayer = players.get(0);
        currentPlayer.setPiece(GameObjectType.X.toString());
        players.get(1).setPiece(GameObjectType.O.toString());

        System.out.println("Player " + currentPlayer.getName() + " starts the game.");

        while (true) try {
            Position position = playerInput.getInputPosition();

            if (position.xAxis() <= 0 || position.xAxis() > BOARD_WIDTH) {
                System.out.println("Die X-Achse muss zwischen 1 und 3 liegen");
                continue;
            }

            if (position.yAxis() <= 0 || position.yAxis() > BOARD_HEIGHT) {
                System.out.println("Die Y-Achse muss zwischen 1 und 3 liegen");
                continue;
            }

            Position positionAsCoordinates = new Position(position.yAxis() - 1, position.xAxis() - 1);

            if (board.isPositionOccupied(positionAsCoordinates)) {
                System.out.println("Dieses Feld ist bereits belegt");
                continue;
            }
            board.addPiece(new GameObject(currentPlayer, GameObjectType.valueOf(currentPlayer.getPiece()), positionAsCoordinates));
            updateBoard();
            checkForWin();
            currentPlayer = players.stream().filter(player -> !player.equals(currentPlayer)).findFirst().orElse(null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void checkForWin() throws Exception {
        if (checkForLine(new Position(0, 0), new Position(1, 1), new Position(2, 2)) ||
                checkForLine(new Position(0, 2), new Position(1, 1), new Position(2, 0))) {
            printVictory(currentPlayer);
        }

        for (int i = 0; i < 3; i++) {
            if (checkForLine(new Position(0, i), new Position(1, i), new Position(2, i)) ||
                    checkForLine(new Position(i, 0), new Position(i, 1), new Position(i, 2))) {
                printVictory(currentPlayer);
            }
        }


    }

    private boolean checkForLine(final Position pos1, final Position pos2, final Position pos3) {
        try {
            return board.getPiecesAtPositions(List.of(pos1, pos2, pos3)).stream().filter(piece -> piece.getPlayer().equals(currentPlayer)).count() == 3;
        } catch (NullPointerException e) {
            return false;
        }
    }

}
