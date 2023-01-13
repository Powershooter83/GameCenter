package ch.romere;

import ch.romere.games.linegames.connectFour.ConnectFour;
import ch.romere.games.linegames.ticTacToe.TicTacToe;
import ch.romere.games.memory.Memory;
import ch.romere.player.Player;
import ch.romere.utils.ASCIIArtGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final List<Player> players;
    private final Scanner scanner;

    public Menu(final List<Player> players) {
        this.players = players;
        scanner = new Scanner(System.in);
        printTitle();
        printGames();
        gameSelection();
    }

    public Menu() {
        this.players = new ArrayList<>();
        scanner = new Scanner(System.in);
        printTitle();
        printPlayerSelection();
        printGames();
        gameSelection();
    }

    /**
     * Dieser Konstruktor wird nur für die internen Unit Tests verwendet
     */
    Menu(final Scanner scanner) {
        this.scanner = scanner;
        this.players = new ArrayList<>();
    }
    /**
     * Diese Methode "druckt" den Titel mit Hilfe des ASCIIArtGenerator in die Konsole.
     */
    public void printTitle() {
        try {
            ASCIIArtGenerator.printTextArt("Welcome to the Game Center", ASCIIArtGenerator.ART_SIZE_SMALL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(System.lineSeparator().repeat(3));
    }

    /**
     * Diese Methode ist für die Erstellung der Spieler zuständig. Sie fragt in der Konsole
     * nach den Spielernamen und erstellt sie anschliessend.
     */
    public void printPlayerSelection() {
        for (int i = 1; i <= 2; i++) {
            System.out.println("     ---> Bitte gib einen Spielernamen fuer Spieler " + i + " ein <---");
            players.add(new Player(scanner.nextLine()));
        }

        System.out.println("\nHerzlich Willkommen " + players.get(0).getName() + " und " + players.get(1).getName() + "!");
    }

    /**
     * Diese Methode gibt die Auswahl der Spielmodis in der Konsole aus.
     */
    public void printGames() {
        System.out.println("Bitte selektiere ein Spiel zum fortfahren:");
        System.out.println("1. TicTacToe");
        System.out.println("2. VierGewinnt");
        System.out.println("3. Memory");
        System.out.println("4. Exit");
    }

    /**
     * Diese Methode dient zur auswahl von Spielmodis. Dazu wird der input des Spielers abgeglichen und dann
     * der jeweilige Spielmodus gestartet oder das Programm beendet.
     */
    public void gameSelection() {
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
