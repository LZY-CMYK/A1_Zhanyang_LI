import java.util.List;

/**
 * A dietitian who advises on nutrition and meal plans. Extends
 * {@link HealthProfessional}.
 */
public class Dietitian extends HealthProfessional {

    /** Area of dietary focus, e.g. "Sports nutrition". */
    private final String dietaryFocus;

    /**
     * Constructs a dietitian with full working-day list.
     *
     * @param id            numeric ID
     * @param name          full name
     * @param availableDays working days
     * @param dietaryFocus  nutrition focus area
     */
    public Dietitian(String id, String name, List<Weekday> availableDays, String dietaryFocus) {
        // Let the base class validate the shared ID, name and working days.
        super(id, name, availableDays);
        // Dietitian-specific rule: the dietary focus must not be blank.
        if (dietaryFocus == null || dietaryFocus.trim().isEmpty()) {
            throw new IllegalArgumentException("Dietary focus must not be empty.");
        }
        this.dietaryFocus = dietaryFocus.trim();
    }

    /**
     * Convenience constructor using the default weekday availability.
     *
     * @param id           numeric ID
     * @param name         full name
     * @param dietaryFocus nutrition focus area
     */
    public Dietitian(String id, String name, String dietaryFocus) {
        super(id, name);
        this.dietaryFocus = dietaryFocus;
    }

    public String getDietaryFocus() {
        return dietaryFocus;
    }

    @Override
    public String getRoleTitle() {
        return "Dietitian";
    }

    @Override
    public String performRoleAction() {
        return "Creates personalised meal plans focused on " + dietaryFocus + ".";
    }

    @Override
    public String toString() {
        return getRoleTitle() + " {" + commonDetails()
                + " | Focus: " + dietaryFocus + "}";
    }
}
