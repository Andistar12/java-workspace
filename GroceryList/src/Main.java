import java.util.Scanner;

/**
 * Created by Andy Nguyen on 1/20/2018.
 */
public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean quitFlag = false;

        System.out.print("Enter size of grocery list: ");
        int groceryListSize = in.nextInt();

        GroceryList list = new GroceryList(groceryListSize);

        int input;
        while (!quitFlag) {
            System.out.println("\n1 - add item");
            System.out.println("2 - delete item");
            System.out.println("3 - view list");
            System.out.println("4 - quit\n");
            System.out.print("Enter operation:");
            input = in.nextInt();
            System.out.println();

            switch(input) {
                case 1:
                    System.out.println("Enter name:" );
                    String name = in.next();
                    System.out.println("Enter type:" );
                    String type = in.next();
                    System.out.println("Enter price:" );
                    double price = in.nextDouble();

                    GroceryItem item = new GroceryItem(name, price, type);
                    list.addGrocery(item);
                    System.out.println("Item added");
                    break;
                case 2:
                    System.out.println("Enter item index to remove:" );
                    int index = in.nextInt();
                    list.removeGrocery(index);
                    System.out.println("Item removed");
                    break;
                case 3:
                    list.printGroceries();
                    break;
                case 4:
                    quitFlag = true;
                    break;
            }

        }

    }



}
