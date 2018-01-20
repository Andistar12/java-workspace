/**
 * Created by Andy Nguyen on 1/19/2018.
 * Represents one Player, who hits/stands according to BlackJack rules
 */
public class Dealer {

    private static final int hand_size = 10;

    private Card[] hand;
    private String name;

    public Dealer() {
        hand = new Card[hand_size];
        this.name = "Dealer";
    }

    public void addCardToHand(Card card) {
        for (int i = 0; i < hand.length; i++) {
            if (hand[i] != null) {
                hand[i] = card;
                break;
            }
        }
    }

    public int calculateValueOfHand() {
        int sum = 0;
        for (int i = 0; i < hand.length; i++) {
            if (hand[i] != null) {
                sum += hand[i].getValue();
            } else {
                break;
            }
        }
        return sum;
    }

    public boolean hitOrStand() {
        //True is hit, false is stand
        return calculateValueOfHand() < 16;
    }

    public String getName() {
        return name;
    }
}
