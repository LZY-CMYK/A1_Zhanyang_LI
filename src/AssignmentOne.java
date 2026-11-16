import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstration program for the health service appointment system.
 *
 * <p>Following the brief, there is no user interface or menu: the {@code main}
 * method seeds the staff, then runs a short scripted simulation that creates
 * appointments, lists/filters/sorts them, cancels some, and prints both the
 * successful cases and the warning cases to the console.</p>
 */
public class AssignmentOne {

    /** Professionals registered at the clinic. */
    private final List<HealthProfessional> staff = new ArrayList<>();

    /** Appointment collection manager. */
    private final AppointmentManager manager = new AppointmentManager();

    /**
     * Seeds the initial staff mix used by the demonstration.
     */
    private void seedStaff() {
        // A realistic mix: three GPs, two specialists, two nurses, one dietitian.
        staff.add(new GeneralPractitioner("101", "Dr Anna Chen",
                Arrays.asList(Weekday.MONDAY, Weekday.WEDNESDAY, Weekday.FRIDAY), 12));
        staff.add(new GeneralPractitioner("102", "Dr Ben Lee", 8));
        staff.add(new GeneralPractitioner("103", "Dr May Wu", 5));
        staff.add(new Specialist("201", "Dr Noah Kim",
                Arrays.asList(Weekday.TUESDAY, Weekday.THURSDAY), "Cardiology"));
        staff.add(new Specialist("202", "Dr Sara Ray", "Paediatrics"));
        staff.add(new Nurse("301", "Nora Holt",
                Arrays.asList(Weekday.MONDAY, Weekday.WEDNESDAY), "Emergency"));
        staff.add(new Nurse("302", "Owen Gale", "Ward 3"));
        staff.add(new Dietitian("401", "Ivy Lane", "Sports nutrition"));
    }

    /**
     * Finds a registered professional by ID.
     *
     * @param id numeric professional ID
     * @return matching professional, or null if none matches
     */
    private HealthProfessional findById(String id) {
        for (HealthProfessional hp : staff) {
            if (hp.getId().equals(id)) {
                return hp;
            }
        }
        return null;
    }

    /** Prints a short horizontal separator line for readable console output. */
    private void printLine() {
        System.out.println("--------------------------------------------------");
    }

    /**
     * Prints all registered professionals with their role action.
     */
    private void listStaff() {
        System.out.println("\n--- Registered health professionals ---");
        for (int i = 0; i < staff.size(); i++) {
            HealthProfessional hp = staff.get(i);
            System.out.printf("  %d. %s%n      Action: %s%n", i + 1, hp, hp.performRoleAction());
        }
    }

    /**
     * Books one appointment by professional ID, validating the patient first.
     *
     * @param staffId      professional ID to book with
     * @param patientName  patient name
     * @param phone        patient mobile number
     * @param slot         chosen time slot
     */
    private void book(String staffId, String patientName, String phone, TimeSlot slot) {
        HealthProfessional professional = findById(staffId);
        if (professional == null) {
            System.out.println("WARNING: No professional with ID " + staffId + ".");
            return;
        }
        try {
            // Validate patient details through the Patient constructor, then book.
            Patient patient = new Patient(patientName, phone);
            System.out.println("  " + manager.addAppointment(patient, professional, slot));
        } catch (IllegalArgumentException ex) {
            // Surface patient validation problems as a warning.
            System.out.println("  WARNING: " + ex.getMessage());
        }
    }

    /**
     * Prints an appointment list with a heading.
     *
     * @param title      section heading
     * @param appts      appointments to print
     * @param emptyLabel message when the list is empty
     */
    private void printAppointments(String title, List<Appointment> appts, String emptyLabel) {
        System.out.println("\n--- " + title + " ---");
        if (appts.isEmpty()) {
            System.out.println("  " + emptyLabel);
            return;
        }
        for (Appointment appt : appts) {
            System.out.println("  " + appt);
        }
    }

    /**
     * Runs the end-to-end scripted demonstration: normal and error cases for
     * adding, viewing, filtering, sorting, and cancelling appointments.
     */
    private void runDemo() {
        // Opening banner for a clean, readable console layout.
        System.out.println("==================================================");
        System.out.println("        HEALTH SERVICE APPOINTMENT SYSTEM");
        System.out.println("==================================================");

        // Part 3: show the registered staff mix first.
        listStaff();

        printLine();
        System.out.println("\n===== 1. Adding appointments (normal cases) =====");
        book("101", "Alice Smith", "0411111111", TimeSlot.SLOT_0800);
        book("201", "Bob Jones", "0422222222", TimeSlot.SLOT_0900);
        book("301", "Charlie Lee", "0433333333", TimeSlot.SLOT_1000);
        book("401", "Diana Wang", "0444444444", TimeSlot.SLOT_1100);

        printLine();
        System.out.println("\n===== 2. Adding appointments (error cases) =====");

        // Same professional already booked into this slot: must be rejected.
        book("101", "Alice Smith", "0411111111", TimeSlot.SLOT_0800);

        // Invalid patient: blank name, must be rejected.
        book("102", "", "0455555555", TimeSlot.SLOT_0830);
        
        // Invalid patient: mobile too short, must be rejected.
        book("102", "Eve Brown", "123", TimeSlot.SLOT_0830);

        // Part 5: view all, time ordered by the Appointment Comparable.
        printAppointments("All appointments (time sorted)",
                manager.getAllAppointments(), "No appointments booked yet.");

        // Filter by professional ID.
        printAppointments("Appointments for professional 101",
                manager.filterByProfessionalId("101"), "No matching appointments.");

        // Filter by patient mobile.
        printAppointments("Appointments for 0422222222",
                manager.filterByPhone("0422222222"), "No matching appointments.");

        printLine();
        System.out.println("\n===== 3. Cancelling appointments (normal cases) =====");
        // Cancel the 101 08:00 booking by professional ID and slot.
        System.out.println("  " + manager.cancelByProfessionalAndSlot("101", TimeSlot.SLOT_0800));
        // Cancel Charlie's booking by mobile.
        System.out.println("  " + manager.cancelByPhone("0433333333"));

        printLine();
        System.out.println("\n===== 4. Cancelling appointments (error cases) =====");
        // This professional and slot combination has no booking: warn instead.
        System.out.println("  " + manager.cancelByProfessionalAndSlot("101", TimeSlot.SLOT_0830));
        // This mobile has no booking: warn instead.
        System.out.println("  " + manager.cancelByPhone("0999999999"));

        // Show the collection again after the cancellations.
        printAppointments("Appointments after cancellation",
                manager.getAllAppointments(), "No appointments booked yet.");
        System.out.println("\nTotal appointments remaining: " + manager.count());
    }

    /**
     * Program entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Build the system, seed the staff, then run the scripted console demonstration.
        AssignmentOne system = new AssignmentOne();
        system.seedStaff();
        system.runDemo();
    }
}
