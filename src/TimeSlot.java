/**
 * Represents one fixed daily time slot of the clinic. The clinic runs from
 * 08:00 to 16:00 in 30-minute intervals, so every possible slot is listed here.
 *
 * <p>Slots compare by clock time, which allows appointments to be ordered
 * earliest to latest.</p>
 */
public enum TimeSlot {
    SLOT_0800(8, 0),
    SLOT_0830(8, 30),
    SLOT_0900(9, 0),
    SLOT_0930(9, 30),
    SLOT_1000(10, 0),
    SLOT_1030(10, 30),
    SLOT_1100(11, 0),
    SLOT_1130(11, 30),
    SLOT_1200(12, 0),
    SLOT_1230(12, 30),
    SLOT_1300(13, 0),
    SLOT_1330(13, 30),
    SLOT_1400(14, 0),
    SLOT_1430(14, 30),
    SLOT_1500(15, 0),
    SLOT_1530(15, 30);

    private final int hour;
    private final int minute;

    TimeSlot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Returns the slot start time as minutes since midnight.
     *
     * @return total minutes used for time ordering
     */
    public int toMinutes() {
        // Convert the slot to minutes since midnight for easy comparison.
        return hour * 60 + minute;
    }

    /**
     * Returns the clock label, e.g. "08:30".
     *
     * @return formatted start time
     */
    public String getLabel() {
        // Format as a fixed-width clock label, e.g. "08:30".
        return String.format("%02d:%02d", hour, minute);
    }

    /**
     * Looks up a slot by its clock label such as "08:30".
     *
     * @param label requested time
     * @return matching TimeSlot, or null if none matches
     */
    public static TimeSlot fromLabel(String label) {
        if (label == null) {
            return null;
        }
        // Look for the slot whose clock label matches the requested text.
        for (TimeSlot slot : values()) {
            if (slot.getLabel().equals(label.trim())) {
                return slot;
            }
        }
        return null;
    }

    /**
     * Compares this slot to another by clock time.
     *
     * @param other slot to compare
     * @return negative/zero/positive by earlier/equal/later time
     */
    public int compareByTime(TimeSlot other) {
        // Compare two slots by their minutes-since-midnight values.
        return Integer.compare(this.toMinutes(), other.toMinutes());
    }
}
