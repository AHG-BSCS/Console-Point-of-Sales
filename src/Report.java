public class Report {
    public void startReport() {
        System.out.println("Report");
        System.out.println("[0] Back");

        Selection();
    }

    public void Selection() {
        try {
            int choice = Functions.getChoice();

            switch (choice) {
                case 0:
                    Functions.clearConsole();
                    new MainMenu().mainMenu();
                    break;
                default:
                    Functions.clearConsole();
                    System.out.println("Invalid selection.\n");
                    startReport();
                    break;
            }
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            startReport();
        }
    }
}