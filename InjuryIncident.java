// Injury incident record.
// This class represents one injury incident involving an athlete.
public class InjuryIncident {
    // Stores the name of the athlete involved
    private final String athleteName;
    // Stores the date of the incident
    private final String date;
    // Stores a description of the injury
    private final String description;
    // Stores the severity level of the injury (ex: minor, moderate, severe)
    private final String severity;
    // Stores the current status (ex: recovering, cleared, resting)
    private final String status;
    //constructor
    // Initializes all fields for an injury incident

    public InjuryIncident(String athleteName, String date, String description, String severity, String status) {
        this.athleteName = athleteName;
        this.date = date;
        this.description = description;
        this.severity = severity;
        this.status = status;
    }
    //getter
    // Returns the athlete's name
    public String getAthleteName() {
        return athleteName;
    }

    @Override
    // Returns a formatted string representation of the injury incident
    // Used when printing incidents in InjuryTracker
    public String toString() {
        return athleteName + " | " + date + " | " + severity + " | " + status + " | " + description;
    }
}
