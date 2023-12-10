import java.util.ArrayList;

public class Inventory implements Menu {
    @Override
    public void choices() {
        while (true) {
            printCategories();
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
            Functions.clearConsole();
            displayItems(classification);
            pressEnterDisplay();
        }
        else {
            Functions.clearConsole();
            System.out.println(Terminal.RED + "Invalid Classification\n" + Terminal.DEFAULT);
        }
        return false;
    }

    public void printCategories() {
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
        System.out.println(Terminal.BOLD + Terminal.RED + "[0]" + Terminal.DEFAULT + " Back");
        System.out.print(Terminal.GREEN + "\nClassification #: " + Terminal.DEFAULT);
    }

    private void searchItem() {
        while (true) {
            DatabaseHelper databaseHelper = new DatabaseHelper();
            ArrayList<Item> items = new ArrayList<>();
            Item item = new Item();

            System.out.println(Terminal.BOLD + Terminal.RED + "[B]" + Terminal.DEFAULT + " Back");
            System.out.print(Terminal.GREEN + "Item Keyword/ID: " + Terminal.DEFAULT);
            String choice = Functions.getChoiceInString();

            switch (choice) {
                case "b":
                    Functions.clearConsole();
                    return;
                case "B":
                    Functions.clearConsole();
                    return;
            }

            try {
                // If choice can be parse to int
                item = databaseHelper.getItem(Integer.parseInt(choice));
                
                if (item.getProductName() != null) {
                    Functions.clearConsole();
                }
                else {
                    Functions.clearConsole();
                    System.out.println(Terminal.RED + "Item not Found!" + Terminal.DEFAULT);
                    break;
                }
            } catch (Exception e) {}
            
            if (item.getProductName() != null) {
                Functions.clearConsole();
                displayItem(item);
                pressEnterDisplay();
                break;
            }
            else {
                Functions.clearConsole();
                items = databaseHelper.getItems(choice);

                if (items.size() != 0) {
                    Functions.clearConsole();
                    for (Item matchedItem : items) {
                        displayItem(matchedItem);
                    }
                    items.clear();
                    pressEnterDisplay();
                }
                else 
                    System.out.println(Terminal.RED + "No Result!" + Terminal.DEFAULT);
            }
        }
    }

    public void displayItem(Item item) {
        System.out.println(Terminal.BOLD + Terminal.GREEN + "[" + item.getItemId() + "] " + Terminal.DEFAULT + item.getProductName());
        System.out.println(Terminal.BOLD + "Price: Php." + Terminal.DEFAULT + String.format("%,.2f", item.getPrice()));
        System.out.println(Terminal.BOLD + "Stock: " + Terminal.DEFAULT + item.getStock() + "pc");
    }

    public ArrayList<Item> displayItems(int classification) {
        // Retrieve and display all items for specific classification
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