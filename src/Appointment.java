/**
 * A single appointment links one patient to one health professional for one
 * daily time slot. It can reference any professional type from Parts 1-2.
 *
 * <p>Appointments are naturally ordered by their time slot, so the class
 * implements {@link Comparable} for time-based sorting.</p>
 */
public class Appointment implements Comparable<Appointment> {

    /** Booked patient. */
    private final Patient patient;

    /** Booked professional (any subtype of HealthProfessional). */
    private final HealthProfessional professional;

    /** Chosen time slot. */
    private final TimeSlot slot;

    /**
     * Constructs and validates an appointment.
     *
     * @param patient      booked patient
     * @param professional booked professional
     * @param slot         chosen time slot
     */
    public Appointment(Patient patient, HealthProfessional professional, TimeSlot slot) {
        // An appointment cannot exist without a patient.
        if (patient == null) {
            throw new IllegalArgumentException("Patient is required.");
        }
        // An appointment cannot exist without a professional.
        if (professional == null) {
            throw new IllegalArgumentException("Health professional is required.");
        }
        // An appointment cannot exist without a time slot.
        if (slot == null) {
            throw new IllegalArgumentException("Time slot is required.");
        }
        this.patient = patient;
        this.professional = professional;
        this.slot = slot;
    }

    public Patient getPatient() {
        return patient;
    }

    public HealthProfessional getProfessional() {
        return professional;
    }

    public TimeSlot getSlot() {
        return slot;
    }

    @Override
    public int compareTo(Appointment other) {
        // Order by clock time: earlier slot comes first.
        return Integer.compare(this.slot.toMinutes(), other.slot.toMinutes());
    }

    @Override
    public String toString() {
        return String.format("[%s] %s -> %s",
                slot.getLabel(), patient, professional);
    }
}
