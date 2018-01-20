import java.util.Scanner;

/**
 * Created by Andy Nguyen on 1/19/2018.
 * Represents one Player, who hits/stands according to user input
 */
public class Player {

    private static final int hand_size = 10;

    private Scanner scanner;
    private Card[] hand;
    private String name;

    public Player(String name) {
        scanner = new Scanner(System.in);
        hand = new Card[hand_size];
        this.name = name;
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
        String input = "";

        while (!input.equals("Y") && !input.equals("N")) {
            System.out.println("Hit or stand? Y for hit, N for stand");
            input = scanner.next();
            input = input.toUpperCase();
        }
        return input.equals("Y");
    }

    public String getName() {
        return name;
    }
}
