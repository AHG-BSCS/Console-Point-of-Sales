import java.util.HashMap;
import java.util.Map;

public class NewTransaction {
    Map<Integer, AddToCart> selectedProduct = new HashMap<>();

    void startNewTransac() {
        System.out.println("[S] Search");
        System.out.println("[R] Receipt");
        System.out.println("[0] Back");
        System.out.print("Item ID: ");

        selection();
    }

    void selection() {
        try {
            int choice = Functions.getChoice();

            switch (choice) {
                case 0:
                    Functions.clearConsole();
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
                    showCart();
                    break;
                case 4:
                    Functions.clearConsole();
                    GenerateReceipt();
                    break;
                default:
                    Functions.clearConsole();
                    System.out.println("Invalid selection.\n");
                    startNewTransac();
                    break;
            }
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            startNewTransac();
        }
    }

    public void showCart() {
        System.out.println("CART");
        System.out.println("0 - Back");
        try {
            int choice = Functions.getChoice();

            switch (choice) {
                case 0:
                    Functions.clearConsole();
                    startNewTransac();
                    break;
                default:
                    Functions.clearConsole();
                    System.out.println("Invalid selection.\n");
                    showCart();
                    break;
            }
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            showCart();
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
            int choice = Functions.getChoice();

            if (choice == 0) {
                Functions.clearConsole();
                NewTransaction back = new NewTransaction();
                back.startNewTransac();
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
            int choice = Functions.getChoice();

            if (choice == 0) {
                Functions.clearConsole();
                NewTransaction back = new NewTransaction();
                back.startNewTransac();
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
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid selection.\n");
            ShowDrinks();
        }
    }

    void ProcessFood(int id) {
        System.out.println("0 - Back");
        try {
            System.out.print("Enter Quantity of Items: ");
            int quantity = Functions.getChoice();
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
            startNewTransac();
        }
        catch (Exception ex) {
            Functions.clearConsole();
            System.out.println("Invalid quantity.\n");
            ProcessFood(id);
        }
    }

    void ProcessDrink(int id) {
        System.out.println("0 - Back");
        try {
            System.out.print("Enter Quantity of Items: ");
            int quantity = Functions.getChoice();
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
            startNewTransac();
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
            int choice = Functions.getChoice();

            switch (choice) {
                case 0:
                    Functions.clearConsole();
                    startNewTransac();
                    break;
                case 1:
                    //Store the receipt to database
                    Functions.clearConsole();
                    new MainMenu().mainMenu();
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