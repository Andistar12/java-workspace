import java.util.Scanner;

/**
 * Created by Andy Nguyen on 2/14/2018.
 */
public class ChaosTheory {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        double init, scalar;
        System.out.print("Enter initial x value: ");
        init = in.nextDouble();
        System.out.print("Enter scalar: ");
        scalar = in.nextDouble();

        double current = init;
        for (int i = 0; i < 50; i++) {
            current = 2 * current * (1 - current);
        }

        System.out.println("Resultant: " + current);
    }

}
