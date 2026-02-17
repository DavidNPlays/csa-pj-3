public class AttendanceRecord {
    private final String practiceId;
    private final String athleteId;
    private final String athleteName;
    private final AttendanceStatus status;
    // Constructor
    public AttendanceRecord(String practiceId, String athleteId, String athleteName, AttendanceStatus status) {
        this.practiceId = practiceId;
        this.athleteId = athleteId;
        this.athleteName = athleteName;
        this.status = status;
    }
    // Getters
    public String getPracticeId() {
        return practiceId;
    }

    public String getAthleteId() {
        return athleteId;
    }

    public String getAthleteName() {
        return athleteName;
    }

    public AttendanceStatus getStatus() {
        return status;
    }
    // toString for display
    @Override
    public String toString() {
        return athleteName + " (" + athleteId + "): " + status;
    }
}
