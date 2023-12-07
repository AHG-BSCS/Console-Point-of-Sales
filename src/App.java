import java.util.Scanner;

class Intro {
    void onStart() {
        Functions functions = new Functions();

        while(true){
            Scanner scn = new Scanner(System.in);
            System.out.println("POSsy - A Point-Of-Sales Console System");
            System.out.println("By Al Hans Gaming");
            System.out.println("\n Select Type of Transaction: ");
            System.out.println("1 - New Transaction");
            System.out.println("2 -  Review Previous Transactions");

            System.out.print("Transaction: ");
            int choice = scn.nextInt();

            switch (choice)
            {
                case 1:
                    functions.clearConsole();
                    functions.Function1();
                    break;
                case 2:
                    functions.clearConsole();
                    functions.Function2();
                    break;
                default:
                    System.out.println("Invalid type of transaction, please choose from the choices, wag na maghanap ng wala");
            }
        }
    }

    
}

class Functions{
    void Function1() {
        System.out.println("FUNCTION uno");
    }

    void Function2() {
        System.out.println("FUNCTION dos");
    }

    void clearConsole() {
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



public class App {
    public static void main(String[] args) {
        Intro intro = new Intro();
        Functions functions = new Functions();

        functions.clearConsole();
        intro.onStart();
    }
}
