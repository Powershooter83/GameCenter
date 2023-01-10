package ch.romere;

import ch.romere.connectFour.ConnectFour;
import ch.romere.memory.Memory;
import ch.romere.player.Player;
import ch.romere.ticTacToe.TicTacToe;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {

    private final ArrayList<Player> players;
    private final Scanner scanner = new Scanner(System.in);

    public MainMenu(final ArrayList<Player> players){
        this.players = players;
        printMenu();
        gameSelection();
    }

    public MainMenu(){
        this.players = new ArrayList<>();
        printMenu();
        printPlayerSelection();
        gameSelection();
    }

    public void printMenu(){

        System.out.println("  ________                         __      __            .__       .___\n" +
                " /  _____/_____    _____   ____   /  \\    /  \\___________|  |    __| _/\n" +
                "/   \\  ___\\__  \\  /     \\_/ __ \\  \\   \\/\\/   /  _ \\_  __ \\  |   / __ | \n" +
                "\\    \\_\\  \\/ __ \\|  Y Y  \\  ___/   \\        (  <_> )  | \\/  |__/ /_/ | \n" +
                " \\______  (____  /__|_|  /\\___  >   \\__/\\  / \\____/|__|  |____/\\____ | \n" +
                "        \\/     \\/      \\/     \\/         \\/                         \\/ ");
    }

    public void printPlayerSelection(){
        for(int i = 1; i <= 2; i++){
            System.out.println("Bitte gib einen Spielernamen fuer Spieler " + i + " ein:");
            players.add(new Player(scanner.nextLine()));
        }

        System.out.println("\nHerzlich Willkommen " + players.get(0).getName() + " und " + players.get(1).getName() + "!");
    }

    public void gameSelection() {
        System.out.println("Bitte selektiere ein Spiel zum fortfahren:");
        System.out.println("1. TicTacToe");
        System.out.println("2. VierGewinnt");
        System.out.println("3. Memory");
        System.out.println("4. Gameshow");
        System.out.println("5. Exit");

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("1")) {
                new TicTacToe(players);
                break;
            }
            if (input.equals("2")) {
                new ConnectFour(players);
                break;
            }
            if (input.equals("3")) {
                new Memory(players);
                break;
            }
        }
    }




}
