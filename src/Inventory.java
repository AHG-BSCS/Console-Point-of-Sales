import java.util.ArrayList;

public class Inventory {
    public void startInventory() {
        System.out.println("Inventory");
        System.out.println("[0] Back");

        selection();
    }

    public void selection() {
        try {
            int choice = Functions.getChoice();

            switch (choice) {
                case 0:
                    Functions.clearConsole();
                    break;
                default:
                    Functions.clearConsole();
                    System.out.println("Invalid selection.\n");
                    startInventory();
                    break;
            }
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            startInventory();
        }
    }

    public void displayItemsByCategory(ArrayList<Item> items) {
        int itemIndex = 1;
        for (Item item : items) {
            System.out.println("\n[" + item.getItemPk() + "] " + item.getProduct());
            System.out.println("Php." + item.getPrice() + " -> " + item.getStock() + "x");
        }
    }
}