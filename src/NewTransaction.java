import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NewTransaction extends Inventory {
    private ArrayList<Item> cart = new ArrayList<>();

    @Override
    public void choices() {
        while (true) {
            listItems();
            System.out.println(Terminal.BOLD + Terminal.GREEN + "\n[S]" + Terminal.DEFAULT + " Search  " +
                            Terminal.BOLD + Terminal.GREEN + "[R]" + Terminal.DEFAULT + " Receipt  " +
                            Terminal.BOLD + Terminal.GREEN + "[C]" + Terminal.DEFAULT + " Clear  " +
                            Terminal.BOLD + Terminal.GREEN + "[B]" + Terminal.DEFAULT + " Back");
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
            System.out.println(Terminal.BOLD + Terminal.YELLOW + "================= I T E M S =================" + Terminal.DEFAULT);
            double totalAmount = 0;

            for (Item item : cart) {
                totalAmount += item.getPrice() * item.getQuantity();

                System.out.println(item.getProductName());
                System.out.println(item.getQuantity() + " pc * " + 
                                    String.format("%,.2f", item.getPrice()) + " = " + 
                                    String.format("%,.2f", (item.getPrice() * item.getQuantity())));
            }
            System.out.println("____________________________________________");
            System.out.println(Terminal.BOLD + Terminal.BLUE + "TOTAL: " + Terminal.DEFAULT + String.format("%,.2f", totalAmount));
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
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[0]" + Terminal.DEFAULT + " Back");
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
            System.out.println(Terminal.BOLD + Terminal.GREEN + "\n[0]" + Terminal.DEFAULT + " Back");
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

            if (cash > 0 & cash < 1_000_000 & cash > totalPrice)
                break;
            else
                System.out.println(Terminal.RED + "Invalid Amount!" + Terminal.DEFAULT);
        }

        if (cash > 0 & cash < 1_000_000 & cash > totalPrice) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            DatabaseHelper databaseHelper = new DatabaseHelper();
            int transactionId = 0;

            // Save transaction to database
            transactionId = databaseHelper.saveTransaction(cart, cash, totalPrice);

            Functions.clearConsole();
            displayReceiptHeader();
            listItems();

            System.out.print(Terminal.BOLD + "CASH: " + Terminal.DEFAULT + String.format("%,.2f", cash));
            System.out.println(Terminal.BOLD + "\nCHANGE: " + Terminal.DEFAULT + String.format("%,.2f", (cash - totalPrice)));

            System.out.println(Terminal.BOLD + "\nVatable Sales: " + Terminal.DEFAULT + String.format("%,.2f", totalPrice));
            System.out.println(Terminal.BOLD + "Vat Amount: " + Terminal.DEFAULT + String.format("%,.2f", (totalPrice * 0.12)));

            System.out.println(Terminal.BOLD + "\nPOS Transaction ID: " + transactionId);
            System.out.println(Terminal.BOLD + "Date: " + Terminal.DEFAULT + LocalDateTime.now().format(dateFormatter));
            System.out.println(Terminal.BOLD + "Time: " + Terminal.DEFAULT + LocalDateTime.now().format(timeFormatter));

            displayReceiptFooter();
        }

        System.out.println("_________________________________________");
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

    private void displayReceiptHeader() {
        System.out.println(Terminal.BOLD + Terminal.YELLOW + "=============== R E C E I P T ===============" + Terminal.DEFAULT);
        System.out.println(Terminal.BOLD + "POSsys By Al Hans Gaming");
        System.out.println("BS Computer Science - Section 2A");
        System.out.println("Laguna State Polytechnic University");
        System.out.println("San Gabriel, San Pablo City, Laguna\n" + Terminal.DEFAULT);
    }

    private void displayReceiptFooter() {
        System.out.println("\nThank you for your purchase.");
        System.out.println("If you are not satisfied with the");
        System.out.println("performance of our product, you can");
        System.out.println("return it along with its warranty.\n\n");
    }
}