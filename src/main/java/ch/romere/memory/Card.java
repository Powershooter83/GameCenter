package ch.romere.memory;

import ch.romere.board.Position;
import ch.romere.logic.Piece;

public class Card extends Piece {

    private String name;
    private final int number;
    private boolean isActive = true;
    private boolean showText = false;
    public Card(final int Number, final Position position) {
        this.number = Number;
        this.position = position;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getNumber() {
        return this.number;
    }

    public void showText(final boolean showText) {
        this.showText = showText;
    }

    public boolean isTextShown() {
        return this.showText;
    }

    public void setInactive() {
        this.isActive = false;
    }

    public boolean isActive(){
        return this.isActive;
    }

    @Override
    public String toString() {
        return this.showText ? this.name : String.valueOf(this.number);
    }

}
