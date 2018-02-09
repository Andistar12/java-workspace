import java.util.ArrayList;

/**
 * Created by Andy Nguyen on 2/8/2018.
 */
public class GoldilocksBinary {

    public static void main(String args[]) {

        //Get input
        int input = 292;

        //Determine highest power
        int power = 0;
        while (Math.pow(2, power) < input) power++;
        power--;

        //Convert to binary form
        int remaining = input;
        ArrayList<Integer> binary = new ArrayList<>();
        while (power >= 0) {
            if (Math.pow(2, power) < remaining) {
                remaining -= Math.pow(2, power);
                binary.add(1);
            } else binary.add(0);
            power--;
        }

        //Parse array
        int sum = 0;
        for (int value : binary) {
            if (value == 0) {
                sum -= 1;
            } else {
                sum += 1;
            }
        }

        //Print output
        if (sum < 0) {
            System.out.println("Too light");
        } else if (sum > 0) {
            System.out.println("Too heavy");
        } else {
            System.out.println("Just right");
        }
    }

}
