public class PracticeMonitor extends AthleteList{
      private int praticesPerWeek;
    
      public PracticeMonitor (String sport, String team, int practicesPerWeek)
      {
        super(sport, team);
        this.practicesPerWeek = 5;
      }

      public boolean setPracticesPerWeek (Coach coach,int practices)
      {
        if ((!coach.authentic())||((practices<0)||(practices>5)))
        {
          return false;
        }
        else
        {
          this.practicesPerWeek = practices;
          return true;
        }
      }

      public int getPracticesPerWeek()
      {
        return practicesPerWeek;
      }

}
