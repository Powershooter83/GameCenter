package ch.romere.logic;

import java.util.ArrayList;

public abstract class Game {

    protected Board board;
    protected ArrayList<Player> players;
    public abstract void start();

}
