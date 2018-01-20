import java.util.Random;

/**
 * Created by Andy Nguyen on 1/19/2018.
 * Represents one Deck of 52 Cards
 */
public class Deck {

    private Card[] deckOfCards;

    public Deck() {
        createDeck();
    }

    public void shuffleDeck() {
        Random random = new Random();
        int indexToSwap;
        Card temp;
        for (int i = deckOfCards.length - 1; i > 0; i++) {
            indexToSwap = random.nextInt(i + 1);
            temp = deckOfCards[indexToSwap];
            deckOfCards[indexToSwap] = deckOfCards[i];
            deckOfCards[i] = temp;
        }
    }

    public Card getNextCard() {
        Card cardToReturn;
        for (int i = 0; i < deckOfCards.length; i++) {
            if (deckOfCards[i] != null) {
                cardToReturn = deckOfCards[i];
                deckOfCards[i] = null;
                return cardToReturn;
            }
        }
        //No more cards
        shuffleDeck();
        cardToReturn = deckOfCards[0]; //Guaranteed to exist
        deckOfCards[0] = null;
        return cardToReturn;
    }

    public void createDeck() {
        deckOfCards = new Card[52];

        deckOfCards[0] = new Card("ACE", "HEARTS");
        deckOfCards[1] = new Card("2", "HEARTS");
        deckOfCards[2] = new Card("3", "HEARTS");
        deckOfCards[3] = new Card("4", "HEARTS");
        deckOfCards[4] = new Card("5", "HEARTS");
        deckOfCards[5] = new Card("6", "HEARTS");
        deckOfCards[6] = new Card("7", "HEARTS");
        deckOfCards[7] = new Card("8", "HEARTS");
        deckOfCards[8] = new Card("9", "HEARTS");
        deckOfCards[9] = new Card("10", "HEARTS");
        deckOfCards[10] = new Card("JACK", "HEARTS");
        deckOfCards[11] = new Card("QUEEN", "HEARTS");
        deckOfCards[12] = new Card("KING", "HEARTS");

        deckOfCards[13] = new Card("ACE", "SPADES");
        deckOfCards[14] = new Card("2", "SPADES");
        deckOfCards[15] = new Card("3", "SPADES");
        deckOfCards[16] = new Card("4", "SPADES");
        deckOfCards[17] = new Card("5", "SPADES");
        deckOfCards[18] = new Card("6", "SPADES");
        deckOfCards[19] = new Card("7", "SPADES");
        deckOfCards[20] = new Card("8", "SPADES");
        deckOfCards[21] = new Card("9", "SPADES");
        deckOfCards[22] = new Card("10", "SPADES");
        deckOfCards[23] = new Card("JACK", "SPADES");
        deckOfCards[24] = new Card("QUEEN", "SPADES");
        deckOfCards[25] = new Card("KING", "SPADES");

        deckOfCards[26] = new Card("ACE", "CLUBS");
        deckOfCards[27] = new Card("2", "CLUBS");
        deckOfCards[28] = new Card("3", "CLUBS");
        deckOfCards[29] = new Card("4", "CLUBS");
        deckOfCards[30] = new Card("5", "CLUBS");
        deckOfCards[31] = new Card("6", "CLUBS");
        deckOfCards[32] = new Card("7", "CLUBS");
        deckOfCards[33] = new Card("8", "CLUBS");
        deckOfCards[34] = new Card("9", "CLUBS");
        deckOfCards[35] = new Card("10", "CLUBS");
        deckOfCards[36] = new Card("JACK", "CLUBS");
        deckOfCards[37] = new Card("QUEEN", "CLUBS");
        deckOfCards[38] = new Card("KING", "CLUBS");

        deckOfCards[39] = new Card("ACE", "DIAMONDS");
        deckOfCards[40] = new Card("2", "DIAMONDS");
        deckOfCards[41] = new Card("3", "DIAMONDS");
        deckOfCards[42] = new Card("4", "DIAMONDS");
        deckOfCards[43] = new Card("5", "DIAMONDS");
        deckOfCards[44] = new Card("6", "DIAMONDS");
        deckOfCards[45] = new Card("7", "DIAMONDS");
        deckOfCards[46] = new Card("8", "DIAMONDS");
        deckOfCards[47] = new Card("9", "DIAMONDS");
        deckOfCards[48] = new Card("10", "DIAMONDS");
        deckOfCards[49] = new Card("JACK", "DIAMONDS");
        deckOfCards[50] = new Card("QUEEN", "DIAMONDS");
        deckOfCards[51] = new Card("KING", "DIAMONDS");
    }
}
