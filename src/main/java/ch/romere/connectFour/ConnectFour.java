package ch.romere.connectFour;

import ch.romere.logic.Game;
import ch.romere.board.Board;
import ch.romere.player.Player;
import ch.romere.board.Position;
import ch.romere.ticTacToe.GameObject;
import ch.romere.ticTacToe.GameObjectType;

import java.util.ArrayList;
import java.util.Collections;

public class ConnectFour extends Game {
    private final String HORIZONTAL_LINE = "  ---+---+---+---+---+---+---";
    private final Character VERTICAL_LINE = '|';
    private final Character SPACE = ' ';


    public ConnectFour(ArrayList<Player> players) {
        BOARD_WIDTH = 7;
        BOARD_HEIGHT = 6;
        this.players = players;
        board = new Board();
        start();
    }

    @Override
    public void printTitle() {
        System.out.println("____   ____.__                 ________              .__               __   \n" +
                "\\   \\ /   /|__| ___________   /  _____/  ______  _  _|__| ____   _____/  |_ \n" +
                " \\   Y   / |  |/ __ \\_  __ \\ /   \\  ____/ __ \\ \\/ \\/ /  |/    \\ /    \\   __\\\n" +
                "  \\     /  |  \\  ___/|  | \\/ \\    \\_\\  \\  ___/\\     /|  |   |  \\   |  \\  |  \n" +
                "   \\___/   |__|\\___  >__|     \\______  /\\___  >\\/\\_/ |__|___|  /___|  /__|  \n" +
                "                   \\/                \\/     \\/               \\/     \\/      ");
    }

    @Override
    public void printBoard(boolean horizontal, boolean vertical) {
        String sb = getLine(5) + System.lineSeparator() +
                HORIZONTAL_LINE + System.lineSeparator() +
                getLine(4) + System.lineSeparator() +
                HORIZONTAL_LINE + System.lineSeparator() +
                getLine(3) + System.lineSeparator() +
                HORIZONTAL_LINE + System.lineSeparator() +
                getLine(2) + System.lineSeparator() +
                HORIZONTAL_LINE + System.lineSeparator() +
                getLine(1) + System.lineSeparator() +
                HORIZONTAL_LINE + System.lineSeparator() +
                getLine(0) + System.lineSeparator() + "   1   2   3   4   5   6   7";
        System.out.println(sb);
    }


    private String getLine(int line) {
        final StringBuilder sb = new StringBuilder();
        sb.append(SPACE).append(SPACE);
        for (int i = 0; i < 7; i++) {
            sb.append(SPACE);
            if (board.getPieceAtPosition(new Position(i, line)) != null) {
                sb.append(board.getPieceAtPosition(new Position(i, line)).toString()).append(SPACE);
                if (i != 6) {
                    sb.append(VERTICAL_LINE);
                }

                continue;
            }
            sb.append(SPACE);
            if (i == 6) {
                sb.append(SPACE);
                continue;
            }
            sb.append(SPACE).append(VERTICAL_LINE);
        }
        return sb.toString();
    }


    @Override
    public void start() {
        printTitle();
        printBoard(false, false);


        Collections.shuffle(players);
        currentPlayer = players.get(0);
        currentPlayer.setPiece(GameObjectType.X.toString());
        players.get(1).setPiece(GameObjectType.O.toString());

        System.out.println("Player " + currentPlayer.getName() + " starts the game.");

        while (true) {
            try {
                Position position = playerInput.getInputPosition();

                if (position.xAxis() <= 0 || position.xAxis() > BOARD_WIDTH) {
                    System.out.println("Die X-Achse muss zwischen 1 und " + BOARD_WIDTH + " liegen");
                    continue;
                }

                Position positionAsCoordinates = new Position(position.xAxis() - 1, position.yAxis());

                boolean validPosition = true;

                for (int i = 0; i < BOARD_HEIGHT; i++) {
                    if (board.isPositionOccupied(new Position(positionAsCoordinates.xAxis(), i))) {
                        if (i == BOARD_HEIGHT - 1) {
                            System.out.println("Die Spalte ist voll");
                            validPosition = false;
                            continue;
                        }
                        continue;
                    }
                    positionAsCoordinates = new Position(positionAsCoordinates.xAxis(), i);
                    break;
                }
                if (!validPosition) {
                    continue;
                }


                board.addPiece(new GameObject(currentPlayer, GameObjectType.valueOf(currentPlayer.getPiece()), positionAsCoordinates));
                currentPlayer = players.stream().filter(player -> !player.equals(currentPlayer)).findFirst().orElse(null);
                updateBoard();
                System.out.println(checkForWin());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private boolean checkForWin() {
        for (int i = 0; i < BOARD_HEIGHT; i++) {

            if (i < BOARD_HEIGHT - 3) {
                for (int horizontal = 0; horizontal < BOARD_WIDTH; horizontal++) {
                    try {
                        GameObject pieceAtPosition = (GameObject) board.getPieceAtPosition(new Position(horizontal, i));
                        GameObject pieceAtPosition2 = (GameObject) board.getPieceAtPosition(new Position(horizontal , i + 1));
                        GameObject pieceAtPosition3 = (GameObject) board.getPieceAtPosition(new Position(horizontal, i + 2));
                        GameObject pieceAtPosition4 = (GameObject) board.getPieceAtPosition(new Position(horizontal, i + 3));

                        if (pieceAtPosition.getType() == pieceAtPosition2.getType() &&
                                pieceAtPosition.getType() == pieceAtPosition3.getType() && pieceAtPosition4.getType() == pieceAtPosition.getType()) {
                            return true;
                        }

                    } catch (NullPointerException ignored) {
                    }
                }

            }

            for (int horizontal = 0; horizontal < BOARD_WIDTH - 3; horizontal++) {
                if(i < 3){
                    try {
                        GameObject pieceAtPosition = (GameObject) board.getPieceAtPosition(new Position(horizontal, i));
                        GameObject pieceAtPosition2 = (GameObject) board.getPieceAtPosition(new Position(horizontal + 1, i + 1));
                        GameObject pieceAtPosition3 = (GameObject) board.getPieceAtPosition(new Position(horizontal + 2, i + 2));
                        GameObject pieceAtPosition4 = (GameObject) board.getPieceAtPosition(new Position(horizontal + 3, i + 3));

                        if (pieceAtPosition.getType() == pieceAtPosition2.getType() &&
                                pieceAtPosition.getType() == pieceAtPosition3.getType() && pieceAtPosition4.getType() == pieceAtPosition.getType()) {
                            return true;
                        }

                    } catch (NullPointerException ignored) {
                    }

                    try {
                        GameObject pieceAtPosition = (GameObject) board.getPieceAtPosition(new Position(6 -  horizontal, i));
                        GameObject pieceAtPosition2 = (GameObject) board.getPieceAtPosition(new Position(6 - horizontal - 1, i + 1));
                        GameObject pieceAtPosition3 = (GameObject) board.getPieceAtPosition(new Position(6 - horizontal - 2, i + 2));
                        GameObject pieceAtPosition4 = (GameObject) board.getPieceAtPosition(new Position(6 - horizontal - 3, i + 3));

                        if (pieceAtPosition.getType() == pieceAtPosition2.getType() &&
                                pieceAtPosition.getType() == pieceAtPosition3.getType() && pieceAtPosition4.getType() == pieceAtPosition.getType()) {
                            return true;
                        }

                    } catch (NullPointerException ignored) {
                    }



                }

                try {
                    GameObject pieceAtPosition = (GameObject) board.getPieceAtPosition(new Position(horizontal, i));
                    GameObject pieceAtPosition2 = (GameObject) board.getPieceAtPosition(new Position(horizontal + 1, i));
                    GameObject pieceAtPosition3 = (GameObject) board.getPieceAtPosition(new Position(horizontal + 2, i));
                    GameObject pieceAtPosition4 = (GameObject) board.getPieceAtPosition(new Position(horizontal + 3, i));

                    if (pieceAtPosition.getType() == pieceAtPosition2.getType() &&
                            pieceAtPosition.getType() == pieceAtPosition3.getType() && pieceAtPosition4.getType() == pieceAtPosition.getType()) {
                        return true;
                    }

                } catch (NullPointerException ignored) {
                }

            }
        }
        return false;
    }


}
