import java.util.ArrayList;

public class Inventory {
    public void startInventory() {
        int classification = 0;

        while (true) {
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

            if (classification == 0) {
                Functions.clearConsole();
                return;
            }
            else if (classification > 0 & classification < 13) {
                Functions.clearConsole();
                break;
            }
            
            Functions.clearConsole();
            System.out.println("Invalid Classification\n");
            }

            // Store and display the retrieved items
            ArrayList<Item> items = new ArrayList<>();
            DatabaseHelper databaseHelper = new DatabaseHelper();

            items = databaseHelper.getItems(classification);
            displayItemsByCategory(items);

            System.out.print("Press Enter to go back");
            Functions.getChoiceInString();
            Functions.clearConsole();
        }
    }

    public void displayItemsByCategory(ArrayList<Item> items) {
        for (Item item : items) {
            System.out.println("[" + item.getItemPk() + "] " + item.getProduct());
            System.out.println("Php." + item.getPrice() + " -> " + item.getStock() + "x\n");
        }
    }
}