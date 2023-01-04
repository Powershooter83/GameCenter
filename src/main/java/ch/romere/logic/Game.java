package ch.romere.logic;

import ch.romere.MainMenu;
import ch.romere.board.Board;
import ch.romere.player.Player;
import ch.romere.utils.ASCIIArtGenerator;
import ch.romere.utils.PlayerInput;
import ch.romere.utils.Display;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Game implements Display {
    protected Board board;
    protected ArrayList<Player> players;
    public abstract void start();
    public int BOARD_HEIGHT;
    public int BOARD_WIDTH;
    protected Player currentPlayer;


    protected final PlayerInput playerInput = new PlayerInput();

    public void printVictory(Player victoryPlayer) throws Exception {
        System.out.println(System.lineSeparator());
        ASCIIArtGenerator.printTextArt(victoryPlayer.getName(), 18, ASCIIArtGenerator.ASCIIArtFont.ART_FONT_MONO,victoryPlayer.getPiece());
        ASCIIArtGenerator.printTextArt("hat gewonnen!", 12, ASCIIArtGenerator.ASCIIArtFont.ART_FONT_MONO,victoryPlayer.getPiece());
        System.out.println("Herzliche Gratulation!");
        System.out.println(System.lineSeparator());
        System.out.println("========================================");
        System.out.println("= 'menu' gehe zum Menu                 =");
        System.out.println("= 'rematch' fuer eine Revanche         =");
        System.out.println("= 'exit' um das Spiel zu verlassen.    =");
        System.out.println("========================================");

        while(true){
            Scanner scanner = new Scanner(System.in);
            if(scanner.nextLine().equals("menu")){
                new MainMenu(players);
                break;
            }
            if(scanner.nextLine().equals("rematch")){
                start();
                break;
            }
            if(scanner.nextLine().equals("exit")){
                System.exit(0);
            }
        }
    }

    @Override
    public void updateBoard(){
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
        printBoard();
    }



}
