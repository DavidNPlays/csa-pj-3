import java.util.ArrayList;

//Tracks goals and personal bests for athletes.
public class PersonalBestMonitor {
    private final ArrayList<Goal> goals;
    private final ArrayList<PerformanceEntry> entries;

    public PersonalBestMonitor() { //makes two arraylists, one tracking goals, the other tracking progress
        goals = new ArrayList<>();
        entries = new ArrayList<>(); //tracks progress through entries over time
    }
    //add a goal
    public void addGoal(Goal goal) {
        goals.add(goal);
    }
    //show all goals
    public ArrayList<Goal> getGoals() {
        return goals;
    }
    //add an entry
    public void logEntry(PerformanceEntry entry) {
        entries.add(entry);
    }
    //show all entries
    public ArrayList<PerformanceEntry> getEntries() {
        return entries;
    }
    //find a goal based on athelete's name and type of goal
    public Goal findGoal(String athleteName, String metricType) {
        for (Goal g : goals) {
            if (g.getAthleteName().equalsIgnoreCase(athleteName) &&
                    g.getMetricType().equalsIgnoreCase(metricType)) {
                return g;
            }
        }
        return null;
    }

    //Checks if the given entry is a new personal best compared to previous entries for the same athlete+metricType. 
    // Uses the goal's "higherIsBetter" when available; if no goal exists, assume higher is better.
    public boolean isNewPersonalBest(PerformanceEntry entry) {
        boolean higherIsBetter = true;
        Goal g = findGoal(entry.getAthleteName(), entry.getMetricType()); //find the goal in the arraylist
        if (g != null) higherIsBetter = g.isHigherIsBetter(); //set higherIsBetter boolean to that of the goal

        Double best = null;
        for (PerformanceEntry e : entries) {
            if (e == entry) continue;
            if (e.getAthleteName().equalsIgnoreCase(entry.getAthleteName()) //if name and metric type are equal
                    && e.getMetricType().equalsIgnoreCase(entry.getMetricType())) {
                if (best == null) best = e.getValue();
                else {
                    if (higherIsBetter) best = Math.max(best, e.getValue());
                    else best = Math.min(best, e.getValue());
                }
            }
        }

        // No prior entries, treat as PB
        if (best == null) return true;

        if (higherIsBetter) return entry.getValue() > best;
        return entry.getValue() < best;
    }

    //Returns percent progress to goal (100% = goal met).
    public Double progressToGoal(String athleteName, String metricType) {
        Goal g = findGoal(athleteName, metricType); //find the goal
        if (g == null) return null;

        PerformanceEntry latest = null;
        for (PerformanceEntry e : entries) {
            if (e.getAthleteName().equalsIgnoreCase(athleteName) &&
                    e.getMetricType().equalsIgnoreCase(metricType)) {
                latest = e;
            } //set latest to last entry
        }
        if (latest == null) return null;

        double target = g.getTargetValue();
        double value = latest.getValue();

        if (g.isHigherIsBetter()) {
            return (value / target) * 100.0;
        } else {
            // lower is better (like time). if value == 0 avoid divide by zero
            if (value == 0) return null;
            return (target / value) * 100.0;
        }
    }
}
