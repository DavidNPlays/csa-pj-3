public Class Goal(){
    public String name;
    public String type;
    public double target;
    public String deadline;
    public boolean typeOfGoal;//true is higher is a better value, false is lower is a better value

    public Goal(String name, String type, double target, String deadline, boolean typeOfGoal){
        this.name=name;
        this.type=type;
        this.target=target;
        this.deadline=deadline;
        this.typeOfGoal=typeOfGoal;
    }

    public String toString(){
        return name + " has a goal for " + type + " of " + target + " by " + deadline;
    }

    public double getTarget(){
        return target;
    }
}