import java.util.ArrayList;

/**
 * Search + filter for athletes (ArrayList, loops, boolean logic).
 */
public class PlayerSearchFilter {

    public ArrayList<Athlete> searchByName(ArrayList<Athlete> roster, String namePart) {
        ArrayList<Athlete> results = new ArrayList<>();
        for (Athlete a : roster) {
            if (a.getName().toLowerCase().contains(namePart.toLowerCase())) results.add(a);
        }
        return results;
    }

    /**
     * Pass null or -1 for any filter you want to ignore.
     */
    public ArrayList<Athlete> filter(ArrayList<Athlete> roster, TeamLevel levelOrNull, int gradeOrMinus1, int minRatingOrMinus1) {
        ArrayList<Athlete> results = new ArrayList<>();
        for (Athlete a : roster) {
            boolean ok = true;

            if (levelOrNull != null && a.getLevel() != levelOrNull) ok = false;
            if (gradeOrMinus1 != -1 && a.getGrade() != gradeOrMinus1) ok = false;
            if (minRatingOrMinus1 != -1 && a.getRating() < minRatingOrMinus1) ok = false;

            if (ok) results.add(a);
        }
        return results;
    }
}
