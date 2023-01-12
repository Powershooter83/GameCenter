package ch.romere.games.linegames.connectFour;

import ch.romere.board.Position;
import ch.romere.games.linegames.LineGame;
import ch.romere.logic.GameState;
import ch.romere.player.Player;

import java.util.List;

public class ConnectFour extends LineGame {

    public ConnectFour(List<Player> players) {
        super(4, "Vier Gewinnt", """
                Connect Four ist ein Brettspiel fuer zwei Spieler.\s
                Das Spielbrett besteht aus 7 Spalten und 6 Reihen.\s
                Die Spieler setzen abwechselnd ihre Spielsteine in die Spalten.\s
                Gewonnen hat der Spieler, der zuerst vier Spielsteine in einer Reihe hat.\s
                Dabei koennen die Spielsteine horizontal, vertikal oder diagonal liegen.\s
                Koordinaten in dem Format: [1, 2, 3, ...] in die Console schreiben. (Spaltennummer)""", 7, 6, 10, 2, true, false, players);
        start();
    }

    public void eventHandler() {
        while (gameState == GameState.RUNNING) {
            Position position = new Position(playerInput.getInputNumber(), 0);

            if (position.xAxis() <= 0 || position.xAxis() > BOARD_WIDTH) {
                System.out.println("  -> Dies ist keine gueltige Spaltennummer! Selektiere bitte eine andere. Format: [1, 2, 3...]");
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

            addPiece(positionAsCoordinates);
        }

    }

}
