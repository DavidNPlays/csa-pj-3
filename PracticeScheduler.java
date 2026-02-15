import java.util.ArrayList;

/**
 * Manages practices (ArrayList + loops).
 */
public class PracticeScheduler {
    private final ArrayList<Practice> practices;
    private int nextId;

    public PracticeScheduler() {
        practices = new ArrayList<>();
        nextId = 1;
    }

    public Practice schedulePractice(String dateTime, String type, String notes) {
        String id = String.format("P%03d", nextId++);
        Practice p = new Practice(id, dateTime, type, notes);
        practices.add(p);
        return p;
    }

    public ArrayList<Practice> getPractices() {
        return practices;
    }

    public Practice findById(String id) {
        for (Practice p : practices) {
            if (p.getId().equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    public void listPractices() {
        if (practices.isEmpty()) {
            System.out.println("No practices scheduled yet.");
            return;
        }
        System.out.println("=== Practices ===");
        for (Practice p : practices) {
            System.out.println(" - " + p);
        }
    }
}
