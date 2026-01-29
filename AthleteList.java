public class AthleteList {
    private String name;
    private String sport;
    private String level;

    public AthleteList(String name, String sport, String level)
    {
        this.name = name;
        this.sport = sport;
        this.level = level;
    }

    public String getName()
    {
        return name;
    }

    public String getSport()
    {
        return sport;
    
    }

    public String getLevel()
    {
        return level;
    }

    public String toString()
    {
        return "Athlete Name: " + name + "| Sport: " + sport + "| Level: " + level;
    }

}