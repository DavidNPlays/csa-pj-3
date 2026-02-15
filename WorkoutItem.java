/**
 * A single item within a workout (e.g., "8x50 free @ :55").
 */
public class WorkoutItem {
    private final String description;
    private final String reps;

    public WorkoutItem(String description, String reps) {
        this.description = description;
        this.reps = reps;
    }

    public String getDescription() {
        return description;
    }

    public String getReps() {
        return reps;
    }

    @Override
    public String toString() {
        return description + " (" + reps + ")";
    }
}
