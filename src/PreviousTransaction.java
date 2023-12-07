import java.util.Scanner;

public class PreviousTransaction {
    void StartPreviousTransaction() {
        System.out.println("Previous Transaction");
        System.out.println("0 - Back");

        Selection();
    }

    void Selection() {
        try {
            Scanner scn = new Scanner(System.in);
            int choice = scn.nextInt();

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
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            StartPreviousTransaction();
        }
    }
}