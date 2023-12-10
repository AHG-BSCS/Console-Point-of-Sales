import java.util.ArrayList;

public class Inventory implements Menu {
    @Override
    public void choices() {
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
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[13]" + Terminal.DEFAULT + " Search");
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[0]" + Terminal.DEFAULT + " Back");
            System.out.print(Terminal.GREEN + "\nClassification #: " + Terminal.DEFAULT);

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
            pressEnterDisplay();
        }
        else {
            Functions.clearConsole();
            System.out.println(Terminal.RED + "Invalid Classification\n" + Terminal.DEFAULT);
        }
        return false;
    }

    private void searchItem() {
        while (true) {
            DatabaseHelper databaseHelper = new DatabaseHelper();
            Item item = new Item();

            System.out.println(Terminal.BOLD + Terminal.GREEN + "[0]" + Terminal.DEFAULT + " Back");
            System.out.print(Terminal.GREEN + "Item ID: " + Terminal.DEFAULT);
            int choice = Functions.getChoice();
            item = databaseHelper.getItem(choice);

            if (choice == 0) {
                Functions.clearConsole();
                break;
            }
            else if (item.getProductName() != null) {
                Functions.clearConsole();
                displayItem(item);
                pressEnterDisplay();
                break;
            }
            else {
                Functions.clearConsole();
                System.out.println(Terminal.RED + "Invalid Item ID!" + Terminal.DEFAULT);
            }
        }
    }

    public void displayItem(Item item) {
        System.out.println(Terminal.BOLD + Terminal.GREEN + "[" + item.getItemId() + "] " + Terminal.DEFAULT + item.getProductName());
        System.out.println(Terminal.BOLD + "Price: Php." + Terminal.DEFAULT + String.format("%,.2f", item.getPrice()));
        System.out.println(Terminal.BOLD + "Stock: " + Terminal.DEFAULT + item.getStock() + "pc");
        System.out.println("_________________________________________________");
    }

    public ArrayList<Item> displayItems(int classification) {
        // Retrieve and display all items for specific classification
        Functions.clearConsole();
        ArrayList<Item> items = new ArrayList<>();
        DatabaseHelper databaseHelper = new DatabaseHelper();
        items = databaseHelper.getItems(classification);

        for (Item item : items) {
            displayItem(item);
        }
        return items;
    }

    public void pressEnterDisplay() {
        System.out.print(Terminal.BLUE + "Press Enter to go back..." + Terminal.DEFAULT);
        Functions.getChoiceInString();
        Functions.clearConsole();
    }
}