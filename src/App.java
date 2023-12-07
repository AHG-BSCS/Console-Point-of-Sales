import java.util.Scanner;

class Intro {
    void Home() {
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
            System.out.print("Transaction: ");
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
                    System.out.println("Invalid type of transaction.\n");
                    Home();
                    break;
            }
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid type of transaction.\n");
            Home();
        }  
    }
}

class Functions {
    static void clearConsole() {
        final String os = System.getProperty("os.name");

        try {
            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}

class NewTransaction {
    void StartNewTransac() {
        System.out.println("New Transaction");
        System.out.println("4 - Back");

        Selection();
    }

    void Selection() {
        try {
            Scanner scn = new Scanner(System.in);
            int choice = scn.nextInt();

            switch (choice) {
                case 4:
                    Functions.clearConsole();
                    Intro back = new Intro();
                    back.Home();
                    break;
                default:
                    Functions.clearConsole();
                    System.out.println("Invalid selection.\n");
                    StartNewTransac();
                    break;
            }
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            StartNewTransac();
        }
    }
}

class PreviousTransaction {
    void StartPreviousTransaction() {
        System.out.println("Previous Transaction");
        System.out.println("4 - Back");

        Selection();
    }

    void Selection() {
        try {
            Scanner scn = new Scanner(System.in);
            int choice = scn.nextInt();

            switch (choice) {
                case 4:
                    Functions.clearConsole();
                    Intro back = new Intro();
                    back.Home();
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

class Inventory {
    void StartInventory() {
        System.out.println("Inventory");
        System.out.println("4 - Back");

        Selection();
    }

    void Selection() {
        try {
            Scanner scn = new Scanner(System.in);
            int choice = scn.nextInt();

            switch (choice) {
                case 4:
                    Functions.clearConsole();
                    Intro back = new Intro();
                    back.Home();
                    break;
                default:
                    Functions.clearConsole();
                    System.out.println("Invalid selection.\n");
                    StartInventory();
                    break;
            }
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            StartInventory();
        }
    }
}

public class App {
    public static void main(String[] args) {
        Intro intro = new Intro();

        Functions.clearConsole();
        intro.Home();
    }
}
