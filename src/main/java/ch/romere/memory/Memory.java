package ch.romere.memory;

import ch.romere.board.Board;
import ch.romere.board.Position;
import ch.romere.logic.Game;
import ch.romere.player.Player;
import ch.romere.ticTacToe.GameObjectType;
import ch.romere.utils.PlayerInput;

import java.util.*;
import java.util.stream.Collectors;

public class Memory extends Game {

    private int openCards = 0;
    private final HashMap<Player, Integer> playerPoints = new HashMap<>();

    private final ArrayList<String> cards = new ArrayList<>(Arrays.asList("Auto", "Fisch", "Wolkenkratzer",
            "Helikopter",
            "Java",
            "BBZW",
            "Schiff",
            "Hund",
            "Banane",
            "Kiosk",
            "Einkaufszentrum",
            "Ferien"));

    public Memory(ArrayList<Player> players) {
        BOARD_WIDTH = 3;
        BOARD_HEIGHT = 2;
        BOARD_CELL_WIDTH = 20;
        BOARD_CELL_HEIGHT = 5;
        this.players = players;
        this.players.forEach(player -> playerPoints.put(player, 0));
        board = new Board();
        start();
    }

    @Override
    public void start() {
        printTitle();
        loadCards();
        printBoard();


        Collections.shuffle(players);
        currentPlayer = players.get(0);
        currentPlayer.setPiece(GameObjectType.X.toString());
        players.get(1).setPiece(GameObjectType.O.toString());

        System.out.println("Player " + currentPlayer.getName() + " starts the game.");

        while (true) {
            int input = playerInput.getInputNumber();


            this.board.getPieces().stream().filter(card-> ((Card)card).getNumber() == input).forEach(card -> {
                ((Card) card).showText(true);
                openCards++;
            });
            updateBoard();

            if(openCards == 2){
                List<Card> openCards = this.board.getPieces().stream().filter(card -> ((Card) card).isTextShown()).map(Card.class::cast).filter(Card::isActive).toList();
                this.openCards = 0;
                if(openCards.get(0).getName().equals(openCards.get(1).getName())){
                    openCards.forEach(Card::setInactive);
                    System.out.println("Ein Paar wurde gefunden!");
                    System.out.println("Player " + currentPlayer.getName() + " hat einen Punkt!");
                    this.playerPoints.put(currentPlayer, this.playerPoints.get(currentPlayer) + 1);

                    if(this.board.getPieces().stream().noneMatch(card -> ((Card) card).isActive())){
                        try {
                            printVictory(Collections.max(this.playerPoints.entrySet(), Map.Entry.comparingByValue()).getKey());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Alle Paare wurden gefunden!");
                        break;
                    }

                }else{
                    openCards.forEach(card -> card.showText(false));
                    System.out.println("Kein Paar gefunden!");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    updateBoard();
                }

            }


        }
    }

    private void loadCards() {
        Collections.shuffle(cards);

        int counting = 1;


        for (int yAxis = 0; yAxis < BOARD_HEIGHT; yAxis++) {
            for (int xAxis = 0; xAxis < BOARD_WIDTH; xAxis++) {
                board.addPiece(new Card(counting, new Position(xAxis, yAxis)));
                counting++;
            }
        }


        for (int i = 0; i < BOARD_WIDTH * BOARD_HEIGHT / 2; i++) {
            String type = this.pickRandom();
            for (int j = 0; j < 2; j++) {

                Collections.shuffle(this.board.getPieces());

                this.board.getPieces().stream()
                        .filter(Card.class::isInstance)
                        .filter(piece -> ((Card) piece).getName() == null)
                        .findFirst()
                        .ifPresent(piece -> ((Card) piece).setName(type));
            }
        }
    }


    @Override
    public void printTitle() {

    }


    private String pickRandom() {
        Collections.shuffle(cards);
        String type = cards.get(0);
        cards.remove(0);
        return type;
    }
}