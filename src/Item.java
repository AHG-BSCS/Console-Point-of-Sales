public class Item extends Product {
    private int stock;
    private int classification;

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