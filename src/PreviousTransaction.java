import java.util.Scanner;

public class PreviousTransaction {
    void StartPreviousTransaction() {
        System.out.println("Previous Transaction");
        System.out.println("0 - Back");

        Selection();
    }

    void Selection() {
        try {
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    Functions.clearConsole();
                    Home back = new Home();
                    back.BackHome();
                    break;
                default:
                    Functions.clearConsole();
                    System.out.println("Invalid selection.\n");
                    StartPreviousTransaction();
                    break;
            }
            scanner.close();
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            StartPreviousTransaction();
        }
    }
}