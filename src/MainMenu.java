import java.util.Scanner;

public class MainMenu {
    public void mainMenu() {
        System.out.println("POSsy - A Point-Of-Sales Console System");
        System.out.println("By Al Hans Gaming");
        System.out.println("\nSelect Type of Transaction: ");
        System.out.println("[1] New Transaction");
        System.out.println("[2] Inventory");
        System.out.println("[3] Report");
        System.out.println("[4] Exit");

        selection();
    }

    public void selection() {
        try {
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Functions.clearConsole();
                    NewTransaction startNewTransac = new NewTransaction();
                    startNewTransac.startNewTransac();
                    break;
                case 2:
                    Functions.clearConsole();
                    Inventory startInventory = new Inventory();
                    startInventory.startInventory();
                    break;
                case 3:
                    Functions.clearConsole();
                    Report startPreviousTransaction = new Report();
                    startPreviousTransaction.startReport();
                    break;
                case 4:
                    Functions.clearConsole();
                    System.out.println("Thank you for checking us out.\n");
                    break;
                default:
                    Functions.clearConsole();
                    System.out.println("Invalid selection.\n");
                    mainMenu();
                    break;
            }
            scanner.close();
        }
        catch (Exception e) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            mainMenu();
        }
    }
}
