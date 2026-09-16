package console;

import java.util.Scanner;
/*
* This class has been created to make the deal with console easier
* include : methods to read (String, Double, hiddenPassword),
*           Clear method that cleans the console screen,
*           Pause method
* */
public class ConsoleAssistant {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readString(String prompt) {
        try {
            System.out.print(prompt);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return scanner.nextLine().trim();
    }

    public static double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readString(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again.");
            }

        }
    }

    public static int readIntInRange(String prompt, int min, int max) {

        while (true) {
            try {
                int choice = Integer.parseInt(readString(prompt));

                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("Invalid choice, please enter a number between " + min + " , " + max );
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid choice, please enter a number between " + min + " , " + max );
            }
        }
    }

    public static String readPassword(String prompt) {
        System.out.print(prompt);

        if (System.console() != null) {
            return new String(System.console().readPassword());
        }

        return scanner.nextLine();
    }

    public static void pause(String prompt) {
        System.out.println(prompt);
        scanner.nextLine();
    }
    public static void pause() {
        scanner.nextLine();
    }


    public static void clear() {
        String os = System.getProperty("os.name");
        try {

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // print 50 lines as a simulation of the "clear" function
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public static void closeScanner() {
        scanner.close();
    }
}
