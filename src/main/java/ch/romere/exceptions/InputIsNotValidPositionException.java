package ch.romere.exceptions;

public class InputIsNotValidPositionException extends RuntimeException {
    public InputIsNotValidPositionException() {
        super("Die eingegebene Position ist keine gültige Position!");
    }
}
