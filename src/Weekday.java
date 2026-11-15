/**
 * Represents the fixed working days available in the clinic's weekly schedule.
 */
public enum Weekday {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday");

    /** Human-readable weekday label, e.g. "Monday". */
    private final String label;

    Weekday(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
