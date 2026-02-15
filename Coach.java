/**
 * Coach user - the only role allowed to modify AthTrack data.
 */
public class Coach extends User {
    private final String teamName;

    public Coach(String id, String name, String teamName) {
        super(id, name);
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    @Override
    public String getRole() {
        return "COACH";
    }

    @Override
    public String toString() {
        return "Coach{id='" + id + "', name='" + name + "', team='" + teamName + "'}";
    }
}
