import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NewTransaction {
    Map<Integer, AddToCart> selectedProduct = new HashMap<>();

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
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    Functions.clearConsole();
                    Home back = new Home();
                    back.BackHome();
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
            scanner.close();
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
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

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
            scanner.close();
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
        System.out.println("2 - Sample Drink (Php 0.00)");
        System.out.println("0 - Back");

        SelectDrink();
    }

    void SelectFood() {
        try {
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

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
            scanner.close();
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            ShowFoods();
        }
    }

    void SelectDrink() {
        try {
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            if (choice == 0) {
                Functions.clearConsole();
                NewTransaction back = new NewTransaction();
                back.StartNewTransac();
            }
            else if (choice == 2) { //If the id exist in database
                Functions.clearConsole();
                ProcessDrink(choice);
            }
            else {
                Functions.clearConsole();
                System.out.println("Invalid selection.\n");
                ShowDrinks();
            }
            scanner.close();
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            ShowDrinks();
        }
    }

    void ProcessFood(int id) {
        System.out.println("0 - Back");
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter Quantity of Items: ");
            int quantity = scanner.nextInt();
            String name = "Sample Food"; //Fill values from database
            double price = 0;
            double vat;
            double price_gross;

            double totalPrice = price * quantity;
            vat = totalPrice*0.12;
            price_gross = totalPrice - vat;
            
            if (selectedProduct.containsKey(id)) {
                AddToCart existingProduct = selectedProduct.get(id);
                existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
            }
            else {
                AddToCart product = new AddToCart(id, name, price, quantity, totalPrice);
                selectedProduct.put(id, product);
            }

            Functions.clearConsole();
            StartNewTransac();
            scanner.close();
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid quantity.\n");
            ProcessFood(id);
        }
    }

    void ProcessDrink(int id) {
        System.out.println("0 - Back");
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter Quantity of Items: ");
            int quantity = scanner.nextInt();
            String name = "Sample Drink"; //Fill values from database
            double price = 0;
            double vat;
            double price_gross;

            double totalPrice = price * quantity;
            vat = totalPrice*0.12;
            price_gross = totalPrice - vat;
            
            if (selectedProduct.containsKey(id)) {
                AddToCart existingProduct = selectedProduct.get(id);
                existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
            }
            else {
                AddToCart product = new AddToCart(id, name, price, quantity, totalPrice);
                selectedProduct.put(id, product);
            }

            Functions.clearConsole();
            StartNewTransac();
            scanner.close();
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid quantity.\n");
            ProcessFood(id);
        }
    }

    void GenerateReceipt() {
        System.out.println("--------------------RECEIPT--------------------");
        for (Map.Entry<Integer, AddToCart> entry : selectedProduct.entrySet()) {
            int id = entry.getKey();
            AddToCart product = entry.getValue();

            double totalPrice = product.getPrice() * product.getQuantity();
            System.out.println(product.getName() + "   Php" + product.getPrice() + "   x" + product.getQuantity() + "   Php" + totalPrice);
        }
        System.out.println("-----------------------------------------------");

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
                    Home back = new Home();
                    back.BackHome();
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