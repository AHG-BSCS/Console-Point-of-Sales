import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewTransaction {
    Map<Integer, AddToCart> selectedProduct = new HashMap<>();
    ArrayList<Item> cart = new ArrayList<>();

    public void startNewTransac() {
        while (true) {
            listItems();
            System.out.println("\n[S] Search  [R] Receipt  [B] Back");
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
                    System.out.println(item.getProduct());
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
            if (quantity > 0 & quantity < 201)
                break;
            else
                System.out.println("\nInvalid Quantity!");
        }
        // Make sure quantity is valid before adding the item
        item.setQuantity(quantity);
        cart.add(item);
    }

    public void listItems() {
        if (cart.size() != 0) {
            System.out.println("=== ITEMS ===");

            for (Item item : cart) {
                System.out.println(item.getProduct());
                System.out.println(item.getQuantity() + " pc * " + 
                                    item.getPrice() + " = " + 
                                    (item.getPrice() * item.getQuantity()));
            }
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
            System.out.println("[7] Fans / Cooler");
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
        System.out.println("Receipt");
        
    }
}