import java.util.ArrayList;

/**
 * Tracks goals and personal bests for athletes.
 */
public class PersonalBestMonitor {
    private final ArrayList<Goal> goals;
    private final ArrayList<PerformanceEntry> entries;

    public PersonalBestMonitor() {
        goals = new ArrayList<>();
        entries = new ArrayList<>();
    }

    public void addGoal(Goal goal) {
        goals.add(goal);
    }

    public ArrayList<Goal> getGoals() {
        return goals;
    }

    public void logEntry(PerformanceEntry entry) {
        entries.add(entry);
    }

    public ArrayList<PerformanceEntry> getEntries() {
        return entries;
    }

    public Goal findGoal(String athleteName, String metricType) {
        for (Goal g : goals) {
            if (g.getAthleteName().equalsIgnoreCase(athleteName) &&
                    g.getMetricType().equalsIgnoreCase(metricType)) {
                return g;
            }
        }
        return null;
    }

    /**
     * Checks if the given entry is a new personal best compared to previous entries
     * for the same athlete+metricType. Uses the goal's "higherIsBetter" when available;
     * if no goal exists, assume higher is better.
     */
    public boolean isNewPersonalBest(PerformanceEntry entry) {
        boolean higherIsBetter = true;
        Goal g = findGoal(entry.getAthleteName(), entry.getMetricType());
        if (g != null) higherIsBetter = g.isHigherIsBetter();

        Double best = null;
        for (PerformanceEntry e : entries) {
            if (e == entry) continue;
            if (e.getAthleteName().equalsIgnoreCase(entry.getAthleteName())
                    && e.getMetricType().equalsIgnoreCase(entry.getMetricType())) {
                if (best == null) best = e.getValue();
                else {
                    if (higherIsBetter) best = Math.max(best, e.getValue());
                    else best = Math.min(best, e.getValue());
                }
            }
        }

        // No prior entries -> treat as PB
        if (best == null) return true;

        if (higherIsBetter) return entry.getValue() > best;
        return entry.getValue() < best;
    }

    /**
     * Returns percent progress to goal (100% = goal met).
     */
    public Double progressToGoal(String athleteName, String metricType) {
        Goal g = findGoal(athleteName, metricType);
        if (g == null) return null;

        PerformanceEntry latest = null;
        for (PerformanceEntry e : entries) {
            if (e.getAthleteName().equalsIgnoreCase(athleteName) &&
                    e.getMetricType().equalsIgnoreCase(metricType)) {
                latest = e;
            }
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
