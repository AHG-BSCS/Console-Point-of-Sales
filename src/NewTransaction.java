import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NewTransaction extends Inventory{
    ArrayList<Item> cart = new ArrayList<>();

    @Override
    public void choices() {
        while (true) {
            listItems();
            System.out.println("\n[S] Search  [R] Receipt  [C] Clear  [B] Back");
            System.out.print("Item ID: ");

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
            System.out.println("================= I T E M S =================");
            double totalAmount = 0;

            for (Item item : cart) {
                totalAmount += item.getPrice() * item.getQuantity();

                System.out.println(item.getProduct());
                System.out.println(item.getQuantity() + " pc * " + 
                                    String.format("%,.2f", item.getPrice()) + " = " + 
                                    String.format("%,.2f", (item.getPrice() * item.getQuantity())));
            }
            System.out.println("____________________________________________");
            System.out.println("TOTAL: " + String.format("%,.2f", totalAmount));
        }
    }

    private void retrieveItem(String choice) {
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper();
            Item item = new Item();
            item = databaseHelper.getItem(Integer.parseInt(choice));
            
            if (item.getProduct() == null)
                System.out.println("Invalid Item ID!");
            else {
                int quantity = 0;
                while (true) {
                    System.out.println(item.getProduct() + " -> Php." + item.getPrice());
                    System.out.print("Quantity: ");
                    quantity = Functions.getChoice();

                    if (quantity > 0 & quantity <= item.getStock())
                        break;
                    else
                        System.out.println("\nOut of stock!");
                }
                // Make sure quantity is valid before adding the item
                item.setQuantity(quantity);
                cart.add(item);
                Functions.clearConsole();
            }
        } catch (Exception e) {
            Functions.clearConsole();
            System.out.println("Invalid Item ID!");
        }
    }

    private void addItemToCart(Item item) {
        int quantity = 0;

        while (true) {
            System.out.println(item.getProduct());
            System.out.print("Quantity: ");
            quantity = Functions.getChoice();

            // Limit the item quantity to 1 - 200
            if (quantity > 0 & quantity <= item.getStock()) {
                Functions.clearConsole();
                break;
            }
            else
                System.out.println("\nOut of stock!");
        }
        // Make sure quantity is valid before adding the item
        item.setQuantity(quantity);
        cart.add(item);
    }

    

    private void searchSelected() {
        int classification = 0;

        while (true) {
            System.out.println("[1] Processor");
            System.out.println("[2] Graphics Card");
            System.out.println("[3] Memory");
            System.out.println("[4] Storage");
            System.out.println("[5] Motherboard");
            System.out.println("[6] Power Supply");
            System.out.println("[7] Fans/Cooler");
            System.out.println("[8] Keyboard");
            System.out.println("[9] Mice");
            System.out.println("[10] Headset");
            System.out.println("[11] Microphone");
            System.out.println("[12] Accessory");
            System.out.println("[0] Back");
            System.out.print("\nClassification #: ");

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
                System.out.println("Invalid Classification!\n");
            }
        }
    }

    private void validateItem(ArrayList<Item> items, int classification) {
        while (true) {
            System.out.println("\n[0] Back");
            System.out.print("Item ID: ");
            
            Item seachedItem = new Item();
            int itemId = Functions.getChoice();

            if (itemId == 0) {
                Functions.clearConsole();
                break;
            }

            for (Item item : items) {
                if (item.getItemPk() == itemId) {
                    seachedItem = item;
                    break;
                }
            }

            // If item was found in the search results, add it to cart
            if (seachedItem.getProduct() != null) {
                addItemToCart(seachedItem);
                return;
            }
            else {
                Functions.clearConsole();
                System.out.println("Item does not exist in the list!");
                displayItems(classification);
            }
        }
    }

    private void receiptSelected() {
        if (cart.size() == 0) {
            System.out.println("No Item!");
            return;
        }

        float cash = 0;
        float totalPrice = totalPrice();

        Functions.clearConsole();
        System.out.println("=============== R E C E I P T ===============");
        System.out.println("POSsys By Al Hans Gaming");
        System.out.println("Laguna State Polytechnic University");
        System.out.println("San Gabriel, San Pablo City, Laguna\n");
        listItems();

        while (true) {
            System.out.println("\n[0] Cancel");
            System.out.print("CASH: ");

            cash = Functions.getChoice();

            if (cash == 0) {
                Functions.clearConsole();
                return;
            }

            if (cash > 0 & cash < 1_000_000 & cash > totalPrice)
                break;
            else
                System.out.println("Invalid Amount!");
        }

        if (cash > 0 & cash < 1_000_000 & cash > totalPrice) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            DatabaseHelper databaseHelper = new DatabaseHelper();
            int transactionId = 0;

            // Save transaction to database
            transactionId = databaseHelper.saveTransaction(cart, cash, totalPrice);

            Functions.clearConsole();
            System.out.println("=============== R E C E I P T ===============");
            System.out.println("POSsys By Al Hans Gaming");
            System.out.println("BS Computer Science - Section 2A");
            System.out.println("Laguna State Polytechnic University");
            System.out.println("San Gabriel, San Pablo City, Laguna\n");
            
            listItems();

            System.out.print("CASH: " + String.format("%,.2f", cash));
            System.out.println("\nCHANGE: " + String.format("%,.2f", (cash - totalPrice)));

            System.out.println("\nVatable Sales: " + String.format("%,.2f", totalPrice));
            System.out.println("Vat Amount: " + String.format("%,.2f", (totalPrice * 0.12)));

            System.out.println("\nPOS Transaction ID: " + transactionId);
            System.out.println("Date: " + LocalDateTime.now().format(dateFormatter));
            System.out.println("Time: " + LocalDateTime.now().format(timeFormatter));
            
            System.out.println("\nThank you for your purchase.");
            System.out.println("If you are not satisfied with the");
            System.out.println("performance of our product, you can");
            System.out.println("return it along with its warranty.");
        }

        System.out.println("\n\n_________________________________________");
        System.out.print("Press Enter to proceed...");
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
}