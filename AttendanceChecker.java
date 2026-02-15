import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Attendance system: mark P/A for each athlete for a given practice.
 */
public class AttendanceChecker {
    private final Map<String, ArrayList<AttendanceRecord>> practiceToRecords;

    public AttendanceChecker() {
        practiceToRecords = new HashMap<>();
    }

    public void markAttendance(Practice practice, ArrayList<Athlete> roster, Scanner sc) {
        if (practice == null) {
            System.out.println("Practice not found.");
            return;
        }
        if (roster.isEmpty()) {
            System.out.println("Roster is empty - add athletes first.");
            return;
        }

        ArrayList<AttendanceRecord> records = new ArrayList<>();
        System.out.println("Mark attendance for " + practice.getId() + " (" + practice.getDateTime() + ")");
        for (Athlete a : roster) {
            AttendanceStatus st = readStatus(sc, a.getName());
            records.add(new AttendanceRecord(practice.getId(), a.getId(), a.getName(), st));
        }

        practiceToRecords.put(practice.getId(), records);
        System.out.println("Attendance saved for " + practice.getId() + ".");
    }

    private AttendanceStatus readStatus(Scanner sc, String athleteName) {
        while (true) {
            System.out.print(athleteName + " (P/A): ");
            String s = sc.nextLine().trim().toUpperCase();
            if (s.equals("P")) return AttendanceStatus.P;
            if (s.equals("A")) return AttendanceStatus.A;
            System.out.println("Please enter P or A.");
        }
    }

    public ArrayList<AttendanceRecord> getRecordsForPractice(String practiceId) {
        return practiceToRecords.getOrDefault(practiceId, new ArrayList<>());
    }

    public void printRecords(String practiceId) {
        ArrayList<AttendanceRecord> recs = getRecordsForPractice(practiceId);
        if (recs.isEmpty()) {
            System.out.println("No attendance records for " + practiceId + ".");
            return;
        }
        System.out.println("Attendance for " + practiceId + ":");
        for (AttendanceRecord r : recs) System.out.println(" - " + r);
    }
}
