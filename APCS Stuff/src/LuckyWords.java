package com.company;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * 2/6/18
 * By Andy and Hayden
 */

public class LuckyWords {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String input = "";
        System.out.print("Enter input: ");
        input = in.nextLine();


        String[] splitInput = input.split(" ");
        ArrayList<String> luckyWords = new ArrayList<>();

        for (String word : splitInput) {
            if (hasDuplicateVowels(word)) {
                luckyWords.add(word);
            }
        }

        System.out.println(luckyWords);

    }

    public static boolean hasDuplicateVowels(String input) {
        if (input.length() <= 1) return false;

        char[] characters = input.toCharArray();
        boolean hasDuplicateVowel = false;
        char currentChar, lastChar = characters[0];

        for (int i = 1; i < characters.length; i++) {
            currentChar = characters[i];

            if (currentChar == lastChar) {

                if (currentChar == 'a' || currentChar == 'e' || currentChar == 'i' || currentChar == 'o' || currentChar == 'u') {
                    hasDuplicateVowel = true;
                } else {
                    return false;
                }

            }

            lastChar = currentChar;
        }

        return hasDuplicateVowel;
    }
}
