import java.util.ArrayList;
public class InjuryTracker() extends InjuryIncident{
    private ArrayList<InjuryIncident> incidents;
    private Scanner sc;

    public void logIncident(InjuryIncident i){
        incidents.add(x);
    }

    public InjuryIncident getByAthlete(String name){
        super(athleteName);
        for(int i = 0, i<incidents.size()-1,i++){
            if(incidents.get(i).toString.contains(name)){
                return incidents.get(i);
            }
        }
    }

    public void listAll(){
        for(InjuryIncident injuries : incidents){
            System.out.println(injuries.toString);
        }

    }

}