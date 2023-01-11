package ch.romere;

import ch.romere.connectFour.ConnectFour;
import ch.romere.memory.Memory;
import ch.romere.player.Player;
import ch.romere.ticTacToe.TicTacToe;
import ch.romere.utils.ASCIIArtGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private final List<Player> players;
    private final Scanner scanner = new Scanner(System.in);

    public MainMenu(final List<Player> players) {
        this.players = players;
        printMenu();
        gameSelection();
    }

    public MainMenu() {
        this.players = new ArrayList<>();
        printMenu();
        printPlayerSelection();
        gameSelection();
    }

    public void printMenu() {
        try {
            ASCIIArtGenerator.printTextArt("Welcome to the Game Center", ASCIIArtGenerator.ART_SIZE_SMALL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(System.lineSeparator().repeat(3));
    }

    public void printPlayerSelection() {
        for (int i = 1; i <= 2; i++) {
            System.out.println("     ---> Bitte gib einen Spielernamen fuer Spieler " + i + " ein <---");
            players.add(new Player(scanner.nextLine()));
        }

        System.out.println("\nHerzlich Willkommen " + players.get(0).getName() + " und " + players.get(1).getName() + "!");
    }

    public void gameSelection() {
        System.out.println("Bitte selektiere ein Spiel zum fortfahren:");
        System.out.println("1. TicTacToe");
        System.out.println("2. VierGewinnt");
        System.out.println("3. Memory");
        System.out.println("4. Exit");

        while (true) {
            switch (scanner.nextLine()) {
                case "1":
                    new TicTacToe(players);
                case "2":
                    new ConnectFour(players);
                case "3":
                    new Memory(players);
                case "4":
                    System.exit(0);
            }
        }
    }

}
