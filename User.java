/**
 * Abstract base user to demonstrate abstraction + inheritance.
 */
public abstract class User {
    protected final String id;
    protected final String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /** Polymorphic method: each subclass reports its role. */
    public abstract String getRole();
}
