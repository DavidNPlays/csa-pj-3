/**
 * A goal for an athlete metric (e.g., "50 free" target time).
 * higherIsBetter = false for time-based goals (lower is better).
 */
public class Goal {
    private final String athleteName;
    private final String metricType;
    private final double targetValue;
    private final String deadline;
    private final boolean higherIsBetter;

    public Goal(String athleteName, String metricType, double targetValue, String deadline, boolean higherIsBetter) {
        this.athleteName = athleteName;
        this.metricType = metricType;
        this.targetValue = targetValue;
        this.deadline = deadline;
        this.higherIsBetter = higherIsBetter;
    }

    public String getAthleteName() {
        return athleteName;
    }

    public String getMetricType() {
        return metricType;
    }

    public double getTargetValue() {
        return targetValue;
    }

    public String getDeadline() {
        return deadline;
    }

    public boolean isHigherIsBetter() {
        return higherIsBetter;
    }

    @Override
    public String toString() {
        return athleteName + " goal: " + metricType + " -> " + targetValue + " by " + deadline +
                (higherIsBetter ? " (higher better)" : " (lower better)");
    }
}
