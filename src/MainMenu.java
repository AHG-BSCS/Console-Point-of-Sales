public class MainMenu {
    public void mainMenu() {
        while (true) {
            System.out.println("POSsy - A Point-Of-Sales Console System");
            System.out.println("By Al Hans Gaming\n");
            System.out.println("[1] New Transaction");
            System.out.println("[2] Inventory");
            System.out.println("[3] Report");
            System.out.println("[4] Exit");
            System.out.print("\nTransaction #: ");

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
