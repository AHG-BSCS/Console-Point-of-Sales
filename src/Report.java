public class Report {
    public void startReport() {
        while (true) {
            System.out.println("[1] List of Transaction");
            System.out.println("[2] Statistics");
            System.out.println("[0] Back");

            if (selection())
                break;
        }
    }

    public boolean selection() {
        int choice = Functions.getChoice();

        switch (choice) {
            case 0:
                Functions.clearConsole();
                return true;
            case 1:
                Functions.clearConsole();
                listOfTransaction();
                break;
            case 2:
                Functions.clearConsole();
                statistics();
                break;
            default:
                Functions.clearConsole();
                System.out.println("Invalid Selection!\n");
        }
        return false;
    }

    public void listOfTransaction() {

    }

    public void statistics() {
        
    }
}