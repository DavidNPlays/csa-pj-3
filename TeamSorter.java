import java.util.ArrayList;
import java.util.Comparator;

/**
 * Sorts a roster into balanced teams by rating.
 * Uses ArrayList + loops + conditionals.
 */
public class TeamSorter {

    /**
     * Snake-draft distribution for balance:
     * highest -> team 1, next -> team 2 ... then reverse direction, etc.
     */
    public ArrayList<ArrayList<Athlete>> sortIntoTeams(ArrayList<Athlete> roster, int numTeams) {
        ArrayList<Athlete> copy = new ArrayList<>(roster);
        copy.sort(Comparator.comparingInt(Athlete::getRating).reversed());

        ArrayList<ArrayList<Athlete>> teams = new ArrayList<>();
        for (int i = 0; i < numTeams; i++) teams.add(new ArrayList<>());

        int idx = 0;
        boolean forward = true;

        for (Athlete a : copy) {
            teams.get(idx).add(a);

            if (forward) {
                idx++;
                if (idx == numTeams) {
                    idx = numTeams - 1;
                    forward = false;
                }
            } else {
                idx--;
                if (idx < 0) {
                    idx = 0;
                    forward = true;
                }
            }
        }
        return teams;
    }

    public void printTeams(ArrayList<ArrayList<Athlete>> teams) {
        for (int i = 0; i < teams.size(); i++) {
            ArrayList<Athlete> team = teams.get(i);
            int sum = 0;
            for (Athlete a : team) sum += a.getRating();

            System.out.println("\nTeam " + (i + 1) + " (" + team.size() + " athletes, total rating=" + sum + "):");
            for (Athlete a : team) {
                System.out.println("  - " + a.getName() + " (rating " + a.getRating() + ")");
            }
        }
    }
}
