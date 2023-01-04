package ch.romere.logic.exceptions;

public class InputIsNotValidPosition extends RuntimeException {
    public InputIsNotValidPosition() {
        super("Die eingegebene Position ist keine gültige Position!");
    }
}
