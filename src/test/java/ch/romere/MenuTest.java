package ch.romere;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.PrintStream;
import java.util.Scanner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MenuTest {

    private final Scanner mockScanner = mock(Scanner.class);
    private final PrintStream mockedPrintStream = Mockito.mock(PrintStream.class);
    private final Menu menu = new Menu(mockScanner);


    @Test
    void printGames() {
        // arrange
        System.setOut(mockedPrintStream);
        // act
        menu.printGames();
        // assert
        Mockito.verify(mockedPrintStream).println("Bitte selektiere ein Spiel zum fortfahren:");
        Mockito.verify(mockedPrintStream).println("1. TicTacToe");
        Mockito.verify(mockedPrintStream).println("2. VierGewinnt");
        Mockito.verify(mockedPrintStream).println("4. Exit");
    }

    @Test
    void printPlayerSelection(){
        // arrange
        System.setOut(mockedPrintStream);
        when(mockScanner.nextLine()).thenReturn("Power").thenReturn("Test");
        Menu menu = new Menu(mockScanner);
        // act
        menu.printPlayerSelection();
        // assert
        Mockito.verify(mockedPrintStream).println("     ---> Bitte gib einen Spielernamen fuer Spieler 1 ein <---");
        Mockito.verify(mockedPrintStream).println("     ---> Bitte gib einen Spielernamen fuer Spieler 2 ein <---");
        Mockito.verify(mockedPrintStream).println("\nHerzlich Willkommen Power und Test!");
    }

}