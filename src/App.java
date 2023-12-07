import java.util.Scanner;

import javax.management.monitor.GaugeMonitorMBean;

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
        System.out.println("1 - Foods");
        System.out.println("2 - Drinks");
        System.out.println("3 - View Cart");
        System.out.println("4 - Generate Receipt");
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
                    Intro back = new Intro();
                    back.Home();
                    break;
                case 1:
                    Functions.clearConsole();
                    ShowFoods();
                    break;
                case 2:
                    Functions.clearConsole();
                    ShowDrinks();
                    break;
                case 3:
                    Functions.clearConsole();
                    ShowCart();
                    break;
                case 4:
                    Functions.clearConsole();
                    GenerateReceipt();
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

    void ShowCart() {
        System.out.println("CART");
        System.out.println("0 - Back");
        try {
            Scanner scn = new Scanner(System.in);
            int choice = scn.nextInt();

            switch (choice) {
                case 0:
                    Functions.clearConsole();
                    StartNewTransac();
                    break;
                default:
                    Functions.clearConsole();
                    System.out.println("Invalid selection.\n");
                    ShowCart();
                    break;
            }
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            ShowCart();
        }
    }

    void ShowFoods() {
        //Display available foods from database
        System.out.println("1 - Sample Food (Php 0.00)");
        System.out.println("0 - Back");

        SelectFood();
    }

    void ShowDrinks() {
        //Display available drinks from database
        System.out.println("1 - Sample Drink (Php 0.00)");
        System.out.println("0 - Back");

        SelectDrink();
    }

    void SelectFood() {
        try {
            Scanner scn = new Scanner(System.in);
            int choice = scn.nextInt();

            if (choice == 0) {
                Functions.clearConsole();
                NewTransaction back = new NewTransaction();
                back.StartNewTransac();
            }
            else if (choice == 1) { //If the id exist in database
                Functions.clearConsole();
                ProcessFood(choice);
            }
            else {
                Functions.clearConsole();
                System.out.println("Invalid selection.\n");
                ShowFoods();
            }
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            ShowFoods();
        }
    }

    void SelectDrink() {
        try {
            Scanner scn = new Scanner(System.in);
            int choice = scn.nextInt();

            if (choice == 0) {
                Functions.clearConsole();
                NewTransaction back = new NewTransaction();
                back.StartNewTransac();
            }
            else if (choice == 1) { //If the id exist in database
                Functions.clearConsole();
                ProcessDrink(choice);
            }
            else {
                Functions.clearConsole();
                System.out.println("Invalid selection.\n");
                ShowDrinks();
            }
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            ShowDrinks();
        }
    }

    void ProcessFood(int id) {
        System.out.println("0 - Back");
        Scanner scn = new Scanner(System.in);
        try {
            System.out.print("Enter Quantity of Items: ");
            int item_quantity = scn.nextInt();
            String item_name = "Sample Food"; //Fill values from database
            double item_price = 0;
            double vat;
            double price_gross;

            double totalPrice = item_price * item_quantity;
            vat = totalPrice*0.12;
            price_gross = totalPrice - vat;
            
            //Store data in a list or dictionary

            Functions.clearConsole();
            StartNewTransac();
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid quantity.\n");
            ProcessFood(id);
        }
    }

    void ProcessDrink(int id) {
        System.out.println("0 - Back");
        Scanner scn = new Scanner(System.in);
        try {
            System.out.print("Enter Quantity of Items: ");
            int item_quantity = scn.nextInt();
            String item_name = "Sample Drink"; //Fill values from database
            double item_price = 0;
            double vat;
            double price_gross;

            double totalPrice = item_price * item_quantity;
            vat = totalPrice*0.12;
            price_gross = totalPrice - vat;
            
            //Store data in a list or dictionary

            Functions.clearConsole();
            StartNewTransac();
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid quantity.\n");
            ProcessFood(id);
        }
    }

    void GenerateReceipt() {
        //Load items from the list or dictionary

        System.out.println("----YOUR RECEIPT----");
        System.out.println("Product Name: ");
        System.out.println("Price Per Unit: ");
        System.out.println("Quantity: \n");
        System.out.println("Gross Price: ");
        System.out.println("Value Added Tax (VAT 12%) ");
        System.out.println("TOTAL AMOUNT: ");

        System.out.println("\n0 - Cancel");
        System.out.println("1 - Confirm");
        try {
            Scanner scn = new Scanner(System.in);
            int choice = scn.nextInt();

            switch (choice) {
                case 0:
                    Functions.clearConsole();
                    StartNewTransac();
                    break;
                case 1:
                    //Store the receipt to database
                    Functions.clearConsole();
                    Intro back = new Intro();
                    back.Home();
                    break;
                default:
                    Functions.clearConsole();
                    System.out.println("Invalid selection.\n");
                    GenerateReceipt();
                    break;
            }
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            GenerateReceipt();
        }
    }
}

class PreviousTransaction {
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
