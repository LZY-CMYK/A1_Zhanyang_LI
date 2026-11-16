import java.util.List;

/**
 * A medical specialist focused on one clinical field, such as paediatrics,
 * radiology or cardiology. Extends {@link HealthProfessional}.
 */
public class Specialist extends HealthProfessional {

    /** Clinical specialty, e.g. "Cardiology". */
    private final String field;

    /**
     * Constructs a specialist with full working-day list.
     *
     * @param id            numeric ID
     * @param name          full name
     * @param availableDays working days
     * @param field         clinical specialty
     */
    public Specialist(String id, String name, List<Weekday> availableDays, String field) {
        // Let the base class validate the shared ID, name and working days.
        super(id, name, availableDays);
        // Specialist-specific rule: the clinical field must not be blank.
        if (field == null || field.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialty field must not be empty.");
        }
        this.field = field.trim();
    }

    /**
     * Convenience constructor using the default weekday availability.
     *
     * @param id    numeric ID
     * @param name  full name
     * @param field clinical specialty
     */
    public Specialist(String id, String name, String field) {
        super(id, name);
        this.field = field;
    }

    public String getField() {
        return field;
    }

    @Override
    public String getRoleTitle() {
        return "Specialist (" + field + ")";
    }

    @Override
    public String performRoleAction() {
        return "Performs specialist assessment and treatment in " + field + ".";
    }

    @Override
    public String toString() {
        return getRoleTitle() + " {" + commonDetails()
                + " | Specialty: " + field + "}";
    }
}
