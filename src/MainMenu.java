public class MainMenu implements Menu {
    @Override
    public void choices() {
        while (true) {
            System.out.println(Terminal.BOLD + Terminal.YELLOW + "POSsys - A Point of Sale Console System");
            System.out.println("By Al Hans Gaming\n" + Terminal.DEFAULT);
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[1]" + Terminal.DEFAULT + " New Transaction");
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[2]" + Terminal.DEFAULT + " Inventory");
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[3]" + Terminal.DEFAULT + " Report");
            System.out.println(Terminal.BOLD + Terminal.GREEN + "[4]" + Terminal.DEFAULT + " Exit");
            System.out.print(Terminal.GREEN + "\nTransaction #: " + Terminal.DEFAULT);

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
                System.out.println(Terminal.RED + "Transactions Ended" + Terminal.DEFAULT);
                return true;
            default:
                Functions.clearConsole();
                System.out.println(Terminal.RED + "Invalid Transaction!\n" + Terminal.DEFAULT);
        }
        return false;
    }
}
