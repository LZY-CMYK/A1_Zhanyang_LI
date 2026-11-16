import java.util.Objects;

/**
 * A patient recorded on an appointment: name plus contact mobile number.
 * Keeping the patient as its own class isolates these details from the
 * appointment itself.
 */
public class Patient {

    /** Patient full name. */
    private final String name;

    /** Mobile contact number (digits only). */
    private final String phone;

    /**
     * Constructs and validates a patient.
     *
     * @param name  patient name
     * @param phone mobile number, digits only
     */
    public Patient(String name, String phone) {
        // Validate the patient name: it must not be blank.
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name must not be empty.");
        }
        String trimmedPhone = phone == null ? null : phone.trim();
        // Validate the mobile: 7 to 15 digits only, checked character by character.
        if (trimmedPhone == null || !isDigitsOnly(trimmedPhone)
                || trimmedPhone.length() < 7 || trimmedPhone.length() > 15) {
            throw new IllegalArgumentException("Phone must contain 7 to 15 digits.");
        }
        this.name = name.trim();
        this.phone = trimmedPhone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Patient: " + name + " | Mobile: " + phone;
    }

    @Override
    public boolean equals(Object o) {
        // Same reference means equal.
        if (this == o) {
            return true;
        }
        // A different type is never equal.
        if (!(o instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) o;
        // Patients are matched by mobile number.
        return Objects.equals(phone, other.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone);
    }

    /**
     * Checks that every character in the text is a digit, without using a regular expression.
     *
     * @param text text to check
     * @return true if the text is non-null, non-empty and all digits
     */
    private static boolean isDigitsOnly(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
