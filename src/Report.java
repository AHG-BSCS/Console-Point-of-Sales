import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Report extends NewTransaction {
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

        // Need to improve
        for (TransactionItem transactionItem : transactionItems) {
            transactionItem.setProductName(databaseHelper.getItem(transactionItem.getItemId()).getProductName());
            if (transactionItem.getProductName().length() > receiptWidth) {
                receiptWidth = transactionItem.getProductName().length();
            }
        }

        printTextWithAsterisk(" T R A N S A C T I O N ");

        for (TransactionItem transactionItem : transactionItems) {
            printTextWithBorder(transactionItem.getProductName());
            printTextWithBorder(transactionItem.getQuantity() + "pc * " +
                                String.format("%,.2f", transactionItem.getPrice()) + " = " + 
                                String.format("%,.2f", transactionItem.getItemTotalPrice()));
            printTextWithBorder("");
        }

        printLine();
        printTextWithStyle(Terminal.BOLD + "TOTAL: " + Terminal.DEFAULT + String.format("%,.2f", transaction.getTotalPrice()));
        printTextWithStyle(Terminal.BOLD + "CASH: " + Terminal.DEFAULT + String.format("%,.2f", transaction.getCash()));
        printTextWithStyle(Terminal.BOLD + "CHANGE: " + Terminal.DEFAULT + String.format("%,.2f", transaction.getChange()));

        printTextWithBorder("");
        printTextWithStyle(Terminal.BOLD + "Vatable Amount: " + Terminal.DEFAULT + String.format("%,.2f", transaction.getTotalPrice()));
        printTextWithStyle(Terminal.BOLD + "VAT: " + Terminal.DEFAULT + String.format("%,.2f", (transaction.getTotalPrice() * 0.12)) + " (12.00%)");

        printTextWithBorder("");
        printTextWithStyle(Terminal.BOLD + "POS Transaction ID: " + Terminal.DEFAULT + transaction.getTransactionPk());
        printTextWithStyle(Terminal.BOLD + "Date: " + Terminal.DEFAULT + localDateTime.format(dateFormatter));
        printTextWithStyle(Terminal.BOLD + "Time: " + Terminal.DEFAULT + localDateTime.format(timeFormatter));
        printBorder();

        receiptWidth = 35;
        pressEnterDisplay();
    }

    private void statisticsSelected() {
        DatabaseHelper databaseHelper = new DatabaseHelper();
        double grossAmount = databaseHelper.getGrossAmount();

        printTextWithAsterisk(" S T A T I S T I C S ");
        printTextWithStyle(Terminal.BOLD + "Items Sold: " + Terminal.DEFAULT + databaseHelper.countTableRow("transaction_item"));
        printTextWithBorder("");
        printTextWithStyle(Terminal.BOLD + "Registered Transactions: " + Terminal.DEFAULT + databaseHelper.countTableRow("transactions"));
        printTextWithBorder("");
        printTextWithStyle(Terminal.BOLD + "Gross Amount: " + Terminal.DEFAULT + String.format("%,.2f", grossAmount));
        printTextWithBorder("");
        printTextWithStyle(Terminal.BOLD + "Estimated Net Amount: " + Terminal.DEFAULT + String.format("%,.2f", (grossAmount * 0.12)));
        printBorder();

        receiptWidth = 35;
        pressEnterDisplay();
    }
}