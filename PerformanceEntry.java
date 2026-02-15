public Class PerformanceEntry(){
    public String name;
    public String type;
    public double value;
    public String date;
    public String notes;
    public String goal;
    public boolean typeOfGoal;//true is higher is a better value, false is lower is a better value

    public Goal(String name, String type, double value, String date, String notes, String goal, boolean type){
        this.name=name;
        this.type=type;
        this.value=value;
        this.date=date;
        this.notes=notes
        this.goal=goal
        this.higher=higher;
    }

    public String toString(){
        return name + " made a performance entry for " + type + " ,the new value is " + value + " on " + date + " Notes: " + notes;
    }
    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public String getGoal(){
        return goal;
    }
    public double getValue(){
        return value;
    }
    public boolean gettypeOfGoal(){
        return typeOfGoal;
    }





}