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
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Patient name must not be empty.");
        }
        // Validate the mobile: 7 to 15 digits only.
        if (phone == null || !phone.trim().matches("\\d{7,15}")) {
            throw new IllegalArgumentException("Phone must contain 7 to 15 digits.");
        }
        this.name = name.trim();
        this.phone = phone.trim();
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
        if (!(o instanceof Patient other)) {
            return false;
        }
        // Patients are matched by mobile number.
        return Objects.equals(phone, other.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone);
    }
}
