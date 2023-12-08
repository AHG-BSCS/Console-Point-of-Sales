public class Item {
    private int itemPk;
    private String product;
    private float price;
    private int quantity;
    private int stock;
    private int classification;

    public int getItemPk() {
        return itemPk;
    }
    public void setItemPk(int itemPk) {
        this.itemPk = itemPk;
    }

    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }

    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getClassification() {
        return classification;
    }
    public void setClassification(int classification) {
        this.classification = classification;
    }
}