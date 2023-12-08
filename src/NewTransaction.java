import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewTransaction {
    Map<Integer, AddToCart> selectedProduct = new HashMap<>();
    ArrayList<Item> cart = new ArrayList<>();

    public void startNewTransac() {
        while (true) {
            listItems();
            System.out.println("[S] Search  [R] Receipt  [C] Clear  [B] Back");
            System.out.print("Item ID: ");

            if (selection()) break;
        }
    }

    public boolean selection() {
        String choice = Functions.getChoiceInString();
        int itemId = 0;

        switch (choice) {
            case "S":
                Functions.clearConsole();
                search();
                return false;
            case "s":
                Functions.clearConsole();
                search();
                return false;
            case "R":
                Functions.clearConsole();
                receipt();
                return false;
            case "r":
                Functions.clearConsole();
                receipt();
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
        
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper();
            Item item = new Item();
            item = databaseHelper.getItem(Integer.parseInt(choice));
            
            if (item.getProduct() == null)
                System.out.println("Invalid Item ID");
            else {
                int quantity = 0;
                while (true) {
                    System.out.println(item.getProduct() + " -> Php." + item.getPrice());
                    System.out.print("Quantity: ");
                    quantity = Functions.getChoice();

                    // Limit the item quantity to 1 - 200
                    if (quantity > 0 & quantity < 201)
                        break;
                    else
                        System.out.println("\nInvalid Quantity!");
                }
                // Make sure quantity is valid before adding the item
                item.setQuantity(quantity);
                cart.add(item);
                Functions.clearConsole();
            }
        } catch (Exception e) {
            Functions.clearConsole();
            System.out.println("Invalid Item ID");
        }
        return false;
    }

    public void addItemToCart(Item item) {
        int quantity = 0;
        while (true) {
            System.out.println(item.getProduct());
            System.out.print("Quantity: ");
            quantity = Functions.getChoice();

            // Limit the item quantity to 1 - 200
            if (quantity > 0 & quantity < 201) {
                Functions.clearConsole();
                break;
            }
            else
                System.out.println("\nInvalid Quantity!");
        }
        // Make sure quantity is valid before adding the item
        item.setQuantity(quantity);
        cart.add(item);
    }

    public void listItems() {
        if (cart.size() != 0) {
            System.out.println("============ I T E M S ============");

            for (Item item : cart) {
                System.out.println(item.getProduct());
                System.out.println(item.getQuantity() + " pc * " + 
                                    item.getPrice() + " = " + 
                                    (item.getPrice() * item.getQuantity()));
            }
            System.out.println();
        }
    }

    public void search() {
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
            System.out.print("\nClassification: ");

            classification = Functions.getChoice();

            if (classification == 0) 
                return;
            else if (classification > 0 & classification < 13) {
                Functions.clearConsole();
                break;
            }
            
            Functions.clearConsole();
            System.out.println("Invalid Classification\n");
        }

        ArrayList<Item> items = new ArrayList<>();
        DatabaseHelper databaseHelper = new DatabaseHelper();
        Inventory inventory = new Inventory();

        items = databaseHelper.getItems(classification);
        inventory.displayItemsByCategory(items);

        // Check if the selected item exist
        while (true) {
            System.out.println("[0] Cancel");
            System.out.print("Item ID: ");
            
            Item seachedItem = new Item();
            int itemId = Functions.getChoice();
            boolean itemExist = false;

            if (itemId == 0) break;

            for (Item item : items) {
                if (item.getItemPk() == itemId) {
                    itemExist = true;
                    seachedItem = item;
                    break;
                }
            }

            if (itemExist) {
                addItemToCart(seachedItem);
                break;
            }
            else {
                Functions.clearConsole();
                System.out.println("Item does not exist");
                inventory.displayItemsByCategory(items);
            }
        }
    }

    public void receipt() {
        if (cart.size() == 0) {
            System.out.println("No Item!");
            return;
        }

        double cash = 0;
        double totalPrice = totalPrice();

        Functions.clearConsole();
        System.out.println("========== R E C E I P T ==========");
        System.out.println("POSsys By Al Hans Gaming");
        System.out.println("Laguna State Polytechnic University");
        System.out.println("San Gabriel, San Pablo City, Laguna\n");
        listItems();

        System.out.println("TOTAL : " + String.format("%,.2f", totalPrice));

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

            Functions.clearConsole();
            System.out.println("========== R E C E I P T ==========");
            System.out.println("POSsys By Al Hans Gaming");
            System.out.println("Laguna State Polytechnic University");
            System.out.println("San Gabriel, San Pablo City, Laguna\n");
            
            listItems();

            System.out.println("TOTAL : " + String.format("%,.2f", totalPrice));
            System.out.print("CASH: " + String.format("%,.2f", cash));
            System.out.println("\nCHANGE: " + String.format("%,.2f", (cash - totalPrice)));

            System.out.println("\nVatable Sales: " + String.format("%,.2f", totalPrice));
            System.out.println("Vat Amount: " + String.format("%,.2f", (totalPrice * 0.14)));

            System.out.println("\nPOS Transaction ID: 0");
            System.out.println("Date: " + LocalDateTime.now().format(dateFormatter));
            System.out.println("Time: " + LocalDateTime.now().format(timeFormatter));
            
            System.out.println("\nThank you for your purchase.");
            System.out.println("If you are not satisfied with the");
            System.out.println("performance of our product, you can");
            System.out.println("return it along with its warranty.");
        }

        System.out.println("\n\n=================================");
        System.out.print("Press Enter to proceed");
        Functions.getChoiceInString();
        Functions.clearConsole();
        cart.clear();
    }

    public double totalPrice() {
        double totalPrice = 0;

        for (Item item : cart) {
            totalPrice += item.getPrice() * item.getQuantity();
        }

        return totalPrice;
    }
}