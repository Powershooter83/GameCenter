package ch.romere.memory;

import ch.romere.board.Board;
import ch.romere.board.Position;
import ch.romere.logic.Game;
import ch.romere.player.Player;
import ch.romere.ticTacToe.GameObjectType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Memory extends Game {

    private int openCards = 0;
    private final HashMap<Player, Integer> playerPoints = new HashMap<>();

    private List<String> cards = new ArrayList<>();

    public Memory(List<Player> players) {
        BOARD_WIDTH = 6;
        BOARD_HEIGHT = 3;
        BOARD_CELL_WIDTH = 21;
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
        printBoard(true, true);


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

    @Override
    public void eventHandler() {

    }

    private void loadCards() {
        try {
            loadCardsFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private void loadCardsFromFile() throws IOException {
        this.cards = Files.readAllLines(Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("memoryWords.txt")).getPath()));
    }


    private String pickRandom() {
        Collections.shuffle(cards);
        String type = cards.get(0);
        cards.remove(0);
        return type;
    }
}
