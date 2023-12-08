import java.util.Scanner;

public class Functions {
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

    public static int getChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {}

        return choice;
    }
}