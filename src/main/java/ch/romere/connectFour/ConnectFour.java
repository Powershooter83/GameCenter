package ch.romere.connectFour;

import ch.romere.board.Board;
import ch.romere.board.Position;
import ch.romere.exceptions.InputIsNotValidPositionException;
import ch.romere.logic.Game;
import ch.romere.logic.GameState;
import ch.romere.logic.Piece;
import ch.romere.player.Player;
import ch.romere.ticTacToe.GameObject;
import ch.romere.ticTacToe.GameObjectType;
import ch.romere.utils.ASCIIArtGenerator;

import java.util.List;

public class ConnectFour extends Game {

    public ConnectFour(List<Player> players) {
        BOARD_WIDTH = 7;
        BOARD_HEIGHT = 6;
        BOARD_CELL_HEIGHT = 2;
        BOARD_CELL_WIDTH = 10;
        hasHorizontalLabeling = true;
        hasVerticalLabeling = false;
        this.players = players;
        board = new Board();
        start();
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

    public void eventHandler() {
        while (gameState == GameState.RUNNING) {
            Position position;
            try {
                position = new Position(playerInput.getInputNumber(), 0);
            } catch (InputIsNotValidPositionException e) {
                System.out.println("  -> Beachte bitte das Format: [1, 2, 3...]");
                continue;
            }

            if (position.xAxis() <= 0 || position.xAxis() > BOARD_WIDTH) {
                System.out.println("  -> Beachte bitte das Format: [1, 2, 3...]");
                continue;
            }

            Position positionAsCoordinates = new Position(position.xAxis() - 1, 0);

            boolean validPosition = true;

            for (int yAxis = 0; yAxis < BOARD_HEIGHT; yAxis++) {
                if (board.isPositionOccupied(new Position(positionAsCoordinates.xAxis(), yAxis))) {
                    if (yAxis == BOARD_HEIGHT - 1) {
                        System.out.println("Die Spalte ist voll");
                        validPosition = false;
                        continue;
                    }
                    continue;
                }
                positionAsCoordinates = new Position(positionAsCoordinates.xAxis(), yAxis);
                break;
            }

            if (!validPosition) {
                continue;
            }

            board.addPiece(new GameObject(currentPlayer, GameObjectType.valueOf(currentPlayer.getPiece()), positionAsCoordinates));
            checkForWin();
            updateBoard(getOpponent(currentPlayer));
            swapCurrentPlayer();
        }

    }

    private boolean hasHorizontalOrVerticalLine() {
        boolean hasLine = false;
        for (int yAxis = 0; yAxis < BOARD_HEIGHT; yAxis++) {
            for (int xAxis = 0; xAxis < BOARD_WIDTH; xAxis++) {
                boolean hasHorizontalLine = true;
                boolean hasVerticalLine = true;
                for (int addAxis = 0; addAxis < 4; addAxis++) {
                    Piece piece = board.getPieceAtPosition(new Position(xAxis + addAxis, yAxis));
                    Piece piece2 = board.getPieceAtPosition(new Position(yAxis, xAxis + addAxis));
                    hasHorizontalLine = piece != null && piece.getPlayer() == currentPlayer && hasHorizontalLine;
                    hasVerticalLine = piece2 != null && piece2.getPlayer() == currentPlayer && hasVerticalLine;
                }

                if (!hasLine) {
                    hasLine = hasHorizontalLine || hasVerticalLine;
                }
            }
        }
        return hasLine;
    }

    private boolean hasDiagonalLine() {
        boolean hasLine = false;

        for (int yAxis = 0; yAxis < BOARD_WIDTH; yAxis++) {
            for (int xAxis = 0; xAxis < BOARD_WIDTH; xAxis++) {
                boolean hasDiagonalLineToRight = true;
                boolean hasDiagonalLineToLeft = true;

                for (int addAxis = 0; addAxis < 4; addAxis++) {
                    Piece piece = board.getPieceAtPosition(new Position(xAxis + addAxis, yAxis + addAxis));
                    hasDiagonalLineToRight = piece != null && piece.getPlayer() == currentPlayer && hasDiagonalLineToRight;

                    Piece piece2 = board.getPieceAtPosition(new Position(xAxis - addAxis, yAxis + addAxis));
                    hasDiagonalLineToLeft = piece2 != null && piece2.getPlayer() == currentPlayer && hasDiagonalLineToLeft;
                }
                if (!hasLine) {
                    hasLine = hasDiagonalLineToRight || hasDiagonalLineToLeft;
                }

            }
        }
        return hasLine;
    }

    private void checkForWin() {
        if (hasHorizontalOrVerticalLine() || hasDiagonalLine()) {
            updateBoard(currentPlayer);

            try {
                printVictory(currentPlayer);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void printTitle() {
        clearScreen();
        try {
            ASCIIArtGenerator.printTextArt("Vier Gewinnt", ASCIIArtGenerator.ART_SIZE_SMALL,
                    ASCIIArtGenerator.ASCIIArtFont.ART_FONT_SANS_SERIF, "$");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        printSpacer();
    }

    @Override
    public void printDescription() {
        System.out.println("""
                Connect Four ist ein Brettspiel fuer zwei Spieler.\s
                Das Spielbrett besteht aus 7 Spalten und 6 Reihen.\s
                Die Spieler setzen abwechselnd ihre Spielsteine in die Spalten.\s
                Gewonnen hat der Spieler, der zuerst vier Spielsteine in einer Reihe hat.\s
                Dabei koennen die Spielsteine horizontal, vertikal oder diagonal liegen.\s
                Koordinaten in dem Format: [1, 2, 3, ...] in die Console schreiben. (Spaltennummer)""");
        printSpacer();
    }


}
