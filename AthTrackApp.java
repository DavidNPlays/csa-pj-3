import java.util.ArrayList;
import java.util.Scanner;

/**
 * AthTrack - console dashboard for coaches.
 * Demonstrates: OOP, abstraction/inheritance, ArrayList, 2D array, loops, conditionals, HashMap.
 */
public class AthTrackApp {
    private final Scanner sc;
    private final AuthService auth;

    private Coach currentCoach;

    private final AthleteRoster roster;
    private final TeamSorter teamSorter;
    private final ManualAdjustmentService manualAdjuster;

    private final PracticeMonitor practiceMonitor;
    private final PracticeScheduler practiceScheduler;

    private final AttendanceChecker attendanceChecker;
    private final SummaryReport summaryReport;

    private final PersonalBestMonitor pbMonitor;
    private final InjuryTracker injuryTracker;

    private final PlayerSearchFilter searchFilter;

    // last teams to support manual swap
    private ArrayList<ArrayList<Athlete>> lastTeams;

    public AthTrackApp() {
        sc = new Scanner(System.in);
        auth = new AuthService();

        roster = new AthleteRoster();
        teamSorter = new TeamSorter();
        manualAdjuster = new ManualAdjustmentService();

        practiceMonitor = new PracticeMonitor();
        practiceScheduler = new PracticeScheduler();

        attendanceChecker = new AttendanceChecker();
        summaryReport = new SummaryReport();

        pbMonitor = new PersonalBestMonitor();
        injuryTracker = new InjuryTracker();

        searchFilter = new PlayerSearchFilter();
        lastTeams = null;
    }

    public static void main(String[] args) {
        new AthTrackApp().run();
    }

    public void run() {
        currentCoach = auth.login(sc);
        if (currentCoach == null) {
            System.out.println("Login failed. Exiting.");
            return;
        }

        // Polymorphism demo for abstract User:
        User u = currentCoach;
        System.out.println("Logged in as: " + u.getName() + " (" + u.getRole() + ")\n");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = InputHelper.readInt(sc, "Choose an option: ", 0, 12);

            switch (choice) {
                case 1 -> addAthleteFlow();
                case 2 -> roster.printRoster();
                case 3 -> sortTeamsFlow();
                case 4 -> manualSwapFlow();
                case 5 -> practiceMonitorFlow();
                case 6 -> schedulePracticeFlow();
                case 7 -> logWorkoutFlow();
                case 8 -> takeAttendanceFlow();
                case 9 -> printSummaryFlow();
                case 10 -> personalBestFlow();
                case 11 -> injuryFlow();
                case 12 -> searchFilterFlow();
                case 0 -> running = false;
            }
            System.out.println();
        }

        System.out.println("Goodbye!");
    }

    private void printMenu() {
        System.out.println("=== AthTrack Menu ===");
        System.out.println("1) Add athlete to roster");
        System.out.println("2) View roster");
        System.out.println("3) Team sorter (balanced teams by rating)");
        System.out.println("4) Manual adjustment (swap athletes between teams)");
        System.out.println("5) Practice monitor (set practices/week + weekly 2D schedule)");
        System.out.println("6) Schedule a practice session");
        System.out.println("7) Log workout for a practice");
        System.out.println("8) Attendance checker (P/A for a practice)");
        System.out.println("9) Generate summary report for a practice");
        System.out.println("10) Personal Best monitor (goals + entries)");
        System.out.println("11) Injury tracker");
        System.out.println("12) Player search & filter");
        System.out.println("0) Exit");
    }

    private void addAthleteFlow() {
        System.out.println("=== Add Athlete ===");
        String id = InputHelper.readNonEmpty(sc, "Athlete ID: ");
        String name = InputHelper.readNonEmpty(sc, "Name: ");
        int grade = InputHelper.readInt(sc, "Grade (9-12): ", 9, 12);
        String sport = InputHelper.readNonEmpty(sc, "Sport/team name (e.g., Swim): ");
        TeamLevel level = InputHelper.readTeamLevel(sc, "Level");
        int rating = InputHelper.readInt(sc, "Rating (1-100): ", 1, 100);
        String notes = "";
        if (InputHelper.readYesNo(sc, "Add notes?")) {
            notes = InputHelper.readNonEmpty(sc, "Notes: ");
        }

        roster.addAthlete(new Athlete(id, name, grade, sport, level, rating, notes));
        System.out.println("Added athlete: " + name);
    }

    private void sortTeamsFlow() {
        if (roster.getAll().isEmpty()) {
            System.out.println("Roster is empty - add athletes first.");
            return;
        }
        int numTeams = InputHelper.readInt(sc, "Number of teams (2-6): ", 2, 6);
        lastTeams = teamSorter.sortIntoTeams(roster.getAll(), numTeams);
        teamSorter.printTeams(lastTeams);
    }

    private void manualSwapFlow() {
        if (lastTeams == null) {
            System.out.println("No teams have been generated yet. Run Team sorter first.");
            return;
        }
        System.out.println("=== Manual Swap ===");
        String n1 = InputHelper.readNonEmpty(sc, "Athlete name #1: ");
        String n2 = InputHelper.readNonEmpty(sc, "Athlete name #2: ");
        boolean ok = manualAdjuster.swapAthletesByName(lastTeams, n1, n2);
        if (!ok) {
            System.out.println("Swap failed: make sure both names exist in the teams exactly.");
            return;
        }
        System.out.println("Swap complete.");
        teamSorter.printTeams(lastTeams);
    }

    private void practiceMonitorFlow() {
        System.out.println("=== Practice Monitor ===");
        int perWeek = InputHelper.readInt(sc, "Practices per week (0-14): ", 0, 14);
        practiceMonitor.setPracticesPerWeek(perWeek);
        System.out.println("Saved practices/week: " + practiceMonitor.getPracticesPerWeek());

        if (InputHelper.readYesNo(sc, "Fill weekly schedule now?")) {
            System.out.println("Days: 0=Mon 1=Tue 2=Wed 3=Thu 4=Fri 5=Sat 6=Sun");
            for (int i = 0; i < perWeek; i++) {
                int day = InputHelper.readInt(sc, "Day index for practice #" + (i + 1) + ": ", 0, 6);
                int slot = InputHelper.readInt(sc, "Slot (0 or 1) for that day: ", 0, 1);
                String time = InputHelper.readNonEmpty(sc, "Time (e.g., 3:30 PM): ");
                boolean ok = practiceMonitor.assignPractice(day, slot, time);
                if (!ok) {
                    System.out.println("Conflict/invalid spot. Try again for this practice.");
                    i--;
                }
            }
        }
        practiceMonitor.printWeeklySchedule();
    }

    private void schedulePracticeFlow() {
        System.out.println("=== Schedule Practice ===");
        String dt = InputHelper.readNonEmpty(sc, "Date/time (e.g., 2026-02-15 15:30): ");
        String type = InputHelper.readNonEmpty(sc, "Type (e.g., Conditioning/Technique): ");
        String notes = "";
        if (InputHelper.readYesNo(sc, "Add notes?")) notes = InputHelper.readNonEmpty(sc, "Notes: ");

        Practice p = practiceScheduler.schedulePractice(dt, type, notes);
        System.out.println("Scheduled: " + p);
    }

    private void logWorkoutFlow() {
        practiceScheduler.listPractices();
        if (practiceScheduler.getPractices().isEmpty()) return;

        String pid = InputHelper.readNonEmpty(sc, "Enter practice ID to attach workout (e.g., P001): ");
        Practice p = practiceScheduler.findById(pid);
        if (p == null) {
            System.out.println("Practice not found.");
            return;
        }

        System.out.println("=== Create Workout for " + p.getId() + " ===");
        String title = InputHelper.readNonEmpty(sc, "Workout title: ");
        int dur = InputHelper.readInt(sc, "Duration minutes (1-300): ", 1, 300);
        int intensity = InputHelper.readInt(sc, "Intensity 1-10: ", 1, 10);
        Workout w = new Workout(title, dur, intensity);

        int items = InputHelper.readInt(sc, "How many workout items? (1-20): ", 1, 20);
        for (int i = 0; i < items; i++) {
            String desc = InputHelper.readNonEmpty(sc, "Item " + (i + 1) + " description: ");
            String reps = InputHelper.readNonEmpty(sc, "Item " + (i + 1) + " reps (e.g., 8x50): ");
            w.addItem(new WorkoutItem(desc, reps));
        }

        p.setWorkout(w);
        System.out.println("Workout saved.");
    }

    private void takeAttendanceFlow() {
        practiceScheduler.listPractices();
        if (practiceScheduler.getPractices().isEmpty()) return;

        String pid = InputHelper.readNonEmpty(sc, "Enter practice ID for attendance: ");
        Practice p = practiceScheduler.findById(pid);
        attendanceChecker.markAttendance(p, roster.getAll(), sc);
    }

    private void printSummaryFlow() {
        practiceScheduler.listPractices();
        if (practiceScheduler.getPractices().isEmpty()) return;

        String pid = InputHelper.readNonEmpty(sc, "Enter practice ID for report: ");
        Practice p = practiceScheduler.findById(pid);
        if (p == null) {
            System.out.println("Practice not found.");
            return;
        }

        String report = summaryReport.generate(p, attendanceChecker.getRecordsForPractice(pid));
        System.out.println(report);
    }

    private void personalBestFlow() {
        System.out.println("=== Personal Best Monitor ===");
        System.out.println("1) Add goal");
        System.out.println("2) Log performance entry");
        System.out.println("3) View goals");
        System.out.println("4) View entries");
        System.out.println("5) Progress to goal");
        int choice = InputHelper.readInt(sc, "Choose: ", 1, 5);

        if (choice == 1) {
            String name = InputHelper.readNonEmpty(sc, "Athlete name: ");
            String metric = InputHelper.readNonEmpty(sc, "Metric type (e.g., 50 free time): ");
            double target = InputHelper.readInt(sc, "Target value (integer for simplicity): ", 1, 100000);
            String deadline = InputHelper.readNonEmpty(sc, "Deadline (e.g., May 1): ");
            boolean higher = InputHelper.readYesNo(sc, "Is higher better for this metric? (time goals usually 'no')");
            pbMonitor.addGoal(new Goal(name, metric, target, deadline, higher));
            System.out.println("Goal added.");
        } else if (choice == 2) {
            String name = InputHelper.readNonEmpty(sc, "Athlete name: ");
            String metric = InputHelper.readNonEmpty(sc, "Metric type: ");
            double value = InputHelper.readInt(sc, "Value (integer for simplicity): ", 0, 100000);
            String date = InputHelper.readNonEmpty(sc, "Date (e.g., 2026-02-15): ");
            String notes = "";
            if (InputHelper.readYesNo(sc, "Add notes?")) notes = InputHelper.readNonEmpty(sc, "Notes: ");
            PerformanceEntry e = new PerformanceEntry(name, metric, value, date, notes);
            pbMonitor.logEntry(e);

            boolean pb = pbMonitor.isNewPersonalBest(e);
            System.out.println("Entry logged.");
            if (pb) System.out.println("[PB] New personal best for " + name + " (" + metric + ")!");
            else System.out.println("Not a PB this time.");
        } else if (choice == 3) {
            if (pbMonitor.getGoals().isEmpty()) System.out.println("No goals.");
            else for (Goal g : pbMonitor.getGoals()) System.out.println(" - " + g);
        } else if (choice == 4) {
            if (pbMonitor.getEntries().isEmpty()) System.out.println("No entries.");
            else for (PerformanceEntry e : pbMonitor.getEntries()) System.out.println(" - " + e);
        } else {
            String name = InputHelper.readNonEmpty(sc, "Athlete name: ");
            String metric = InputHelper.readNonEmpty(sc, "Metric type: ");
            Double pct = pbMonitor.progressToGoal(name, metric);
            if (pct == null) System.out.println("Need both a goal and at least one entry to compute progress.");
            else System.out.printf("Progress: %.1f%%\n", pct);
        }
    }

    private void injuryFlow() {
        System.out.println("=== Injury Tracker ===");
        System.out.println("1) Log incident");
        System.out.println("2) List all incidents");
        System.out.println("3) Search incidents by athlete name");
        int choice = InputHelper.readInt(sc, "Choose: ", 1, 3);

        if (choice == 1) {
            String name = InputHelper.readNonEmpty(sc, "Athlete name: ");
            String date = InputHelper.readNonEmpty(sc, "Date: ");
            String desc = InputHelper.readNonEmpty(sc, "Description: ");
            String sev = InputHelper.readNonEmpty(sc, "Severity (low/med/high): ");
            String status = InputHelper.readNonEmpty(sc, "Status (resting/cleared/etc): ");
            injuryTracker.logIncident(new InjuryIncident(name, date, desc, sev, status));
            System.out.println("Incident logged.");
        } else if (choice == 2) {
            injuryTracker.listAll();
        } else {
            String name = InputHelper.readNonEmpty(sc, "Athlete name: ");
            ArrayList<InjuryIncident> res = injuryTracker.getByAthlete(name);
            if (res.isEmpty()) System.out.println("No incidents found for " + name + ".");
            else for (InjuryIncident i : res) System.out.println(" - " + i);
        }
    }

    private void searchFilterFlow() {
        if (roster.getAll().isEmpty()) {
            System.out.println("Roster is empty - add athletes first.");
            return;
        }
        System.out.println("=== Search & Filter ===");
        System.out.println("1) Search by name");
        System.out.println("2) Filter by level/grade/minRating");
        int choice = InputHelper.readInt(sc, "Choose: ", 1, 2);

        if (choice == 1) {
            String q = InputHelper.readNonEmpty(sc, "Name contains: ");
            ArrayList<Athlete> res = searchFilter.searchByName(roster.getAll(), q);
            if (res.isEmpty()) System.out.println("No matches.");
            else for (Athlete a : res) System.out.println(" - " + a);
        } else {
            TeamLevel level = null;
            if (InputHelper.readYesNo(sc, "Filter by level?")) {
                level = InputHelper.readTeamLevel(sc, "Level");
            }
            int grade = -1;
            if (InputHelper.readYesNo(sc, "Filter by grade?")) {
                grade = InputHelper.readInt(sc, "Grade (9-12): ", 9, 12);
            }
            int minRating = -1;
            if (InputHelper.readYesNo(sc, "Filter by minimum rating?")) {
                minRating = InputHelper.readInt(sc, "Min rating (0-100): ", 0, 100);
            }

            ArrayList<Athlete> res = searchFilter.filter(roster.getAll(), level, grade, minRating);
            if (res.isEmpty()) System.out.println("No matches.");
            else for (Athlete a : res) System.out.println(" - " + a);
        }
    }
}
