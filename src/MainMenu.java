public class MainMenu implements Menu{
    @Override
    public void choices() {
        while (true) {
            System.out.println("\033[33mPOSsys - A Point of Sale Console System");
            System.out.println("By Al Hans Gaming\033[0m\n");
            System.out.println("\033[44m[1]\033[0m New Transaction");
            System.out.println("\033[44m[2]\033[0m Inventory");
            System.out.println("\033[44m[3]\033[0m Report");
            System.out.println("\033[44m[4]\033[0m Exit");
            System.out.print("\n\033[34mTransaction #: \033[0m");

            if (select()) break;
        }
    }

    @Override
    public boolean select() {
        int choice = Functions.getChoice();

        switch (choice) {
            case 1:
                Functions.clearConsole();
                NewTransaction startNewTransac = new NewTransaction();
                startNewTransac.choices();
                break;
            case 2:
                Functions.clearConsole();
                Inventory startInventory = new Inventory();
                startInventory.choices();
                break;
            case 3:
                Functions.clearConsole();
                Report startPreviousTransaction = new Report();
                startPreviousTransaction.choices();
                break;
            case 4:
                Functions.clearConsole();
                System.out.println("Transactions Ended\n");
                return true;
            default:
                Functions.clearConsole();
                System.out.println("Invalid Transaction!\n");
        }
        return false;
    }
}
