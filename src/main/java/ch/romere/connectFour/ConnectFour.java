package ch.romere.connectFour;

import ch.romere.logic.Game;
import ch.romere.board.Board;
import ch.romere.logic.GameState;
import ch.romere.logic.Piece;
import ch.romere.player.Player;
import ch.romere.board.Position;
import ch.romere.ticTacToe.GameObject;
import ch.romere.ticTacToe.GameObjectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConnectFour extends Game {

   public ConnectFour(List<Player> players) {
      BOARD_WIDTH = 7;
      BOARD_HEIGHT = 6;
      BOARD_CELL_HEIGHT = 2;
      BOARD_CELL_WIDTH = 10;
      this.players = players;
      board = new Board();
      start();
   }

   @Override
   public void start() {
      printTitle();
      printBoard(true, false);

      currentPlayer = getRandomPlayer();
      currentPlayer.setPiece(GameObjectType.X.toString());
      players.get(1).setPiece(GameObjectType.O.toString());
      printStartingPlayer();

      eventHandler();
   }

   public void eventHandler() {
      while (gameState == GameState.RUNNING) {
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
         checkForWin();
         swapCurrentPlayer();
      }

   }

   private boolean hasHorizontalOrVerticalLine() {
      boolean hasLine = false;
      for (int yAxis = 0; yAxis < BOARD_HEIGHT; yAxis++) {

         for (int xAxis = 0; xAxis < BOARD_WIDTH; xAxis++) {
            boolean hasHorizontalLine = false;
            boolean hasVerticalLine = false;
            for (int addAxis = 0; addAxis < 4; addAxis++) {
               Piece piece = board.getPieceAtPosition(new Position(xAxis + addAxis, yAxis));
               hasHorizontalLine = piece != null && piece.getPlayer() == currentPlayer;

               Piece piece2 = board.getPieceAtPosition(new Position(yAxis + addAxis, xAxis));
               hasVerticalLine = piece2 != null && piece2.getPlayer() == currentPlayer;
            }

            if(!hasLine){
               hasLine = hasHorizontalLine || hasVerticalLine;
            }

         }

      }
      return hasLine;
   }

   private boolean checkForWin() {
           return false;
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

}
