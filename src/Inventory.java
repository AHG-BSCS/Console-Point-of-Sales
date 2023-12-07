import java.util.Scanner;

public class Inventory {
    public void startInventory() {
        System.out.println("Inventory");
        System.out.println("0 - Back");

        selection();
    }

    public void selection() {
        try {
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    Functions.clearConsole();
                    MainMenu back = new MainMenu();
                    back.mainMenu();
                    break;
                default:
                    Functions.clearConsole();
                    System.out.println("Invalid selection.\n");
                    startInventory();
                    break;
            }
            scanner.close();
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            startInventory();
        }
    }
}