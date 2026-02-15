//A single practice session
public class Practice {
    private final String id;
    private final String dateTime;
    private final String type;
    private String notes;
    private Workout workout; // optional

    public Practice(String id, String dateTime, String type, String notes) {
        this.id = id;
        this.dateTime = dateTime;
        this.type = type;
        this.notes = notes;
    }
    //getters and setters
    public String getId() {
        return id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getType() {
        return type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public Workout getWorkout() {
        return workout;
    }

    @Override
    public String toString() {
        return "Practice " + id + " [" + type + "] at " + dateTime +
                (notes == null || notes.isBlank() ? "" : " | notes: " + notes) +
                (workout == null ? "" : " | workout: " + workout.getTitle());
    }
}
