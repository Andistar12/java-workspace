package blackjack;

import java.util.Scanner;

/**
 * Created by Andy Nguyen on 1/19/2018.
 * Where the game is played. Includes the main(String[]) method
 */
public class BlackJack {

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);

        Player player = new Player("USER");
        Dealer dealer = new Dealer();

        Deck deck = new Deck();
    }
}
