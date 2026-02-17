import java.util.ArrayList;

/**
 * Allows coach to adjust and change inputs freely through manual swapping of athletes.
 */
public class ManualAdjustmentService {
    // Swaps two athletes by their names
    public boolean swapAthletesByName(ArrayList<ArrayList<Athlete>> teams, String name1, String name2) {
        Athlete a1 = null, a2 = null;
        int t1 = -1, t2 = -1;
        // Loop through each teams
        for (int i = 0; i < teams.size(); i++) {
            // Loop through each athlete
            for (Athlete a : teams.get(i)) {
                if (a.getName().equalsIgnoreCase(name1)) 
                { 
                    a1 = a; 
                    t1 = i; 
                }

                if (a.getName().equalsIgnoreCase(name2)) 
                { 
                    a2 = a; 
                    t2 = i; 
                }
            }
        }
        
        if (a1 == null || a2 == null) 
        {
            return false;
        }
        
        // Remove the athletes from their current teams
        teams.get(t1).remove(a1);
        teams.get(t2).remove(a2);
        // Add the athletes to their new teams
        teams.get(t1).add(a2);
        teams.get(t2).add(a1);
        return true;
    }
}
