import java.util.ArrayList;

/**
 * Manages the roster (dynamic data management via ArrayList).
 */
public class AthleteRoster {
    private final ArrayList<Athlete> athletes;

    public AthleteRoster() {
        athletes = new ArrayList<>();
    }

    public void addAthlete(Athlete a) {
        athletes.add(a);
    }

    public ArrayList<Athlete> getAll() {
        return athletes;
    }

    public Athlete findById(String id) {
        for (Athlete a : athletes) {
            if (a.getId().equalsIgnoreCase(id)) return a;
        }
        return null;
    }

    public Athlete findByNameExact(String name) {
        for (Athlete a : athletes) {
            if (a.getName().equalsIgnoreCase(name)) return a;
        }
        return null;
    }

    public boolean removeById(String id) {
        Athlete target = findById(id);
        if (target == null) return false;
        return athletes.remove(target);
    }

    public void printRoster() {
        if (athletes.isEmpty()) {
            System.out.println("Roster is empty.");
            return;
        }
        System.out.println("Roster (" + athletes.size() + " athletes):");
        for (int i = 0; i < athletes.size(); i++) {
            System.out.println((i + 1) + ". " + athletes.get(i));
        }
    }
}
