package ch.romere.utils;

import ch.romere.exceptions.InputIsNotValidPositionException;
import ch.romere.board.Position;

import java.util.Scanner;

public class PlayerInput {

    private final Scanner scanner = new Scanner(System.in);

    public Position getInputPosition() {
        String input = scanner.nextLine().replaceAll("\\s", "");

        if(input.length() < 2){
            return new Position(Character.getNumericValue(input.charAt(0)), 0);
        }

        if(!Character.isDigit(input.charAt(0)) && !Character.isDigit(input.charAt(1))){
            throw new InputIsNotValidPositionException();
        }
        return new Position(Character.getNumericValue(input.charAt(0)), Character.getNumericValue(input.charAt(1)));
    }

}
