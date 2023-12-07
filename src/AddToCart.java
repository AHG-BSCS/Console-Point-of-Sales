public class AddToCart {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private double totalPrice;

    public AddToCart(int id, String name, double price, int quantity, double totalPrice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double totalPrice() {
        return totalPrice;
    }
}
