import java.util.ArrayList;

/**
 * Workout contains many WorkoutItems (composition).
 */
public class Workout {
    private final String title;
    private final int durationMin;
    private final int intensity1to10;
    private final ArrayList<WorkoutItem> items;

    public Workout(String title, int durationMin, int intensity1to10) {
        this.title = title;
        this.durationMin = durationMin;
        this.intensity1to10 = intensity1to10;
        this.items = new ArrayList<>();
    }

    public void addItem(WorkoutItem item) {
        items.add(item);
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<WorkoutItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append(" (").append(durationMin).append(" min, intensity ").append(intensity1to10).append(")\n");
        for (int i = 0; i < items.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(items.get(i)).append("\n");
        }
        return sb.toString();
    }
}
