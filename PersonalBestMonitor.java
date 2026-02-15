import java.util.ArrayList;
public Class PersonalBestMonitor(){
   private ArrayList<Goal> goals;
   private ArrayList<PerformanceEntry> entries;

    public void addGoal(Goal goal){
        goals.add(goal);
    }
    public void logEntry(PerformanceEntry entry){
        entries.add(entry);
    }
    public boolean isNewPB(PerformanceEntry entry){ //dont log entry until isNewPB is called
        private ArrayList<PerformanceEntry> current;
        String curName=entry.getName();
        String curType=entry.getType();
        String curGoal=entry.getGoal();
        String curValue=entry.getValue()
        int currentPB = 0
        for(PerformanceEntry current :entries){
            if(entries.getName()=curName && entries.getType()=curType && entries.getGoal()=curGoal){
                double cur = entries.getValue();
            }
            if(entry.gettypeOfGoal()){
            if(cur>currentPB){
                currentPB=cur;
            }
        }
        else{
            if(cur<currentPB){
               currentPB=cur; 
            }
        }
    }
       if(entry.gettypeOfGoal()){
            if(curValue>currentPB){
                return true;
            }
            else{return false;}
        }
        else{
            if(curValue<currentPB){
               return true;
            }
            else{return false;}
        } 
}
    public double progressToGoal(String name, double value, double type, Goal current){
        double current = current.getTarget();
        if(entry.gettypeOfGoal()){
            return (value/current)*100;
    }
    else{
        return (current/value)*100;
    }
}
}