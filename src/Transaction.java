public class Transaction {
    private int transactionPk;
    private int employeeId;
    private int branchId;
    private String dateTime;
    private float totalPrice;
    private float cash;
    private float change;

    public int getTransactionPk() {
        return transactionPk;
    }
    public void setTransactionPk(int transactionPk) {
        this.transactionPk = transactionPk;
    }

    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getBranchId() {
        return branchId;
    }
    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public float getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getCash() {
        return cash;
    }
    public void setCash(float cash) {
        this.cash = cash;
    }
    
    public float getChange() {
        return change;
    }
    public void setChange(float change) {
        this.change = change;
    }
}
