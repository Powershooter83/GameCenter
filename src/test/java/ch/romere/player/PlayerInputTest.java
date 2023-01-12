package ch.romere.player;


import ch.romere.board.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerInputTest {

    private final Scanner mockScanner = mock(Scanner.class);
    private final PlayerInput playerInput = new PlayerInput(mockScanner);

    @ParameterizedTest
    @ValueSource(strings = {"a1", "1a", "a 1", "1 a", "a1 ", " a1", " a1 ", " a 1 ", " a 1"})
    public void getInputPositionWithValidInput(final String input){
        // arrange
        when(mockScanner.nextLine()).thenReturn(input);
        Position expectedPosition = new Position(0, 0);
        //act
        Position actualPosition = playerInput.getInputPosition();
        // assert
        assertEquals(expectedPosition, actualPosition);
    }

    @Test
    public void getInputPositionWithInputTooShort(){
        // arrange
        when(mockScanner.nextLine()).thenReturn("a");
        //act
        Position actualPosition = playerInput.getInputPosition();
        // assert
        assertNull(actualPosition);
    }

    @ParameterizedTest
    @ValueSource(strings = {"aa", "11", "a11", "11a", "aa1", "1aa", "aa1 ", " aa1", " aa1 ", " aa 1 ", " aa 1"})
    public void getInputPositionWithInvalidInput(final String input){
        // arrange
        when(mockScanner.nextLine()).thenReturn(input);
        //act
        Position actualPosition = playerInput.getInputPosition();
        // assert
        assertNull(actualPosition);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5234234, -41})
    public void getInputNumberWithValidInput(final int input){
        // arrange
        when(mockScanner.nextLine()).thenReturn(String.valueOf(input));
        //act
        Integer actualNumber = playerInput.getInputNumber();
        // assert
        assertEquals(input, actualNumber);
    }

    @ParameterizedTest
    @ValueSource(strings = {"bruh", "qc", "-1.12"})
    public void getInputNumberWithValidInput(final String input){
        // arrange
        when(mockScanner.nextLine()).thenReturn(String.valueOf(input));
        //act
        Integer actualNumber = playerInput.getInputNumber();
        // assert
        assertNull(actualNumber);
    }



}