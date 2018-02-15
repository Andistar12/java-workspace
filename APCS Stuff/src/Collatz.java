/**
 * Created by Andy Nguyen on 2/14/2018.
 */
public class Collatz {

    public static void main(String args[]) {

        System.out.println("Checking 1 to 200. Non-printed numbers are assumed to have converged");
        int max = 0;
        int iterations = 0;
        int running_total;
        for (int i = 1; i < 201; i++) {
            running_total = i;
            iterations = 0;
            while (running_total != 4 && ++iterations != 1000) {
                running_total = (running_total % 2 == 0) ? running_total / 2 : running_total * 3 + 1;
            }
            if (running_total != 4) {
                System.out.println("Value " + i + " does not converge after 1000");
            } else {
                if (iterations > max) max = iterations;
            }
        }
        System.out.println("All numbers checked");
        System.out.println("Max iterations: " + max);

    }

    public static int collatz(int value) {
        return (value % 2 == 0) ? value / 2 : value * 3 + 1;
    }


}
