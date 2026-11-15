import java.util.List;

/**
 * A general practitioner (GP): a first-contact doctor who can prescribe
 * medication. Extends the shared {@link HealthProfessional} model.
 */
public class GeneralPractitioner extends HealthProfessional {

    /** Years of clinical experience. */
    private final int yearsExperience;

    /**
     * Constructs a GP with full working-day list.
     *
     * @param id              numeric ID
     * @param name            full name
     * @param availableDays   working days
     * @param yearsExperience years in practice (non-negative)
     */
    public GeneralPractitioner(String id, String name, List<Weekday> availableDays,
                               int yearsExperience) {
        // Let the base class validate the shared ID, name and working days.
        super(id, name, availableDays);
        // GP-specific rule: years of experience cannot be negative.
        if (yearsExperience < 0) {
            throw new IllegalArgumentException("Years of experience cannot be negative.");
        }
        this.yearsExperience = yearsExperience;
    }

    /**
     * Convenience constructor using the default weekday availability.
     *
     * @param id              numeric ID
     * @param name            full name
     * @param yearsExperience years in practice
     */
    public GeneralPractitioner(String id, String name, int yearsExperience) {
        super(id, name);
        this.yearsExperience = yearsExperience;
    }

    public int getYearsExperience() {
        return yearsExperience;
    }

    @Override
    public String getRoleTitle() {
        return "General Practitioner";
    }

    @Override
    public String performRoleAction() {
        return "Diagnoses common conditions and prescribes medication.";
    }

    @Override
    public String toString() {
        return getRoleTitle() + " {" + commonDetails()
                + " | Years of experience: " + yearsExperience + "}";
    }
}
