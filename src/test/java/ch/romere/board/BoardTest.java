package ch.romere.board;

import ch.romere.games.linegames.GameObject;
import ch.romere.games.linegames.GameObjectType;
import ch.romere.games.memory.Card;
import ch.romere.player.Player;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {


    @ParameterizedTest
    @CsvSource({"1,0", "0,1", "345,123", "1,634", "1,1"})
    void isPositionOccupied(final int x, final int y) {
        // arrange
        Board board = new Board();
        board.addPiece(new Card(1, new Position(x, y)));
        // act
        boolean actual = board.isPositionOccupied(new Position(x, y));
        // assert
        assertTrue(actual);
    }

    @ParameterizedTest
    @CsvSource({"0,0", "0,1", "345,123", "1,0", "1,1"})
    void getPieceAtPosition(final int x, final int y) {
        // arrange
        Board board = new Board();
        Card expected = new Card(1, new Position(x, y));
        board.addPiece(expected);
        // act
        Card actual = (Card) board.getPieceAtPosition(new Position(x, y));
        // assert
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"Powershooter93,0,14", "Lukik53,-140,435345"})
    void getPlayerOfPieceAtPosition(final String player, final int x, final int y) {
        // arrange
        Board board = new Board();
        Player expected = new Player(player);
        board.addPiece(new GameObject(expected, GameObjectType.X, new Position(x, y)));
        // act
        Player actual = board.getPlayerOfPieceAtPosition(new Position(x, y));
        // assert
        assertEquals(expected, actual);
    }
}