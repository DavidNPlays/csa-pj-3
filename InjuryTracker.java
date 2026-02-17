import java.util.ArrayList;

//Keeps track of injury incidents (ArrayList + search).
public class InjuryTracker {
    // ArrayList storing all injury incidents
    private final ArrayList<InjuryIncident> incidents; //arraylist of incidents
   
    // Constructor initializes the incident list
    public InjuryTracker() {
        incidents = new ArrayList<>();
    }
    
    // Adds a new injury incident to the list
    public void logIncident(InjuryIncident i) { //adds a new incident
        incidents.add(i); 
    }
    
    // Searches for incidents that match a specific athlete's name
    // A new ArrayList is created to store matching incidents
    //array list is made, incidents that include a certain athelete are added.
    public ArrayList<InjuryIncident> getByAthlete(String athleteName) {
        // List to store results of the search
        ArrayList<InjuryIncident> results = new ArrayList<>();
        // Loop through all stored incidents
        for (InjuryIncident inc : incidents) {
            // Compare athlete names (case-insensitive)
            if (inc.getAthleteName().equalsIgnoreCase(athleteName)) results.add(inc);
        }
        // Return matching incidents
        return results;
    }

    //list all incidents
    // Displays all logged injury incidents
    public void listAll() {
        // Check if there are no incidents recorded
        if (incidents.isEmpty()) {
            System.out.println("No injury incidents logged.");
            return;
        }
        // Header for incident list
        System.out.println("=== Injury Incidents ===");
        // Loop through and print each incident
        for (InjuryIncident inc : incidents) {
            System.out.println(" - " + inc);
        }
    }
}
