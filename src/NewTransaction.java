import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NewTransaction extends Inventory {
    private ArrayList<Item> cart = new ArrayList<>();
    public int receiptWidth = 35;

    @Override
    public void choices() {
        while (true) {
            listItems();
            System.out.println(Terminal.BOLD + Terminal.GREEN + "\n[S]" + Terminal.DEFAULT + " Search  " +
                            Terminal.BOLD + Terminal.GREEN + "[R]" + Terminal.DEFAULT + " Receipt  " +
                            Terminal.BOLD + Terminal.RED + "[C]" + Terminal.DEFAULT + " Clear  " +
                            Terminal.BOLD + Terminal.RED + "[B]" + Terminal.DEFAULT + " Back");
            System.out.print(Terminal.GREEN + "Item ID: " + Terminal.DEFAULT);

            if (select()) break;
        }
    }

    @Override
    public boolean select() {
        String choice = Functions.getChoiceInString();

        switch (choice) {
            case "S":
                Functions.clearConsole();
                searchSelected();
                return false;
            case "s":
                Functions.clearConsole();
                searchSelected();
                return false;
            case "R":
                Functions.clearConsole();
                receiptSelected();
                return false;
            case "r":
                Functions.clearConsole();
                receiptSelected();
                return false;
            case "C":
                Functions.clearConsole();
                cart.clear();
                return false;
            case "c":
                Functions.clearConsole();
                cart.clear();
                return false;
            case "B":
                Functions.clearConsole();
                return true;
            case "b":
                Functions.clearConsole();
                return true;
        }
        retrieveItem(choice);
        return false;
    }

    private void listItems() {
        if (cart.size() != 0) {
            for (Item item : cart) {
                if (item.getProductName().length() > receiptWidth)
                    receiptWidth = item.getProductName().length();
            }

            double totalAmount = 0;
            printTextWithEqual(" I T E M S ");

            for (Item item : cart) {
                
                totalAmount += item.getPrice() * item.getQuantity();

                printTextWithBorder(item.getProductName());
                printTextWithBorder(item.getQuantity() + " pc * " + 
                                    String.format("%,.2f", item.getPrice()) + " = " + 
                                    String.format("%,.2f", (item.getPrice() * item.getQuantity())));
                printTextWithBorder("");
            }
            printLine();
            printTextWithStyle(Terminal.BOLD + "TOTAL: " + Terminal.DEFAULT + String.format("%,.2f", totalAmount));
        }
    }

    private void retrieveItem(String choice) {
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper();
            Item item = new Item();
            item = databaseHelper.getItem(Integer.parseInt(choice));
            
            if (item.getProductName() != null) {
                addItemToCart(item);
                Functions.clearConsole();
            }
            else
                System.out.println(Terminal.RED + "Invalid Item ID!" + Terminal.DEFAULT);
        } catch (Exception e) {
            Functions.clearConsole();
            System.out.println(Terminal.RED + "Invalid Item ID!" + Terminal.DEFAULT);
        }
    }

    private void addItemToCart(Item item) {
        int quantity = 0;

        while (true) {
            System.out.println(item.getProductName());
            System.out.print(Terminal.GREEN + "Quantity: " + Terminal.DEFAULT);
            quantity = Functions.getChoice();

            if (quantity > 0) {
                if (quantity <= item.getStock()) {
                    Functions.clearConsole();
                    break;
                }
                else
                    System.out.println(Terminal.RED + "\nInsuficient Stock!" + Terminal.DEFAULT);
            }
            else
                System.out.println(Terminal.RED + "\nInvalid Quantity!" + Terminal.DEFAULT);
        }
        // Make sure quantity is valid before adding the item
        item.setQuantity(quantity);
        cart.add(item);
    }

    private void searchSelected() {
        int classification = 0;

        while (true) {
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[1]" + Terminal.DEFAULT + " Processor");
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[2]" + Terminal.DEFAULT + " Graphics Card");
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[3]" + Terminal.DEFAULT + " Memory");
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[4]" + Terminal.DEFAULT + " Storage");
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[5]" + Terminal.DEFAULT + " Motherboard");
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[6]" + Terminal.DEFAULT + " Power Supply");
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[7]" + Terminal.DEFAULT + " Fans/Cooler");
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[8]" + Terminal.DEFAULT + " Keyboard");
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[9]" + Terminal.DEFAULT + " Mice");
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[10]" + Terminal.DEFAULT + " Headset");
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[11]" + Terminal.DEFAULT + " Microphone");
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[12]" + Terminal.DEFAULT + " Accessory");
            System.out.println(Terminal.BOLD + Terminal.RED + "[0]" + Terminal.DEFAULT + " Back");
            System.out.print(Terminal.GREEN + "\nClassification #: " + Terminal.DEFAULT);

            classification = Functions.getChoice();

            if (classification == 0) {
                Functions.clearConsole();
                return;
            }
            else if (classification > 0 & classification < 13) {
                validateItem(displayItems(classification), classification);
                return;
            }
            else {
                Functions.clearConsole();
                System.out.println(Terminal.RED + "Invalid Classification!\n" + Terminal.DEFAULT);
            }
        }
    }

    private void validateItem(ArrayList<Item> items, int classification) {
        while (true) {
            System.out.println(Terminal.BOLD + Terminal.RED + "\n[0]" + Terminal.DEFAULT + " Back");
            System.out.print(Terminal.GREEN + "Item ID: " + Terminal.DEFAULT);
            
            Item seachedItem = new Item();
            int itemId = Functions.getChoice();

            if (itemId == 0) {
                Functions.clearConsole();
                break;
            }

            for (Item item : items) {
                if (item.getItemId() == itemId) {
                    seachedItem = item;
                    break;
                }
            }

            // If item was found in the search results, add it to cart
            if (seachedItem.getProductName() != null) {
                addItemToCart(seachedItem);
                return;
            }
            else {
                Functions.clearConsole();
                System.out.println(Terminal.RED + "Item does not exist in the list!" + Terminal.DEFAULT);
                displayItems(classification);
            }
        }
    }

    private void receiptSelected() {
        if (cart.size() == 0) {
            System.out.println(Terminal.RED + "No Item!" + Terminal.DEFAULT);
            return;
        }

        float cash = 0;
        float totalPrice = totalPrice();
        
        Functions.clearConsole();
        displayReceiptHeader();
        listItems();

        while (true) {
            System.out.println(Terminal.BOLD + Terminal.GREEN + "\n[0]" + Terminal.DEFAULT + " Cancel");
            System.out.print(Terminal.GREEN + "CASH: " + Terminal.DEFAULT);

            cash = Functions.getChoice();

            if (cash == 0) {
                Functions.clearConsole();
                return;
            }
            else if (cash > 0 && cash <= (totalPrice + 1000)) {
                if (cash >= totalPrice) {
                    DatabaseHelper databaseHelper = new DatabaseHelper();
                    int transactionId = databaseHelper.saveTransaction(cart, cash, totalPrice);;
                    displayReceipt(cash, totalPrice, transactionId);
                    receiptWidth = 35; // Reset receipt width
                    break;
                }
                else
                    System.out.println(Terminal.RED + "Insufficient Amount!" + Terminal.DEFAULT);
            }
            else if (cash > (totalPrice + 1000))
                    System.out.println(Terminal.RED + "Too Large Amount!" + Terminal.DEFAULT);
            else
                System.out.println(Terminal.RED + "Invalid Amount!" + Terminal.DEFAULT);
        }

        System.out.print(Terminal.BLUE + "Press Enter to proceed..." + Terminal.DEFAULT);
        Functions.getChoiceInString();
        Functions.clearConsole();
        cart.clear();
    }

    private float totalPrice() {
        float totalPrice = 0;

        for (Item item : cart) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    private void displayReceipt(float cash, float totalPrice, int transactionId) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

        Functions.clearConsole();
        displayReceiptHeader();
        listItems();

        printTextWithStyle(Terminal.BOLD + "CASH: " + Terminal.DEFAULT + String.format("%,.2f", cash));
        printTextWithStyle(Terminal.BOLD + "CHANGE: " + Terminal.DEFAULT + String.format("%,.2f", (cash - totalPrice)));

        printTextWithBorder("");
        printTextWithStyle(Terminal.BOLD + "Vatable Amount: " + Terminal.DEFAULT + String.format("%,.2f", totalPrice));
        printTextWithStyle(Terminal.BOLD + "VAT: " + Terminal.DEFAULT + String.format("%,.2f", (totalPrice * 0.12)) + " (12.00%)");

        printTextWithBorder("");
        printTextWithStyle(Terminal.BOLD + "POS Transaction ID: " + Terminal.DEFAULT + transactionId);
        printTextWithStyle(Terminal.BOLD + "Date: " + Terminal.DEFAULT + LocalDateTime.now().format(dateFormatter));
        printTextWithStyle(Terminal.BOLD + "Time: " + Terminal.DEFAULT + LocalDateTime.now().format(timeFormatter));

        displayReceiptFooter();
    }

    public void displayReceiptHeader() {
        printTextWithAsterisk(" R E C E I P T ");
        printTextWithStyle(Terminal.BOLD + "POSsys By Al Hans Gaming" + Terminal.DEFAULT);
        printTextWithStyle(Terminal.BOLD + "BS Computer Science - Section 2A" + Terminal.DEFAULT);
        printTextWithStyle(Terminal.BOLD + "Laguna State Polytechnic University" + Terminal.DEFAULT);
        printTextWithStyle(Terminal.BOLD + "San Gabriel, San Pablo City, Laguna" + Terminal.DEFAULT);
        printTextWithBorder("");
    }

    private void displayReceiptFooter() {
        printTextWithBorder("");
        printTextWithBorder("Thank you for your purchase.");
        printTextWithBorder("If you are not satisfied with the");
        printTextWithBorder("performance of our product, you can");
        printTextWithBorder("return it along with its warranty.");
        printBorder();
        System.out.println();
    }

    public void printBorder() {
        for (int i = 0; i < receiptWidth + 4; i++) {
            System.out.print(Terminal.BLUE + "*" + Terminal.DEFAULT);
        }
        System.out.println();
    }

    public void printLine() {
        System.out.print(Terminal.BLUE + "* " + Terminal.DEFAULT);
        for (int i = 0; i < receiptWidth; i++) {
            System.out.print(Terminal.BLUE + "_" + Terminal.DEFAULT);
        }
        System.out.println(Terminal.BLUE + " *" + Terminal.DEFAULT);
    }

    public void printTextWithEqual(String text) {
        int padding = (receiptWidth - text.length()) / 2;

        System.out.print(Terminal.BLUE + "* ");
        for (int i = 0; i < padding; i++) {
            System.out.print("=");
        }

        System.out.print(text);

        for (int i = 0; i < padding; i++) {
            System.out.print("=");
        }
        System.out.println(" *" + Terminal.DEFAULT);
    }

    public void printTextWithAsterisk(String text) {
        int padding = ((receiptWidth - text.length()) / 2) + 2;

        System.out.print(Terminal.BLUE);
        for (int i = 0; i < padding; i++) {
            System.out.print("*");
        }

        System.out.print(text);

        for (int i = 0; i < padding; i++) {
            System.out.print("*");
        }
        System.out.println(Terminal.DEFAULT);
    }

    public void printTextWithBorder(String text) {
        int padding = receiptWidth - text.length();

        System.out.print(Terminal.BLUE + "* " + Terminal.DEFAULT);
        System.out.print(text);

        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }
        System.out.println(Terminal.BLUE + " *" + Terminal.DEFAULT);
    }

    public void printTextWithStyle(String text) {
        int padding = receiptWidth - (text.length() - 8);

        System.out.print(Terminal.BLUE + "* " + Terminal.DEFAULT);
        System.out.print(text);

        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }
        System.out.println(Terminal.BLUE + " *" + Terminal.DEFAULT);
    }
}