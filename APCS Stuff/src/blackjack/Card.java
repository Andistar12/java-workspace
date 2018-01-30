package blackjack;

/**
 * Created by Andy Nguyen on 1/19/2018.
 * Represents one single blackjack.Card in the deck
 */
public class Card {

    private String suit;
    private String face;
    private int value;

    public Card(String suit, String face) {
        this.suit = suit;
        this.face = face;

        //We can figure out the value from the face
        switch (face.toUpperCase()) {
            case "ACE":
            case "1":
                value = 1;
                break;
            case "2":
                value = 2;
            case "3":
                value = 3;
                break;
            case "4":
                value = 4;
                break;
            case "5":
                value = 5;
                break;
            case "6":
                value = 6;
                break;
            case "7":
                value = 7;
                break;
            case "8":
                value = 8;
                break;
            case "9":
                value = 9;
                break;
            case "10":
            case "JACK":
            case "QUEEN":
            case "KING":
                value = 10;
                break;
        }
    }

    /*
     * Getters and setters for our variables
     * Since value is the only value that could change (ACE = 1 or 11)
     * We only need to create a value setter
     */

    public String getSuit() {
        return suit;
    }

    public String getFace() {
        return face;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
