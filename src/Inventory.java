import java.util.ArrayList;

public class Inventory implements Menu {
    @Override
    public void choices() {
        while (true) {
            System.out.println("=== I N V E N T O R Y ===\n");
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
            System.out.println("[13] Search");
            System.out.println("[0] Back");
            System.out.print("\nClassification #: ");

            if (select()) break;
        }
    }

    @Override
    public boolean select() {
        int classification = Functions.getChoice();

        if (classification == 0) {
            Functions.clearConsole();
            return true;
        }
        else if (classification == 13) {
            Functions.clearConsole();
            searchItem();
            // Dont return to keep looping
        }
        else if (classification > 0 & classification < 13) {
            displayItems(classification);

            System.out.print("Press Enter to go back...");
            Functions.getChoiceInString();
            Functions.clearConsole();
        }
        else {
            Functions.clearConsole();
            System.out.println("Invalid Classification\n");
        }
        return false;
    }

    private void searchItem() {
        while (true) {
            DatabaseHelper databaseHelper = new DatabaseHelper();
            Item item = new Item();

            System.out.println("[0] Back");
            System.out.print("Item ID: ");
            int choice = Functions.getChoice();
            item = databaseHelper.getItem(choice);

            if (choice == 0) {
                Functions.clearConsole();
                break;
            }
            else if (item.getProductName() != null) {
                Functions.clearConsole();
                displayItem(item);

                System.out.print("Press Enter to go back...");
                Functions.getChoiceInString();
                Functions.clearConsole();
                break;
            }
            else {
                Functions.clearConsole();
                System.out.println("Invalid Item ID!");
            }
        }
    }

    public void displayItem(Item item) {
        System.out.println("[" + item.getItemId() + "] " + item.getProductName());
        System.out.println("Price: Php." + String.format("%,.2f", item.getPrice()));
        System.out.println("Stock: " + item.getStock() + "pc");
        System.out.println("_________________________________________________");
    }

    public ArrayList<Item> displayItems(int classification) {
        // Retrieve and display all items for specific classification
        Functions.clearConsole();
        ArrayList<Item> items = new ArrayList<>();
        DatabaseHelper databaseHelper = new DatabaseHelper();
        items = databaseHelper.getItems(classification);

        for (Item item : items) {
            System.out.println("[" + item.getItemId() + "] " + item.getProductName());
            System.out.println("Price: Php." + String.format("%,.2f", item.getPrice()));
            System.out.println("Stock: " + item.getStock() + "pc");
            System.out.println("_________________________________________________");
        }
        return items;        
    }
}