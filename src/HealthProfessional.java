import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents any health professional in the clinic. This abstract base class
 * holds the information and behaviour shared by every type of professional,
 * while leaving type-specific details and actions to its subclasses.
 *
 * <p>It cannot be instantiated directly: only a concrete subclass can be
 * created, which keeps the model honest about every staff member having a
 * real specialty.</p>
 */
public abstract class HealthProfessional {

    /** Numeric-only identifier of the professional. */
    private final String id;

    /** Full name of the professional. */
    private final String name;

    /** Fixed weekly days on which the professional works. */
    private final List<Weekday> availableDays;

    /**
     * Full constructor with validation.
     *
     * @param id            numeric-only professional ID
     * @param name          full professional name
     * @param availableDays weekly working days
     */
    protected HealthProfessional(String id, String name, List<Weekday> availableDays) {
        // Validate the ID: it must be non-null and contain digits only.
        if (id == null || !id.matches("\\d+")) {
            throw new IllegalArgumentException("Professional ID must contain digits only.");
        }
        // Validate the name: it must not be blank.
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Professional name must not be empty.");
        }
        // Validate availability: at least one working day must be supplied.
        if (availableDays == null || availableDays.isEmpty()) {
            throw new IllegalArgumentException("At least one working day is required.");
        }
        this.id = id;
        this.name = name.trim();
        // Store a defensive copy so external code cannot mutate the internal list.
        this.availableDays = new ArrayList<>(availableDays);
    }

    /**
     * Convenience constructor: defaults to working every weekday.
     *
     * @param id   numeric-only professional ID
     * @param name full professional name
     */
    protected HealthProfessional(String id, String name) {
        this(id, name, List.of(Weekday.MONDAY, Weekday.TUESDAY, Weekday.WEDNESDAY,
                Weekday.THURSDAY, Weekday.FRIDAY));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns a copy of the working days so the internal list cannot be changed
     * from outside the object.
     *
     * @return copy of the available days
     */
    public List<Weekday> getAvailableDays() {
        // Return a copy again, keeping the internal list private and read-only.
        return new ArrayList<>(availableDays);
    }

    /**
     * Returns the professional's specialty title, e.g. "General Practitioner".
     *
     * @return role title supplied by the subclass
     */
    public abstract String getRoleTitle();

    /**
     * Returns the description of the typical action this professional performs.
     *
     * @return role-specific action description
     */
    public abstract String performRoleAction();

    /**
     * Builds the shared part of the details shown for every professional.
     *
     * @return common details string
     */
    protected String commonDetails() {
        // Join the working-day labels with commas for a readable summary.
        StringBuilder days = new StringBuilder();
        for (Weekday day : availableDays) {
            if (days.length() > 0) {
                days.append(", ");
            }
            days.append(day.getLabel());
        }
        return String.format("ID: %s | Name: %s | Available: %s", id, name, days);
    }

    @Override
    public String toString() {
        return getRoleTitle() + " {" + commonDetails() + "}";
    }

    /**
     * Two professionals are considered equal when they share the same ID.
     *
     * @param o object to compare
     * @return true if IDs match
     */
    @Override
    public boolean equals(Object o) {
        // Same object reference means equal.
        if (this == o) {
            return true;
        }
        // A different type is never equal.
        if (!(o instanceof HealthProfessional other)) {
            return false;
        }
        // Two professionals are equal when their IDs match.
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
