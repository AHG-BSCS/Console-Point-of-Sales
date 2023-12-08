public class MainMenu {
    public void mainMenu() {
        while (true) {
            System.out.println("\033[33mPOSsys - A Point of Sale Console System");
            System.out.println("By Al Hans Gaming\033[0m\n");
            System.out.println("\033[44m[1]\033[0m New Transaction");
            System.out.println("\033[44m[2]\033[0m Inventory");
            System.out.println("\033[44m[3]\033[0m Report");
            System.out.println("\033[44m[4]\033[0m Exit");
            System.out.print("\n\033[34mTransaction #: \033[0m");

            if (selection()) break;
        }
    }

    public boolean selection() {
        int choice = Functions.getChoice();

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
                System.out.println("Transactions Ended\n");
                return true;
            default:
                Functions.clearConsole();
                System.out.println("Invalid Transaction.\n");
        }
        return false;
    }
}
