import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Attendance system: mark P/A for each athlete for a given practice.
 */
public class AttendanceChecker {
    // HashMap that stores attendance records
    // key = practice id
    // value = list of attendance records for that practice
    private final Map<String, ArrayList<AttendanceRecord>> practiceToRecords;
    // Constructor
    public AttendanceChecker() {
        practiceToRecords = new HashMap<>();
    }
    // Method to mark attendance for a given practice 
    public void markAttendance(Practice practice, ArrayList<Athlete> roster, Scanner sc) {
        // check if practice exists and roster is not empty
        if (practice == null) {
            System.out.println("Practice not found.");
            return;
        }
        if (roster.isEmpty()) {
            System.out.println("Roster is empty - add athletes first.");
            return;
        }
        // create new list of attendance records
        ArrayList<AttendanceRecord> records = new ArrayList<>();
        System.out.println("Mark attendance for " + practice.getId() + " (" + practice.getDateTime() + ")");
        // Loop through each athlete 
        for (Athlete a : roster) {
            AttendanceStatus st = readStatus(sc, a.getName());
            records.add(new AttendanceRecord(practice.getId(), a.getId(), a.getName(), st));
        }
        // Save the records in the map
        practiceToRecords.put(practice.getId(), records);
        System.out.println("Attendance saved for " + practice.getId() + ".");
    }
    // Helper method to read attendance status from user input
    private AttendanceStatus readStatus(Scanner sc, String athleteName) {
        while (true) {
            System.out.print(athleteName + " (P/A): ");
            String s = sc.nextLine().trim().toUpperCase();
            if (s.equals("P")) return AttendanceStatus.P;
            if (s.equals("A")) return AttendanceStatus.A;
            System.out.println("Please enter P or A.");
        }
    }
    // Method to get attendance records for a given practice
    public ArrayList<AttendanceRecord> getRecordsForPractice(String practiceId) {
        return practiceToRecords.getOrDefault(practiceId, new ArrayList<>());
    }
    // Method to print attendance records for a given practice
    public void printRecords(String practiceId) {
        ArrayList<AttendanceRecord> recs = getRecordsForPractice(practiceId);
        if (recs.isEmpty()) {
            System.out.println("No attendance records for " + practiceId + ".");
            return;
        }
        System.out.println("Attendance for " + practiceId + ":");
        for (AttendanceRecord r : recs) 
        {
            System.out.println(" - " + r);
        }
    }
}