import java.util.ArrayList;

//Keeps track of injury incidents (ArrayList + search).
public class InjuryTracker {
    private final ArrayList<InjuryIncident> incidents; //arraylist of incidents

    public InjuryTracker() {
        incidents = new ArrayList<>();
    }

    public void logIncident(InjuryIncident i) { //adds a new incident
        incidents.add(i); 
    }

    //array list is made, incidents that include a certain athelete are added.
    public ArrayList<InjuryIncident> getByAthlete(String athleteName) {
        ArrayList<InjuryIncident> results = new ArrayList<>();
        for (InjuryIncident inc : incidents) {
            if (inc.getAthleteName().equalsIgnoreCase(athleteName)) results.add(inc);
        }
        return results;
    }

    //list all incidents
    public void listAll() {
        if (incidents.isEmpty()) {
            System.out.println("No injury incidents logged.");
            return;
        }
        System.out.println("=== Injury Incidents ===");
        for (InjuryIncident inc : incidents) {
            System.out.println(" - " + inc);
        }
    }
}
