import java.util.ArrayList;
import java.util.Scanner;
//hashmap
public class AuthService {
    private ArrayList<String> pass;
    private ArrayList<String> athletes;
    private Scanner sc;

    public AuthService() {
        pass = new ArrayList<>();
        athletes = new ArrayList<>();
        pass.add("password");
        sc = new Scanner(System.in);
    }

    public boolean authenticate() {
        System.out.println("Enter password:");
        String password = sc.nextLine();

        if (pass.contains(password)) {
            System.out.println("Access granted!");
            return true;
        } else {
            System.out.println("Incorrect password");
            return false;
        }
    }
}