import java.util.ArrayList;
import java.util.Comparator;

/**
 * Organized athletes into balanced teams based on rating.
 * Highly and lowly rated athletes are evenly distributed across teams.
 */
public class TeamSorter {

    /**
     * Snake-draft distribution for balance:
     * Steps:
     * 1. Sort athletes by rating (highest to lowest)
     * 2. Distribute athletes in a "snake" pattern across teams
     * 3. Adding the top-rated athlete to team1, second to team2, etc.
     * 4. Move forward until the last team, then reverse direction and
     * continue until all athletes are assigned.
     */
    public ArrayList<ArrayList<Athlete>> sortIntoTeams(ArrayList<Athlete> roster, int numTeams) {
        // Make a copy that won't modify the original roster
        ArrayList<Athlete> copy = new ArrayList<>(roster);
        // sort by rating descending using a comparator 
        copy.sort(Comparator.comparingInt(Athlete::getRating).reversed());
        // Initialize empty teams
        ArrayList<ArrayList<Athlete>> teams = new ArrayList<>();
        for (int i = 0; i < numTeams; i++) teams.add(new ArrayList<>());
        // current index
        int idx = 0;
        // boolean to track direction
        boolean forward = true;
        // Assign athletes to teams
        for (Athlete a : copy) {
            // add athlete to current team
            teams.get(idx).add(a);

            if (forward) {
                idx++; // move to next team
                // if we reach the end, reverse direction
                if (idx == numTeams) {
                    idx = numTeams - 1;
                    forward = false;
                }

            } else {
                idx--; //move backward
                // if we reach the begining, reverse direction
                if (idx < 0) {
                    idx = 0;
                    forward = true;
                }
            }
        }
        return teams;
    }

    // Method to print teams and their total ratings 
    public void printTeams(ArrayList<ArrayList<Athlete>> teams) {
        for (int i = 0; i < teams.size(); i++) {
            ArrayList<Athlete> team = teams.get(i);
            int sum = 0;
            // Calculate total rating for the team 
            for (Athlete a : team) 
            {
                sum += a.getRating();
            }
            System.out.println("\nTeam " + (i + 1) + " (" + team.size() + " athletes, total rating=" + sum + "):");
            // Print each athlete in the team
            for (Athlete a : team) {
                System.out.println("  - " + a.getName() + " (rating " + a.getRating() + ")");
            }
        }
    }
}
