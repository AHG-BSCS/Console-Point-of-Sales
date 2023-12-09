import java.util.Scanner;

public class Functions {
    private static Scanner scanner;

    public static void clearConsole() {
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
        scanner = new Scanner(System.in);
        int choice = -1;
        
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {}
        return choice;
    }

    public static String getChoiceInString() {
        scanner = new Scanner(System.in);
        String choice = null;
        
        try {
            choice = scanner.nextLine();
        } catch (Exception e) {}
        return choice;
    }
}