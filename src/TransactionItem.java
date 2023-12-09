public class TransactionItem extends Product{
    private int transactionId;
    private float itemTotalPrice;

    public int getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public float getItemTotalPrice() {
        return itemTotalPrice;
    }
    public void setItemTotalPrice(float itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }
}
