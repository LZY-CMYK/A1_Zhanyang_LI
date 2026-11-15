import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the full collection of same-day appointments: adding with
 * double-booking protection, filtering, sorting, and cancelling.
 *
 * <p>An {@link ArrayList} is used because the system needs a growable, ordered
 * list that can be searched and sorted, which matches the daily booking model.</p>
 */
public class AppointmentManager {

    /** All current appointments. */
    private final List<Appointment> appointments = new ArrayList<>();

    /**
     * Adds an appointment after checking required details and preventing the
     * same professional from being booked twice into one slot.
     *
     * @param patient      booked patient
     * @param professional booked professional
     * @param slot         chosen slot
     * @return feedback message: success or warning reason
     */
    public String addAppointment(Patient patient, HealthProfessional professional, TimeSlot slot) {
        // Prevent double-booking: reject the same professional being booked into the same slot twice.
        for (Appointment existing : appointments) {
            if (existing.getProfessional().equals(professional)
                    && existing.getSlot().equals(slot)) {
                return "WARNING: " + professional.getName()
                        + " is already booked at " + slot.getLabel()
                        + ". Double-booking is not allowed.";
            }
        }

        try {
            // All checks pass, so create and store the appointment.
            appointments.add(new Appointment(patient, professional, slot));
            return "OK: Appointment booked for " + patient.getName()
                    + " with " + professional.getName() + " at " + slot.getLabel() + ".";
        } catch (IllegalArgumentException ex) {
            // Surface any validation failure from the Appointment constructor.
            return "WARNING: Appointment not created - " + ex.getMessage();
        }
    }

    /**
     * Returns all appointments sorted earliest to latest.
     *
     * @return sorted copy of the collection
     */
    public List<Appointment> getAllAppointments() {
        // Copy the list, then sort the copy by time (Appointment implements Comparable).
        List<Appointment> sorted = new ArrayList<>(appointments);
        Collections.sort(sorted);
        return sorted;
    }

    /**
     * Filters appointments by professional ID, time ordered.
     *
     * @param professionalId numeric professional ID
     * @return matching appointments
     */
    public List<Appointment> filterByProfessionalId(String professionalId) {
        List<Appointment> result = new ArrayList<>();
        // Keep only appointments whose professional ID matches.
        for (Appointment appt : appointments) {
            if (appt.getProfessional().getId().equals(professionalId)) {
                result.add(appt);
            }
        }
        Collections.sort(result);
        return result;
    }

    /**
     * Filters appointments by patient mobile number, time ordered.
     *
     * @param phone patient mobile number
     * @return matching appointments
     */
    public List<Appointment> filterByPhone(String phone) {
        List<Appointment> result = new ArrayList<>();
        // Keep only appointments booked under the matching patient mobile.
        for (Appointment appt : appointments) {
            if (appt.getPatient().getPhone().equals(phone)) {
                result.add(appt);
            }
        }
        Collections.sort(result);
        return result;
    }

    /**
     * Cancels the appointment for a given professional and slot.
     *
     * @param professionalId professional ID to match
     * @param slot           slot to match
     * @return feedback message
     */
    public String cancelByProfessionalAndSlot(String professionalId, TimeSlot slot) {
        Appointment target = null;
        // Find the single appointment matching the professional ID and slot.
        for (Appointment appt : appointments) {
            if (appt.getProfessional().getId().equals(professionalId)
                    && appt.getSlot().equals(slot)) {
                target = appt;
                break;
            }
        }
        // No match: return a warning instead of failing silently.
        if (target == null) {
            return "WARNING: No appointment found for professional ID "
                    + professionalId + " at " + slot.getLabel() + ".";
        }
        appointments.remove(target);
        return "OK: Cancelled appointment for " + target.getPatient().getName()
                + " at " + slot.getLabel() + ".";
    }

    /**
     * Cancels every appointment booked under a patient phone number.
     *
     * @param phone patient mobile number
     * @return feedback message
     */
    public String cancelByPhone(String phone) {
        List<Appointment> toRemove = new ArrayList<>();
        // Collect every appointment booked under this mobile.
        for (Appointment appt : appointments) {
            if (appt.getPatient().getPhone().equals(phone)) {
                toRemove.add(appt);
            }
        }
        // No match: return a warning instead of failing silently.
        if (toRemove.isEmpty()) {
            return "WARNING: No appointment found for phone number " + phone + ".";
        }
        appointments.removeAll(toRemove);
        return "OK: Cancelled " + toRemove.size() + " appointment(s) for " + phone + ".";
    }

    /**
     * Returns the current number of appointments.
     *
     * @return appointment count
     */
    public int count() {
        // Current number of stored appointments.
        return appointments.size();
    }
}
