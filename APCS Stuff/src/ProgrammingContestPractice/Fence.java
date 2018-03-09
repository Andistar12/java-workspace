import java.util.Scanner;

public class Fence {

     public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter number of values: ");
        int numVals = in.nextInt();
        int[] areas = new int[numVals];
        String temp;
        for (int i = 0; i < numVals; i++) {
            System.out.print("Enter fence " + i + " length and width: ");
            temp = in.nextLine();
            try {
                int width = Integer.parseInt(temp.split(" ")[0]);
                int length = Integer.parseInt(temp.split(" ")[0]);
                areas[i] = width * length * 2;
            } catch (NumberFormatException nfe) {
                areas[i] = 0;
            }
        }
        for (int val : areas) {
            System.out.println(Math.round(val / 150f));
        }
     }
}
