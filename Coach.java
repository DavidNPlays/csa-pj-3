public class Coach extends User {

    private String team;
    public Coach(String id, String name, String team) {
        super(id, name);
        this.team=team;
    }
}