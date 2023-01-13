package ch.romere.games.linegames.connectFour;


import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ch.romere.player.Player;

class ConnectFourTest {



   @Test
   public void checkForWin(){
      // arrange
      List<Player> players = new ArrayList<>();
      players.add(new Player("Peter"));
      players.add(new Player("Pan"));
      ConnectFour connectFour = new ConnectFour(players);
      when(mockScanner.nextLine()).thenReturn(input);


   }

}