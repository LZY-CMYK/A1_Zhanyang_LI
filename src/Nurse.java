import java.util.List;

/**
 * A nurse assigned to a ward, responsible for patient care tasks such as
 * taking blood samples. Extends {@link HealthProfessional}.
 */
public class Nurse extends HealthProfessional {

    /** Ward the nurse works on, e.g. "Emergency". */
    private final String ward;

    /**
     * Constructs a nurse with full working-day list.
     *
     * @param id            numeric ID
     * @param name          full name
     * @param availableDays working days
     * @param ward          assigned ward
     */
    public Nurse(String id, String name, List<Weekday> availableDays, String ward) {
        // Let the base class validate the shared ID, name and working days.
        super(id, name, availableDays);
        // Nurse-specific rule: the assigned ward must not be blank.
        if (ward == null || ward.trim().isEmpty()) {
            throw new IllegalArgumentException("Ward must not be empty.");
        }
        this.ward = ward.trim();
    }

    /**
     * Convenience constructor using the default weekday availability.
     *
     * @param id   numeric ID
     * @param name full name
     * @param ward assigned ward
     */
    public Nurse(String id, String name, String ward) {
        super(id, name);
        this.ward = ward;
    }

    public String getWard() {
        return ward;
    }

    @Override
    public String getRoleTitle() {
        return "Nurse";
    }

    @Override
    public String performRoleAction() {
        return "Provides nursing care and can take a blood sample on the " + ward + " ward.";
    }

    @Override
    public String toString() {
        return getRoleTitle() + " {" + commonDetails()
                + " | Ward: " + ward + "}";
    }
}
