package grocerylist;

/**
 * Created by Andy Nguyen on 1/20/2018.
 * The list of groceries to buy
 */
public class GroceryList {

    private GroceryItem[] groceries;

    public GroceryList(int size) {
        groceries = new GroceryItem[size];
    }

    public void addGrocery(GroceryItem newItem) {
        for (int i = 0; i < groceries.length; i++) {
            if (groceries[i] == null) {
                groceries[i] = newItem;
                break;
            }
        }
    }

    public void removeGrocery(int index) {
        if (index < 0 || index > groceries.length - 1) {
            return;
        }
        groceries[index] = null;
        for (int i = index + 1; i < groceries.length; i++) {
            groceries[i - 1] = groceries[i];
        }
    }

    public void printGroceries() {
        System.out.println("Grocery total: " + calculateTotal());
        for (int i = 0; i < groceries.length; i++) {
            if (groceries[i] != null) {
                System.out.println(i + ". " + groceries[i].getName() + " (" + groceries[i].getType() + ") - $" + groceries[i].getPrice());
            }
        }
    }

    public double calculateTotal() {
        double sum = 0;
        for (int i = 0; i < groceries.length; i++) {
            if (groceries[i] != null) {
                sum = sum + groceries[i].getPrice();
            }
        }
        return sum;
    }

}
