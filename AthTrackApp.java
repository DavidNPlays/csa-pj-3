import java.util.ArrayList;
import java.util.Scanner;

// Main application class for AthTrack.
// This class runs the program, displays the menu, and connects all features together.
public class AthTrackApp {
    //final variables for immutability(doesn't change)
    // Scanner for user input
    private final Scanner sc;
    // Authentication service for coach login
    private final AuthService auth;
    // Stores the currently logged-in coach
    private Coach currentCoach;
  
    // Core system components
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

    // last teams to support manual swap(in order to remember)
    private ArrayList<ArrayList<Athlete>> lastTeams;
    
    // Constructor initializes all services and systems
    public AthTrackApp() {
        sc = new Scanner(System.in);
        //initializing
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
        //null at the start bc haven't made teams yet
        lastTeams = null;
    }
    // Program entry point
    public static void main(String[] args) {
        //run command
        new AthTrackApp().run();
    }
    // Main program loop
    public void run() {
        // Coach login
        currentCoach = auth.login(sc);
        if (currentCoach == null) {
            // if user inputs the wrong login 3 times, restart the program
            System.out.println("Login failed. Exiting.");
            return;
        }
        // Display login confirmation
        User u = currentCoach;
        System.out.println("Logged in as: " + u.getName() + " (" + u.getRole() + ")\n");

        boolean running = true;
        // Main menu loop
        while (running) {
            printMenu();
            int choice = InputHelper.readInt(sc, "Choose an option: ", 0, 12);
            //all options(functionalities) for user to pick
            if(choice == 1) {
                addAthleteFlow();
            }
            if(choice == 2) {
                roster.printRoster();
            }
            if(choice == 3) {
                sortTeamsFlow();
            }
            if(choice == 4) {
                manualSwapFlow();
            }
            if(choice == 5) {
                practiceMonitorFlow();
            }
            if(choice == 6) {
                schedulePracticeFlow();
            }
            if(choice == 7) {
                logWorkoutFlow();
            }
            if(choice == 8) {
                takeAttendanceFlow();
            }
            if(choice == 9) {
                printSummaryFlow();
            }
            if(choice == 10) {
                personalBestFlow();
            }
            if(choice == 11) {
                injuryFlow();
            }
            if(choice == 12) {
                searchFilterFlow();
            }
            if(choice == 0) {
                running = false;
            }

}
        // Program exit message
        System.out.println("Goodbye!");
    }
    // Prints the main menu options
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
    // Flow for adding an athlete to the roster
    // Prompts user for athlete info and adds athlete to roster
    private void addAthleteFlow() {
        System.out.println("=== Add Athlete ===");
        // Read athlete information from user
        String id = InputHelper.readNonEmpty(sc, "Athlete ID: ");
        String name = InputHelper.readNonEmpty(sc, "Name: ");
        int grade = InputHelper.readInt(sc, "Grade (9-12): ", 9, 12);
        String sport = InputHelper.readNonEmpty(sc, "Sport/team name (e.g., Swim): ");
        TeamLevel level = InputHelper.readTeamLevel(sc, "Level");
        int rating = InputHelper.readInt(sc, "Rating (1-100): ", 1, 100);
        // Optional notes
        String notes = "";
        if (InputHelper.readYesNo(sc, "Add notes?")) {
            notes = InputHelper.readNonEmpty(sc, "Notes: ");
        }
        // Add athlete object to roster
        roster.addAthlete(new Athlete(id, name, grade, sport, level, rating, notes));
        System.out.println("Added athlete: " + name);
    }
    // Flow for sorting athletes into balanced teams
    private void sortTeamsFlow() {
        // Check if roster is empty
        if (roster.getAll().isEmpty()) {
            System.out.println("Roster is empty - add athletes first.");
            return;
        }
        // Ask how many teams to create
        int numTeams = InputHelper.readInt(sc, "Number of teams (2-6): ", 2, 6);
        // Sort athletes into teams and store result
        lastTeams = teamSorter.sortIntoTeams(roster.getAll(), numTeams);
        // Display teams
        teamSorter.printTeams(lastTeams);
    }
    // Flow for manually swapping athletes between teams
    private void manualSwapFlow() {
        // Ensure teams already exist
        if (lastTeams == null) {
            System.out.println("No teams have been generated yet. Run Team sorter first.");
            return;
        }
        System.out.println("=== Manual Swap ===");
        // Get athlete names to swap
        String n1 = InputHelper.readNonEmpty(sc, "Athlete name #1: ");
        String n2 = InputHelper.readNonEmpty(sc, "Athlete name #2: ");
        // Attempt swap
        boolean ok = manualAdjuster.swapAthletesByName(lastTeams, n1, n2);
        if (!ok) {
            System.out.println("Swap failed: make sure both names exist in the teams exactly.");
            return;
        }
        System.out.println("Swap complete.");
        teamSorter.printTeams(lastTeams);
    }
    // Flow for practice monitor (weekly practice schedule)
    private void practiceMonitorFlow() {
        System.out.println("=== Practice Monitor ===");
        // Set number of practices per week
        int perWeek = InputHelper.readInt(sc, "Practices per week (0-14): ", 0, 14);
        practiceMonitor.setPracticesPerWeek(perWeek);
        System.out.println("Saved practices/week: " + practiceMonitor.getPracticesPerWeek());
        // Option to fill schedule
        if (InputHelper.readYesNo(sc, "Fill weekly schedule now?")) {
            System.out.println("Days: 0=Mon 1=Tue 2=Wed 3=Thu 4=Fri 5=Sat 6=Sun");
            for (int i = 0; i < perWeek; i++) {
                int day = InputHelper.readInt(sc, "Day index for practice #" + (i + 1) + ": ", 0, 6);
                int slot = InputHelper.readInt(sc, "Slot (0 or 1) for that day: ", 0, 1);
                String time = InputHelper.readNonEmpty(sc, "Time (e.g., 3:30 PM): ");
                // Try to assign practice time
                boolean ok = practiceMonitor.assignPractice(day, slot, time);
                // Retry if invalid
                if (!ok) {
                    System.out.println("Conflict/invalid spot. Try again for this practice.");
                    i--;
                }
            }
        }
        // Print weekly schedule
        practiceMonitor.printWeeklySchedule();
    }
    // Flow for scheduling a single practice session
    private void schedulePracticeFlow() {
        System.out.println("=== Schedule Practice ===");
        String dt = InputHelper.readNonEmpty(sc, "Date/time (e.g., 2026-02-15 15:30): ");
        String type = InputHelper.readNonEmpty(sc, "Type (e.g., Conditioning/Technique): ");
        String notes = "";
        if (InputHelper.readYesNo(sc, "Add notes?")) notes = InputHelper.readNonEmpty(sc, "Notes: ");

        Practice p = practiceScheduler.schedulePractice(dt, type, notes);
        System.out.println("Scheduled: " + p);
    }
    // Flow for logging workout information
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
        // Add workout items
        for (int i = 0; i < items; i++) {
            String desc = InputHelper.readNonEmpty(sc, "Item " + (i + 1) + " description: ");
            String reps = InputHelper.readNonEmpty(sc, "Item " + (i + 1) + " reps (e.g., 8x50): ");
            w.addItem(new WorkoutItem(desc, reps));
        }

        p.setWorkout(w);
        System.out.println("Workout saved.");
    }
    // Flow for taking attendance
    private void takeAttendanceFlow() {
        practiceScheduler.listPractices();
        if (practiceScheduler.getPractices().isEmpty()) return;

        String pid = InputHelper.readNonEmpty(sc, "Enter practice ID for attendance: ");
        Practice p = practiceScheduler.findById(pid);
        attendanceChecker.markAttendance(p, roster.getAll(), sc);
    }
    // Flow for printing summary report
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
    // Flow for personal best monitoring
    // Lets user add goals, log performance entries, view data, and check progress
    private void personalBestFlow() {
        // Menu options for PB feature
        System.out.println("=== Personal Best Monitor ===");
        System.out.println("1) Add goal");
        System.out.println("2) Log performance entry");
        System.out.println("3) View goals");
        System.out.println("4) View entries");
        System.out.println("5) Progress to goal");
        // Read which option the user chose
        int choice = InputHelper.readInt(sc, "Choose: ", 1, 5);
       
        // Option 1: Add a new goal for an athlete
        if (choice == 1) {
            // Read goal information
            String name = InputHelper.readNonEmpty(sc, "Athlete name: ");
            String metric = InputHelper.readNonEmpty(sc, "Metric type (e.g., 50 free time): ");
            double target = InputHelper.readInt(sc, "Target value (integer for simplicity): ", 1, 100000);
            String deadline = InputHelper.readNonEmpty(sc, "Deadline (e.g., May 1): ");
            // higher = true means bigger numbers are better (ex: points)
            // higher = false means smaller numbers are better (ex: time)
            boolean higher = InputHelper.readYesNo(sc, "Is higher better for this metric? (time goals usually 'no')");
            // Create Goal object and store it in the personal best monitor
            pbMonitor.addGoal(new Goal(name, metric, target, deadline, higher));
            System.out.println("Goal added.");
        // Option 2: Log a new performance entry (like a time or score)
        } else if (choice == 2) {
            // Read entry information
            String name = InputHelper.readNonEmpty(sc, "Athlete name: ");
            String metric = InputHelper.readNonEmpty(sc, "Metric type: ");
            double value = InputHelper.readInt(sc, "Value (integer for simplicity): ", 0, 100000);
            String date = InputHelper.readNonEmpty(sc, "Date (e.g., 2026-02-15): ");
            // Optional notes for entry
            String notes = "";
            if (InputHelper.readYesNo(sc, "Add notes?")) notes = InputHelper.readNonEmpty(sc, "Notes: ");
            // Create the performance entry object
            PerformanceEntry e = new PerformanceEntry(name, metric, value, date, notes);
            // Store the entry in the monitor
            pbMonitor.logEntry(e);
          
            // Check if this entry is a new personal best
            boolean pb = pbMonitor.isNewPersonalBest(e);
            System.out.println("Entry logged.");
            // Print message depending on whether it is a PB or not
            if (pb) System.out.println("[PB] New personal best for " + name + " (" + metric + ")!");
            else System.out.println("Not a PB this time.");
        // Option 3: View all stored goals
        } else if (choice == 3) {
            // If no goals, print message
            if (pbMonitor.getGoals().isEmpty()) System.out.println("No goals.");
            // Otherwise loop through and print each goal
            else for (Goal g : pbMonitor.getGoals()) System.out.println(" - " + g);
        // Option 4: View all stored performance entries
        } else if (choice == 4) {
            // If no entries, print message
            if (pbMonitor.getEntries().isEmpty()) System.out.println("No entries.");
            // Otherwise loop through and print each entry
            else for (PerformanceEntry e : pbMonitor.getEntries()) System.out.println(" - " + e);
        // Option 5: Calculate progress to a goal
        } else {
            // Ask which athlete + metric to calculate progress for
            String name = InputHelper.readNonEmpty(sc, "Athlete name: ");
            String metric = InputHelper.readNonEmpty(sc, "Metric type: ");
            // progressToGoal returns a percent (0-100) or null if missing data
            Double pct = pbMonitor.progressToGoal(name, metric);
            // If missing a goal or entry, cannot compute progress
            if (pct == null) System.out.println("Need both a goal and at least one entry to compute progress.");
            // Otherwise print progress percent
            else System.out.printf("Progress: %.1f%%\n", pct);
        }
    }
    // Flow for injury tracker
    // Lets user log injuries, list all injuries, or search injuries by athlete name
    private void injuryFlow() {
        System.out.println("=== Injury Tracker ===");
        // Menu options for injury tracker
        System.out.println("1) Log incident");
        System.out.println("2) List all incidents");
        System.out.println("3) Search incidents by athlete name");
        // Read user choice
        int choice = InputHelper.readInt(sc, "Choose: ", 1, 3);
        
        // Option 1: Log a new injury incident
        if (choice == 1) {
            // Read incident details from user
            String name = InputHelper.readNonEmpty(sc, "Athlete name: ");
            String date = InputHelper.readNonEmpty(sc, "Date: ");
            String desc = InputHelper.readNonEmpty(sc, "Description: ");
            String sev = InputHelper.readNonEmpty(sc, "Severity (low/med/high): ");
            String status = InputHelper.readNonEmpty(sc, "Status (resting/cleared/etc): ");
            // Create InjuryIncident object and store it
            injuryTracker.logIncident(new InjuryIncident(name, date, desc, sev, status));
            System.out.println("Incident logged.");
        // Option 2: Print all incidents
        } else if (choice == 2) {
            injuryTracker.listAll();
        // Option 3: Search incidents by athlete name
        } else {
            // Ask for athlete name
            String name = InputHelper.readNonEmpty(sc, "Athlete name: ");
            // Get list of incidents for that athlete
            ArrayList<InjuryIncident> res = injuryTracker.getByAthlete(name);
            // Print results
            if (res.isEmpty()) System.out.println("No incidents found for " + name + ".");
            else for (InjuryIncident i : res) System.out.println(" - " + i);
        }
    }
    // Flow for player search and filter
    // Lets user search by name or filter by multiple attributes
    private void searchFilterFlow() {
        // Make sure there are athletes to search/filter
        if (roster.getAll().isEmpty()) {
            System.out.println("Roster is empty - add athletes first.");
            return;
        }
        System.out.println("=== Search & Filter ===");
        // Menu options
        System.out.println("1) Search by name");
        System.out.println("2) Filter by level/grade/minRating");
        // Read user choice
        int choice = InputHelper.readInt(sc, "Choose: ", 1, 2);
        
        // Option 1: Search athletes by name substring
        if (choice == 1) {
            // Read search query
            String q = InputHelper.readNonEmpty(sc, "Name contains: ");
            // Search roster list
            ArrayList<Athlete> res = searchFilter.searchByName(roster.getAll(), q);
            // Print results
            if (res.isEmpty()) System.out.println("No matches.");
            else for (Athlete a : res) System.out.println(" - " + a);
        // Option 2: Filter athletes by optional criteria
        } else {
            // Level filter is optional
            TeamLevel level = null;
            if (InputHelper.readYesNo(sc, "Filter by level?")) {
                level = InputHelper.readTeamLevel(sc, "Level");
            }
            // Grade filter is optional
            int grade = -1;
            if (InputHelper.readYesNo(sc, "Filter by grade?")) {
                grade = InputHelper.readInt(sc, "Grade (9-12): ", 9, 12);
            }
            // Minimum rating filter is optional
            int minRating = -1;
            if (InputHelper.readYesNo(sc, "Filter by minimum rating?")) {
                minRating = InputHelper.readInt(sc, "Min rating (0-100): ", 0, 100);
            }
            // Run filter with chosen criteria
            ArrayList<Athlete> res = searchFilter.filter(roster.getAll(), level, grade, minRating);
            // Print results
            if (res.isEmpty()) System.out.println("No matches.");
            else for (Athlete a : res) System.out.println(" - " + a);
        }
    }
}
