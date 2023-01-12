package ch.romere.games.linegames.ticTacToe;

import ch.romere.board.Position;
import ch.romere.games.linegames.LineGame;
import ch.romere.player.Player;

import java.util.List;

public class TicTacToe extends LineGame {

    public TicTacToe(final List<Player> players) {
        super(3, "TicTacToe", """
                Tic Tac Toe ist ein Spiel fuer zwei Spieler.\s
                Jeder Spieler hat ein Symbol, das er auf dem Spielfeld platzieren kann.\s
                Ziel ist es, drei seiner Symbole in einer Reihe zu platzieren.\s
                Die Reihen koennen horizontal, vertikal oder diagonal sein.\s
                Gewonnen hat der Spieler, der zuerst drei seiner Symbole in einer Reihe platziert hat.\s
                Koordinaten in dem Format: [A1, B2, C3, ...] in die Console schreiben.""", 3, 3, 3, 1, true, true, players);
        start();
    }

    public void eventHandler() {
        while (true) {
            final Position position = playerInput.getInputPosition();

            if (position == null || position.xAxis() < 0 || position.xAxis() >= BOARD_WIDTH || position.yAxis() < 0 || position.yAxis() >= BOARD_HEIGHT) {
                System.out.println("  -> Beachte bitte das Format: [A1, B2, C3...]");
                continue;
            }

            if (board.isPositionOccupied(position)) {
                System.out.println("  -> Dieses Feld ist bereits belegt! Selektiere bitte ein anderes.");
                continue;
            }
            addPiece(position);
            if(checkForWin()){
                break;
            }
            updateBoard(getOpponent(currentPlayer));
            swapCurrentPlayer();
        }
    }

}
