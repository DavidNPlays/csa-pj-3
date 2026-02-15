/**
 * Injury incident record.
 */
public class InjuryIncident {
    private final String athleteName;
    private final String date;
    private final String description;
    private final String severity;
    private final String status;

    public InjuryIncident(String athleteName, String date, String description, String severity, String status) {
        this.athleteName = athleteName;
        this.date = date;
        this.description = description;
        this.severity = severity;
        this.status = status;
    }

    public String getAthleteName() {
        return athleteName;
    }

    @Override
    public String toString() {
        return athleteName + " | " + date + " | " + severity + " | " + status + " | " + description;
    }
}
