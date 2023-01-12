package ch.romere.memory;

import ch.romere.board.Board;
import ch.romere.board.Position;
import ch.romere.exceptions.InputIsNotAValidPositionException;
import ch.romere.logic.Game;
import ch.romere.logic.GameState;
import ch.romere.player.Player;
import ch.romere.ticTacToe.GameObjectType;
import ch.romere.utils.ASCIIArtGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Memory extends Game {

    private final HashMap<Player, Integer> playerPoints = new HashMap<>();
    private int openCards = 0;
    private List<String> cards = new ArrayList<>();

    public Memory(List<Player> players) {
        super("Memory", """
                Memory ist ein Gedaechtnisspiel, bei dem die Spieler versuchen, so viele Paare wie moeglich zu finden.
                Die Spieler muessen die Kartenpaare aufdecken und versuchen, sie zu finden.
                Wenn ein Spieler ein Paar gefunden hat, erhaelt er einen Punkt.
                Das Spiel endet, wenn alle Kartenpaare gefunden wurden.""");
        BOARD_WIDTH = 2;
        BOARD_HEIGHT = 2;
        BOARD_CELL_WIDTH = 21;
        BOARD_CELL_HEIGHT = 5;
        hasHorizontalLabeling = false;
        hasVerticalLabeling = false;
        this.players = players;
        this.players.forEach(player -> playerPoints.put(player, 0));
        board = new Board();
        start();
    }

    @Override
    public void start() {
        printTitle();
        printDescription();
        loadCards();

        currentPlayer = getRandomPlayer();
        currentPlayer.setPiece(GameObjectType.X.toString());
        players.get(1).setPiece(GameObjectType.O.toString());
        printStartingPlayer();

        printBoard(hasHorizontalLabeling, hasVerticalLabeling);

        gameState = GameState.RUNNING;
        eventHandler();

    }

    @Override
    public void eventHandler() {
        while (gameState == GameState.RUNNING) {
            int input;
            try {
                input = playerInput.getInputNumber();
            } catch (InputIsNotAValidPositionException e) {
                System.out.println("  -> Beachte bitte das Format: [1, 2, 3...]");
                continue;
            }

            if (input > BOARD_WIDTH * BOARD_HEIGHT || input <= 0) {
                System.out.println("  -> Beachte bitte das Format: [1, 2, 3...]");
                continue;
            }


            this.board.getPieces().stream().filter(card -> ((Card) card).getNumber() == input).forEach(card -> {
                ((Card) card).showText(true);
                openCards++;
            });
            updateBoard(currentPlayer);

            if (openCards != 2) {
                continue;
            }
            List<Card> openCards = this.board.getPieces().stream().filter(card -> ((Card) card).isTextShown()).map(Card.class::cast).filter(Card::isActive).toList();
            this.openCards = 0;
            if (openCards.size() < 2) {
                this.openCards = 1;
                System.out.println("  -> Diese Karte wurde bereits geöffnet!");
                continue;
            }

            if (openCards.get(0).getName().equals(openCards.get(1).getName())) {
                openCards.forEach(Card::setInactive);
                System.out.println("Ein Paar wurde gefunden!");
                System.out.println("Spieler " + currentPlayer.getName() + " hat einen Punkt!");
                System.out.println("Der Spieler " + currentPlayer.getName() + " ist nochmals an der Reihe!");
                this.playerPoints.put(currentPlayer, this.playerPoints.get(currentPlayer) + 1);

                if (this.board.getPieces().stream().noneMatch(card -> ((Card) card).isActive())) {
                    try {
                        ASCIIArtGenerator.printTextArt("Resultat: " + Collections.max(this.playerPoints.entrySet(), Map.Entry.comparingByValue()).getValue() + " zu " + Collections.min(this.playerPoints.entrySet(), Map.Entry.comparingByValue()).getValue(), ASCIIArtGenerator.ART_SIZE_SMALL, ASCIIArtGenerator.ASCIIArtFont.ART_FONT_SANS_SERIF, "$");

                        printVictory(Collections.max(this.playerPoints.entrySet(), Map.Entry.comparingByValue()).getKey());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Alle Paare wurden gefunden!");
                    break;
                }

            } else {
                openCards.forEach(card -> card.showText(false));
                System.out.println("  -> Es wurde kein Paar gefunden!");
                swapCurrentPlayer();
                System.out.println("  -> Der Spieler " + currentPlayer.getName() + " ist nun an der Reihe.");
                System.out.println("  -> Die Karten werden in 5 Sekunden wieder verdeckt.");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                updateBoard(currentPlayer);
            }


        }
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

                this.board.getPieces().stream().filter(Card.class::isInstance).filter(piece -> ((Card) piece).getName() == null).findFirst().ifPresent(piece -> ((Card) piece).setName(type));
            }
        }
    }

    private void loadCardsFromFile() throws IOException {
        this.cards = Files.readAllLines(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("memoryWords.txt")).getFile()).toPath());
    }

    private String pickRandom() {
        Collections.shuffle(cards);
        String type = cards.get(0);
        cards.remove(0);
        return type;
    }
}
