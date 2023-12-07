import java.util.Scanner;

public class Home {
    void BackHome() {
        System.out.println("POSsy - A Point-Of-Sales Console System");
        System.out.println("By Al Hans Gaming");
        System.out.println("\nSelect Type of Transaction: ");
        System.out.println("1 - New Transaction");
        System.out.println("2 - Previous Transactions");
        System.out.println("3 - Inventory");
        System.out.println("4 - Exit");

        Selection();
    }

    void Selection() {
        try {
            Scanner scn = new Scanner(System.in);
            int choice = scn.nextInt();

            switch (choice) {
                case 1:
                    Functions.clearConsole();
                    NewTransaction startNewTransac = new NewTransaction();
                    startNewTransac.StartNewTransac();
                    break;
                case 2:
                    Functions.clearConsole();
                    PreviousTransaction startPreviousTransaction = new PreviousTransaction();
                    startPreviousTransaction.StartPreviousTransaction();
                    break;
                case 3:
                    Functions.clearConsole();
                    Inventory startInventory = new Inventory();
                    startInventory.StartInventory();
                    break;
                case 4:
                    Functions.clearConsole();
                    System.out.println("Thank you for checking us out.\n");
                    break;
                default:
                    Functions.clearConsole();
                    System.out.println("Invalid selection.\n");
                    BackHome();
                    break;
            }
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            BackHome();
        }
    }
}
