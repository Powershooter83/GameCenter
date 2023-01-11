package ch.romere.ticTacToe;

import ch.romere.board.Board;
import ch.romere.board.Position;
import ch.romere.logic.Game;
import ch.romere.logic.GameState;
import ch.romere.logic.Piece;
import ch.romere.player.Player;;
import java.util.List;

public class TicTacToe extends Game {

   public TicTacToe(final List<Player> players) {
      BOARD_WIDTH = 3;
      BOARD_HEIGHT = 3;
      BOARD_CELL_WIDTH = 3;
      BOARD_CELL_HEIGHT = 1;
      this.players = players;
      board = new Board();
      start();
   }

   public void eventHandler() {
      while (gameState == GameState.RUNNING) {
         Position position = playerInput.getInputPosition();

         if (position.xAxis() <= 0 || position.xAxis() > BOARD_WIDTH) {
            System.out.println("Die X-Achse muss zwischen 1 und " + BOARD_WIDTH + " liegen");
            continue;
         }

         if (position.yAxis() <= 0 || position.yAxis() > BOARD_HEIGHT) {
            System.out.println("Die Y-Achse muss zwischen 1 und " + BOARD_WIDTH + " liegen");
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
         swapCurrentPlayer();
      }
   }

   @Override
   public void start() {
      printTitle();
      printBoard(true, true);

      currentPlayer = getRandomPlayer();
      currentPlayer.setPiece(GameObjectType.X.toString());
      players.get(1).setPiece(GameObjectType.O.toString());
      printStartingPlayer();

      eventHandler();
   }

   private void checkForWin() {
      if (hasHorizontalOrVerticalLine()) {
         try {
            printVictory(currentPlayer);
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
      }
   }

   private boolean hasHorizontalOrVerticalLine() {
      boolean hasLine = false;
      for (int yAxis = 0; yAxis < 3; yAxis++) {
         boolean hasHorizontalLine = false;
         boolean hasVerticalLine = false;

         for (int xAxis = 0; xAxis < 3; xAxis++) {
            Piece piece = board.getPieceAtPosition(new Position(xAxis, yAxis));
            hasHorizontalLine = piece != null && piece.getPlayer() == currentPlayer;

            Piece piece2 = board.getPieceAtPosition(new Position(yAxis, xAxis));
            hasVerticalLine = piece != null && piece2.getPlayer() == currentPlayer;
         }

         if (!hasLine) {
            hasLine = hasHorizontalLine || hasVerticalLine;
         }

      }
      return hasLine;
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

}
