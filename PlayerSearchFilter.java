import java.util.ArrayList;
public class PlayerSearch {
    private ArrayList<Athlete> athletes;
    public PlayerSearch(ArrayList<Athlete> athletes) {
        this.athletes = athletes;
    }
    public ArrayList<Athlete> searchByName(String name) {
        ArrayList<Athlete> results = new ArrayList<>();

        for (Athlete a : athletes) {
            if (a.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(a);
            }
        }

        return results;
    }
    public ArrayList<Athlete> filterPlayers(TeamLevel level, int grade, int minRating) {
        ArrayList<Athlete> results = new ArrayList<>();

        for (Athlete a : athletes) {

            boolean levelMatch = (level == null || a.getLevel() == level);
            boolean gradeMatch = (grade == -1 || a.getGrade() == grade);
            boolean ratingMatch = (minRating == -1 || a.getRating() >= minRating);

            if (levelMatch && gradeMatch && ratingMatch) {
                results.add(a);
            }
        }

        return results;
    }
}
