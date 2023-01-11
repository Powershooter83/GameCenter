package ch.romere.player;

import ch.romere.board.Position;
import ch.romere.exceptions.InputIsNotValidPositionException;

import java.util.Scanner;

public class PlayerInput {

    private final Scanner scanner = new Scanner(System.in);

    public Position getInputPosition() {
        String input = scanner.nextLine().replaceAll("\\s", "").toLowerCase();

        if (input.length() < 2) {
            throw new InputIsNotValidPositionException();
        }

        if (Character.isLetter(input.charAt(0)) && Character.isDigit(input.charAt(1))) {
            return new Position(Character.getNumericValue(input.charAt(1)), input.charAt(0) - 'a' + 1);
        } else if (Character.isDigit(input.charAt(0)) && Character.isLetter(input.charAt(1))) {
            return new Position(Character.getNumericValue(input.charAt(0)), input.charAt(1) - 'a' + 1);
        } else {
            throw new InputIsNotValidPositionException();
        }
    }

    public int getInputNumber() {
        String input = scanner.nextLine().replaceAll("\\s", "");
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InputIsNotValidPositionException();
        }
    }


}
