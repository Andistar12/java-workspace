import java.util.Scanner;

public class PPrime {

     public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter number of values: ");
        int numVals = in.nextInt();
        int[] values = new int[numVals];
        for (int i = 0; i < numVals; i++) {
            System.out.print("Enter val " + i + ": ");
            values[i] = in.nextInt();
        }
        for (int val : values) {
            if (!isPrime(val)) System.out.println("no");
            else if (!isPalindrome(val)) System.out.println("no");
            else System.out.println("yes");
        }
     }
     
     public static boolean isPrime(int val) {
         if (val % 2 == 0) return false;
         for(int i = 3; i*i <= val; i += 2) if (val % i == 0) return false;
         return true;
     }
     
     public static boolean isPalindrome(int val) {
         char[] array = (val + "").toCharArray();
         for (int i = 0; i < array.length; i++) {
             if (array[i] != array[array.length - 1 - i]) return false;
         }
         return true; 
     }
}
