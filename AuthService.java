import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Simple authentication. Uses HashMap for credentials.
 * (Not secure - just a school project demo.)
 */
public class AuthService {
    private final Map<String, String> userToPass;
    private final Map<String, Coach> userToCoach;

    public AuthService() {
        userToPass = new HashMap<>();
        userToCoach = new HashMap<>();

        // Demo coach account(s)
        Coach demo = new Coach("C001", "Coach Demo", "LFA Varsity");
        userToPass.put("coach", "password");
        userToCoach.put("coach", demo);
    }

    public Coach login(Scanner sc) {
        System.out.println("=== Coach Login ===");
        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.print("Username: ");
            String username = sc.nextLine().trim();
            System.out.print("Password: ");
            String password = sc.nextLine().trim();

            if (userToPass.containsKey(username) && userToPass.get(username).equals(password)) {
                System.out.println("Access granted.");
                return userToCoach.get(username);
            }
            System.out.println("Incorrect username/password (attempt " + attempt + "/3).");
        }
        return null;
    }
}
