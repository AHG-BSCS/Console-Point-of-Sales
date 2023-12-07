import java.util.Scanner;

public class Report {
    public void startReport() {
        System.out.println("Previous Transaction");
        System.out.println("0 - Back");

        Selection();
    }

    public void Selection() {
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
                    startReport();
                    break;
            }
            scanner.close();
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            startReport();
        }
    }
}