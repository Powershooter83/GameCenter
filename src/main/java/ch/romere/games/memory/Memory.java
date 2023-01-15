package ch.romere.games.memory;

import ch.romere.board.Position;
import ch.romere.logic.Game;
import ch.romere.player.Player;
import ch.romere.utils.ASCIIArtGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Memory extends Game {

    private final HashMap<Player, Integer> playerPoints = new HashMap<>();
    private List<String> cards = new ArrayList<>();

    public Memory(List<Player> players) {
        super("Memory", """
                Memory ist ein Gedaechtnisspiel, bei dem die Spieler versuchen, so viele Paare wie moeglich zu finden.
                Die Spieler muessen die Kartenpaare aufdecken und versuchen, sie zu finden.
                Wenn ein Spieler ein Paar gefunden hat, erhaelt er einen Punkt.
                Das Spiel endet, wenn alle Kartenpaare gefunden wurden.""", 3, 6, 21, 5, false, false, players);
        this.players.forEach(player -> playerPoints.put(player, 0));
        start();

    }

    @Override
    public void start() {
        printTitle();
        printDescription();

        currentPlayer = getRandomPlayer();
        printStartingPlayer();

        loadCards();
        printBoard(hasHorizontalLabeling, hasVerticalLabeling);

        eventHandler();
    }

    /**
     * Diese Methode dient als eine Art von "eventHandler".
     * Sie holt sich die Position koordinaten von dem Spieler Input und prüft dann ob das Format korrekt ist.
     * Sollte das Format stimmen wird die Karte aufgedeckt.
     * Sollten zwei Karten geöffnet sein, wird auf ein Paar geprüft werden.
     */
    @Override
    public void eventHandler() {
        while (true) {
            Integer input = playerInput.getInputNumber();

            if (input == null || input > BOARD_WIDTH * BOARD_HEIGHT || input <= 0) {
                System.out.println("  -> Beachte bitte das Format: [1, 2, 3...]");
                continue;
            }

            this.board.getPieces().stream().filter(card -> ((Card) card).getNumber() == input).forEach(card -> ((Card) card).showText(true));
            updateBoard(currentPlayer);

            if (this.board.getPieces().stream().filter(card -> ((Card) card).isActive() && ((Card) card).isTextShown()).count() == 2) {
                checkForPair();
                break;
            }

        }
    }

    /**
     * Diese Methode prüft auf ein Paar. Dabei wird die openCards Liste angeschaut und uberprüft,
     * ob die geöffneten Karten den selben Wert haben. Falls dies der Fall ist, bekommt der aktuelle Spieler einen Punkt
     * und darf nochmals ran.
     * Sollten sie einen unterschiedlichen Wert haben wird eine andere Meldung in die Konsoel gedruckt.
     * Befor der neue (oder alte) Spieler an der Reihe ist wird noch ein sleep von 5 Sekunden gestartet, bis die Karten zugedeckt werden.
     */
    private void checkForPair() {
        List<Card> openCards = this.board.getPieces().stream().filter(card -> ((Card) card).isTextShown()).map(Card.class::cast).filter(Card::isActive).toList();

        if (openCards.get(0).getName().equals(openCards.get(1).getName())) {
            openCards.forEach(Card::setInactive);
            System.out.println("  -> Ein Paar wurde gefunden! <-  ");
            System.out.println("     Spieler " + currentPlayer.getName() + " hat einen Punkt!");
            System.out.println("  !! Der Spieler " + currentPlayer.getName() + " ist nochmals an der Reihe !!");
            this.playerPoints.put(currentPlayer, this.playerPoints.get(currentPlayer) + 1);


        }
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

    /**
     * Diese Methode prüft auf einen Sieger (oder auf ein Unentschieden).
     * Sollte die Card Liste keine aktiven Karten mehr besitzen, so wurden alle Karten gefunden.
     * Dann wird einfach überprüft, welcher Spieler am meisten Punkte hat.
     * Im falle eines Unentschiedens wird anstatt printVictory printDraw aufgerufen.
     */
    public boolean checkForWin() {
        if (this.board.getPieces().stream().noneMatch(card -> ((Card) card).isActive())) {
            try {
                System.out.println(System.lineSeparator());
                System.out.println(" -> Alle Paare wurden gefunden! <- ");
                System.out.println(System.lineSeparator());
                ASCIIArtGenerator.printTextArt("Resultat: " + Collections.max(this.playerPoints.entrySet(), Map.Entry.comparingByValue()).getValue() + " zu " + Collections.min(this.playerPoints.entrySet(), Map.Entry.comparingByValue()).getValue(), ASCIIArtGenerator.ART_SIZE_SMALL, ASCIIArtGenerator.ASCIIArtFont.ART_FONT_SANS_SERIF, "$");

                if (Collections.max(this.playerPoints.entrySet(), Map.Entry.comparingByValue()).getValue() == Collections.min(this.playerPoints.entrySet(), Map.Entry.comparingByValue()).getValue()) {
                    printDraw();
                    return true;
                }

                printVictory(Collections.max(this.playerPoints.entrySet(), Map.Entry.comparingByValue()).getKey());
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    /**
     * Diese Methode lädt die Karten auf das Board. Dabei wird die loadCardsFromFile()
     * Methode aufgerufen und dann die ganze Liste random gemischt. Es wird für jedes Feld
     * auf dem Brett eine neue Karte erstellt und dann einen Wert zugewiesen.
     */
    private void loadCards() {
        loadCardsFromFile();
        Collections.shuffle(cards);

        int counting = 1;

        for (int yAxis = 0; yAxis < BOARD_HEIGHT; yAxis++) {
            for (int xAxis = 0; xAxis < BOARD_WIDTH; xAxis++) {
                board.addPiece(new Card(counting, new Position(xAxis, yAxis)));
                counting++;
            }
        }

        for (int pairNumber = 0; pairNumber < BOARD_WIDTH * BOARD_HEIGHT / 2; pairNumber++) {
            final String type = this.pickRandom();
            for (int card = 0; card < 2; card++) {
                Collections.shuffle(this.board.getPieces());
                this.board.getPieces().stream().filter(Card.class::isInstance).filter(piece -> ((Card) piece).getName() == null).findFirst().ifPresent(piece -> ((Card) piece).setName(type));
            }
        }
    }

    /**
     * Diese Methode lädt alle Möglichen Begriffe aus der memoryWords.txt Datei in die Cards Liste.
     * Dabei ist eine Zeile immer ein Wort.
     */
    private void loadCardsFromFile() {
        try {
            this.cards = Files.readAllLines(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("memoryWords.txt")).getFile()).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Diese Methode holt und entfernt dann eine Zufällige Karte von der cards liste.
     * @return Der aus der Liste entfernter Begriff.
     */

    private String pickRandom() {
        Collections.shuffle(cards);
        return cards.remove(0);
    }
}
