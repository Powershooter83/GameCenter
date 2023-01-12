package ch.romere.exceptions;

public class InputIsNotAValidPositionException extends RuntimeException {
    public InputIsNotAValidPositionException() {
        super("Die eingegebene Position ist keine gültige Position!");
    }
}
