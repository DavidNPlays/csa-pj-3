import java.util.ArrayList;
public class PracticeScheduler {
    private ArrayList<String> practiceTimes;
    public PracticeScheduler() {
        practiceTimes = new ArrayList<>();
    }
    public void addPracticeTime(String time) {
        practiceTimes.add(time);
    }
    public void showPracticeTimes() {
        if (practiceTimes.size() == 0) {
            System.out.println("No practices scheduled.");
        } else {
            System.out.println("Practice Schedule:");
            for (String t : practiceTimes) {
                System.out.println("- " + t);
            }
        }
    }
    public ArrayList<String> getPracticeTimes() {
        return practiceTimes;
    }
}
