import java.util.ArrayList;

/**
 * Generates a text summary report for a practice: workout + attendance + notes.
 */
public class SummaryReport {

    public String generate(Practice p, ArrayList<AttendanceRecord> attendance) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Summary Report ===\n");
        sb.append(p).append("\n\n");

        Workout w = p.getWorkout();
        if (w != null) {
            sb.append("Workout:\n").append(w).append("\n");
        } else {
            sb.append("Workout: (none logged)\n\n");
        }

        if (attendance == null || attendance.isEmpty()) {
            sb.append("Attendance: (none recorded)\n");
            return sb.toString();
        }

        sb.append("Attendance:\n");
        int present = 0;
        for (AttendanceRecord r : attendance) if (r.getStatus() == AttendanceStatus.P) present++;

        sb.append("Present: ").append(present).append("/").append(attendance.size()).append("\n");
        sb.append("List:\n");
        for (AttendanceRecord r : attendance) sb.append(" - ").append(r).append("\n");

        return sb.toString();
    }
}
