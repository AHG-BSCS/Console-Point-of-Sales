import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Report implements Menu {
    @Override
    public void choices() {
        while (true) {
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[1]" + Terminal.DEFAULT + " List of Transaction");
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[2]" + Terminal.DEFAULT + " Statistics");
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[0]" + Terminal.DEFAULT + " Back");
            System.out.print(Terminal.GREEN + "Report #: " + Terminal.DEFAULT);

            if (select()) break;
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
                System.out.println(Terminal.RED + "Invalid Selection!\n" + Terminal.DEFAULT);
        }
        return false;
    }

    private void listOfTransactionSelected() {
        while (true) {
            ArrayList<Transaction> transactions = new ArrayList<>();
            DatabaseHelper databaseHelper = new DatabaseHelper();

            transactions = databaseHelper.getTransactions();
            listTrasactions(transactions);

            if (validateTrasaction(transactions, databaseHelper))
                break;
        }
    }

    private void listTrasactions(ArrayList<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[" + transaction.getTransactionPk() + "] " + Terminal.DEFAULT + 
                                transaction.getDateTime() + " -> " + 
                                transaction.getTotalPrice() + "\n");
        }
    }

    private boolean validateTrasaction(ArrayList<Transaction> transactions, DatabaseHelper databaseHelper) {
        ArrayList<TransactionItem> transactionItems = new ArrayList<>();

        while (true) {
            System.out.println(Terminal.BOLD + Terminal.GREEN + "\n[0]" + Terminal.DEFAULT + " Back");
            System.out.print(Terminal.GREEN + "Transaction ID: " + Terminal.DEFAULT);
            
            Transaction selectedTransaction = new Transaction();
            int transactionId = Functions.getChoice();

            if (transactionId == 0) {
                Functions.clearConsole();
                return true;
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
                System.out.println(Terminal.RED + "Transaction does not exist!\n" + Terminal.DEFAULT);
                break;
            }
        }
        return false;
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

        System.out.println(Terminal.BOLD + "TOTAL : " + Terminal.DEFAULT + String.format("%,.2f", transaction.getTotalPrice()));
        System.out.print(Terminal.BOLD + "CASH: " + Terminal.DEFAULT + String.format("%,.2f", transaction.getCash()));
        System.out.println(Terminal.BOLD + "\nCHANGE: " + Terminal.DEFAULT + String.format("%,.2f", transaction.getChange()));

        System.out.println(Terminal.BOLD + "\nVatable Sales: " + Terminal.DEFAULT + String.format("%,.2f", transaction.getTotalPrice()));
        System.out.println(Terminal.BOLD + "Vat Amount: " + Terminal.DEFAULT + String.format("%,.2f", (transaction.getTotalPrice() * 0.12)));

        System.out.println(Terminal.BOLD + "\nPOS Transaction ID: " + Terminal.DEFAULT + transaction.getTransactionPk());
        System.out.println(Terminal.BOLD + "Date: " + Terminal.DEFAULT + localDateTime.format(dateFormatter));
        System.out.println(Terminal.BOLD + "Time: " + Terminal.DEFAULT + localDateTime.format(timeFormatter));

        pressEnterDisplay();
    }

    private void statisticsSelected() {
        DatabaseHelper databaseHelper = new DatabaseHelper();
        double grossAmount = databaseHelper.getGrossAmount();

        System.out.println(Terminal.BOLD + Terminal.BLUE + "====== S T A T I S T I C S ======\n" + Terminal.DEFAULT);
        System.out.println(Terminal.BOLD + "Items Sold: " + Terminal.DEFAULT + databaseHelper.countTableRow("transaction_item") + "\n");
        System.out.println(Terminal.BOLD + "Registered Transactions: " + Terminal.DEFAULT + databaseHelper.countTableRow("transactions") + "\n");
        System.out.println(Terminal.BOLD + "Gross Amount: " + Terminal.DEFAULT + String.format("%,.2f", grossAmount) + "\n");
        System.out.println(Terminal.BOLD + "Estimated Net Amount: " + Terminal.DEFAULT + String.format("%,.2f", (grossAmount * 0.12)));

        pressEnterDisplay();
    }

    private void pressEnterDisplay() {
        System.out.println("\n=================================");
        System.out.print(Terminal.BLUE + "Press Enter to go back..." + Terminal.DEFAULT);
        Functions.getChoiceInString();
        Functions.clearConsole();
    }
}