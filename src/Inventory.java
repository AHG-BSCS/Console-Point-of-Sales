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
}