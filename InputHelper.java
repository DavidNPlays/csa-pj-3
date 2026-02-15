import java.util.Scanner;

/**
 * Small helper to keep AthTrackApp modular and avoid crashes from bad input.
 */
public class InputHelper {

    public static String readNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Please enter a non-empty value.");
        }
    }

    public static int readInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String raw = sc.nextLine().trim();
            try {
                int val = Integer.parseInt(raw);
                if (val < min || val > max) {
                    System.out.println("Enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    public static TeamLevel readTeamLevel(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt + " (V/JV): ");
            String s = sc.nextLine().trim().toUpperCase();
            if (s.equals("V")) return TeamLevel.V;
            if (s.equals("JV")) return TeamLevel.JV;
            System.out.println("Please type V or JV.");
        }
    }

    public static boolean readYesNo(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String s = sc.nextLine().trim().toLowerCase();
            if (s.equals("y") || s.equals("yes")) return true;
            if (s.equals("n") || s.equals("no")) return false;
            System.out.println("Please answer y or n.");
        }
    }
}
