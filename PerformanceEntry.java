//A logged performance entry for a metric (value + date + notes).
public class PerformanceEntry {
    private final String athleteName;
    private final String metricType;
    private final double value;
    private final String date;
    private final String notes;

    public PerformanceEntry(String athleteName, String metricType, double value, String date, String notes) {
        this.athleteName = athleteName;
        this.metricType = metricType;
        this.value = value;
        this.date = date;
        this.notes = notes;
    }
    //getters and setters
    public String getAthleteName() {
        return athleteName;
    }

    public String getMetricType() {
        return metricType;
    }

    public double getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return athleteName + " entry: " + metricType + " = " + value + " (" + date + ")" +
                (notes == null || notes.isBlank() ? "" : " | notes: " + notes);
    }
}
