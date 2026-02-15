/**
 * Athlete is also a User (inheritance). Coaches manage Athletes in a roster.
 */
public class Athlete extends User {
    private int grade;
    private String sport;
    private TeamLevel level;  // V or JV
    private int rating;       // 1..100
    private String notes;

    public Athlete(String id, String name, int grade, String sport, TeamLevel level, int rating, String notes) {
        super(id, name);
        this.grade = grade;
        this.sport = sport;
        this.level = level;
        this.rating = rating;
        this.notes = notes;
    }

    public int getGrade() {
        return grade;
    }

    public String getSport() {
        return sport;
    }

    public TeamLevel getLevel() {
        return level;
    }

    public int getRating() {
        return rating;
    }

    public String getNotes() {
        return notes;
    }

    public void updateRating(int delta) {
        rating += delta;
        if (rating < 0) rating = 0;
    }

    public void setLevel(TeamLevel level) {
        this.level = level;
    }

    public void appendNotes(String extra) {
        if (extra == null || extra.isBlank()) return;
        if (notes == null) notes = "";
        if (!notes.isBlank()) notes += " | ";
        notes += extra.trim();
    }

    @Override
    public String getRole() {
        return "ATHLETE";
    }

    @Override
    public String toString() {
        return name + " (ID:" + id + ", grade " + grade + ", " + sport + ", " + level +
                ", rating " + rating + (notes == null || notes.isBlank() ? "" : ", notes: " + notes) + ")";
    }
}
