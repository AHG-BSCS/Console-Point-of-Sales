import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Report implements Menu{
    @Override
    public void choices() {
        while (true) {
            System.out.println("[1] List of Transaction");
            System.out.println("[2] Statistics");
            System.out.println("[0] Back");
            System.out.print("Report #: ");

            if (select())
                break;
        }
    }

    @Override
    public boolean select() {
        int choice = Functions.getChoice();

        switch (choice) {
            case 0:
                Functions.clearConsole();
                return true;
            case 1:
                Functions.clearConsole();
                listOfTransactionSelected();
                break;
            case 2:
                Functions.clearConsole();
                statisticsSelected();
                break;
            default:
                Functions.clearConsole();
                System.out.println("Invalid Selection!\n");
        }
        return false;
    }

    private void listOfTransactionSelected() {
        while (true) {
            ArrayList<Transaction> transactions = new ArrayList<>();
            DatabaseHelper databaseHelper = new DatabaseHelper();

            transactions = databaseHelper.getTransactions();
            listTrasactions(transactions);
            validateTrasaction(transactions, databaseHelper);
        }
    }

    private void listTrasactions(ArrayList<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            System.out.println("[" + transaction.getTransactionPk() + "] " + 
                                transaction.getDateTime() + " -> " + 
                                transaction.getTotalPrice() + "\n");
        }
    }

    private void validateTrasaction(ArrayList<Transaction> transactions, DatabaseHelper databaseHelper) {
        ArrayList<TransactionItem> transactionItems = new ArrayList<>();

        while (true) {
            System.out.println("\n[0] Back");
            System.out.print("Transaction ID: ");
            
            Transaction selectedTransaction = new Transaction();
            int transactionId = Functions.getChoice();

            if (transactionId == 0) {
                Functions.clearConsole();
                return;
            }

            for (Transaction transaction : transactions) {
                if (transaction.getTransactionPk() == transactionId) {
                    selectedTransaction = transaction;
                    break;
                }
            }

            if (selectedTransaction.getDateTime() != null) {
                transactionItems = databaseHelper.getTransactionItem(transactionId);
                displayTransaction(selectedTransaction, transactionItems, databaseHelper);
                break;
            }
            else {
                Functions.clearConsole();
                System.out.println("Transaction does not exist!\n");
                break;
            }
        }
    }

    private void displayTransaction(Transaction transaction, ArrayList<TransactionItem> transactionItems, DatabaseHelper databaseHelper) {
        Functions.clearConsole();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss a");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        LocalDateTime localDateTime = LocalDateTime.parse(transaction.getDateTime(), dateTimeFormatter);

        for (TransactionItem transactionItem : transactionItems) {
            System.out.println("[" + transactionItem.getItemId() + "] " + 
                                databaseHelper.getItem(transactionItem.getItemId()).getProductName());
            System.out.println(transactionItem.getQuantity() + "pc * " +
                                String.format("%,.2f", transactionItem.getPrice()) + " = " + 
                                String.format("%,.2f", transactionItem.getItemTotalPrice()) + "\n");
        }

        System.out.println("TOTAL : " + String.format("%,.2f", transaction.getTotalPrice()));
        System.out.print("CASH: " + String.format("%,.2f", transaction.getCash()));
        System.out.println("\nCHANGE: " + String.format("%,.2f", transaction.getChange()));

        System.out.println("\nVatable Sales: " + String.format("%,.2f", transaction.getTotalPrice()));
        System.out.println("Vat Amount: " + String.format("%,.2f", (transaction.getTotalPrice() * 0.12)));

        System.out.println("\nPOS Transaction ID: " + transaction.getTransactionPk());
        System.out.println("Date: " + localDateTime.format(dateFormatter));
        System.out.println("Time: " + localDateTime.format(timeFormatter));

        System.out.println("\n=================================");
        System.out.print("Press Enter to go back...");
        Functions.getChoiceInString();
        Functions.clearConsole();
    }

    private void statisticsSelected() {
        DatabaseHelper databaseHelper = new DatabaseHelper();
        double grossAmount = databaseHelper.getGrossAmount();

        System.out.println("====== S T A T I S T I C S ======\n");
        System.out.println("Items Sold: " + databaseHelper.countTableRow("transaction_item") + "\n");
        System.out.println("Registered Transactions: " + databaseHelper.countTableRow("transactions") + "\n");
        System.out.println("Gross Amount: " + String.format("%,.2f", grossAmount) + "\n");
        System.out.println("Estimated Net Amount: " + String.format("%,.2f", (grossAmount * 0.12)));

        System.out.println("\n=================================");
        System.out.print("Press Enter to go back...");
        Functions.getChoiceInString();
        Functions.clearConsole();
    }
}