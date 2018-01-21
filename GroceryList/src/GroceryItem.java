/**
 * Created by Andy Nguyen on 1/20/2018.
 * One single grocery item to be purchased
 */
public class GroceryItem {

    private String name;
    private double price;
    private String type;

    public GroceryItem(String name, double price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }
}
