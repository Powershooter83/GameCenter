package ch.romere.player;

import ch.romere.board.Position;

import java.util.Scanner;

public class PlayerInput {

    private final Scanner scanner;

    public PlayerInput() {
        scanner = new Scanner(System.in);
    }

    PlayerInput(final Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Diese Methode gibt die eingegebene "Position" zurück.
     * Sollte sich beim eingegebenen Text nicht um eine valide Position (nur format prüfung)
     * handeln, wird null zurückgegeben.
     * Die Methode wandelt buchstaben automatisch in ihre zahlenwerte um (a -> 1, etc)
     * Zudem wird die Koordinate um eins Subtrahiert, da das Code interne Koordinatensystem bei 0 beginnt.
     * @return Die vom Spieler eingegebene Position.
     */
    public Position getInputPosition() {
        final String input = scanner.nextLine().replaceAll("\\s", "").toLowerCase();

        if (input.length() != 2 || Character.isLetter(input.charAt(0)) && Character.isLetter(input.charAt(1)) ||
                Character.isDigit(input.charAt(0)) && Character.isDigit(input.charAt(1))) {
            return null;
        }

        return Character.isLetter(input.charAt(0)) && Character.isDigit(input.charAt(1)) ?
                new Position(Character.getNumericValue(input.charAt(1)) - 1, input.charAt(0) - 'a') :
                new Position(Character.getNumericValue(input.charAt(0) - 1), input.charAt(1) - 'a');
    }

    /**
     * Diese Methode gibt die eingegebene "Spalte" zurück.
     * Sollte sich beim eingegebenen Text nicht um eine valide Zahl
     * handeln, wird null zurückgegeben.
     * @return Die vom Spieler eingegebene Zahl.
     */
    public Integer getInputNumber() {
        try {
            return Integer.parseInt(scanner.nextLine().replaceAll("\\s", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
