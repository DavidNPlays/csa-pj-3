/**
 * Tracks practices per week and a weekly plan using a 2D array.
 */
public class PracticeMonitor {
    private int practicesPerWeek;

    // 2D array[day][slot] for weekly schedule
    // slot 0 = morning, slot 1 = afternoon
    private final String[][] weeklySchedule;
    
    public PracticeMonitor() {
        practicesPerWeek = 0;
        weeklySchedule = new String[7][2];
    }
    // Getters and setters
    public void setPracticesPerWeek(int n) {
        practicesPerWeek = n;
    }

    public int getPracticesPerWeek() {
        return practicesPerWeek;
    }
    // boolean method to check valid day and slot
    public boolean assignPractice(int dayIndex0to6, int slot0or1, String time) {
        if (dayIndex0to6 < 0 || dayIndex0to6 > 6) return false;
        if (slot0or1 < 0 || slot0or1 > 1) return false;

        // Check if the slot is already occupied
        if (weeklySchedule[dayIndex0to6][slot0or1] != null) return false;
        weeklySchedule[dayIndex0to6][slot0or1] = time;
        return true;
    }
    // Method to print the weekly schedule
    public void printWeeklySchedule() {
        String[] days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
        System.out.println("=== Weekly Practice Plan (2D array) ===");
        for (int d = 0; d < 7; d++) {
            System.out.print(days[d] + ": ");
            boolean printed = false;
            for (int s = 0; s < 2; s++) {
                if (weeklySchedule[d][s] != null) {
                    if (printed) System.out.print(" | ");
                    System.out.print(weeklySchedule[d][s]);
                    printed = true;
                }
            }
            if (!printed) System.out.print("(none)");
            System.out.println();
        }
    }
}
