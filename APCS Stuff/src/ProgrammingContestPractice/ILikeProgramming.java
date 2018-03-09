import java.util.Scanner;

public class HelloWorld{

     public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter number of likes: ");
        int numLikes = in.nextInt();
        String[] likes = new String[numLikes];
        for (int i = 0; i < numLikes; i++) {
            System.out.print("Enter like " + i + ": ");
            likes[i] = in.next();
        }
        for (String s : likes) {
            System.out.println("I like " + s + ".");
        }
     }
}
