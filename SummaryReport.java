import java.util.ArrayList;

// SummaryReport class
// Generates a summary report for a practice including:
// practice info, workout info, and attendance.

public class SummaryReport {
// generate method
// Input: Practice p (practice session)
// ArrayList<AttendanceRecord> attendance (attendance records)
// Output: A formatted String report summarizing the practice
    
    public String generate(Practice p, ArrayList<AttendanceRecord> attendance) {
        // StringBuilder is used to build the report text step-by-step
        StringBuilder sb = new StringBuilder();
        // Header of the report
        sb.append("=== Summary Report ===\n");
        // Add practice information
        sb.append(p).append("\n\n");

        // Get workout from the practice
        Workout w = p.getWorkout();
        // If a workout exists, print it
        if (w != null) {
            sb.append("Workout:\n").append(w).append("\n");
        } else {
            // Otherwise, show that no workout was logged
            sb.append("Workout: (none logged)\n\n");
        }
        // If attendance list is null or empty, show message and stop
        if (attendance == null || attendance.isEmpty()) {
            sb.append("Attendance: (none recorded)\n");
            return sb.toString();
        }
        // Attendance section header
        sb.append("Attendance:\n");
        // Count how many athletes are present
        int present = 0;
        // Loop through attendance records
        // If status is P (present), increase counter
        for (AttendanceRecord r : attendance) if (r.getStatus() == AttendanceStatus.P) present++;
       
        // Print attendance summary
        sb.append("Present: ").append(present).append("/").append(attendance.size()).append("\n");
        // Print each attendance record
        sb.append("List:\n");
        for (AttendanceRecord r : attendance) sb.append(" - ").append(r).append("\n");
       
        // Return the final report as a string
        return sb.toString();
    }
}
