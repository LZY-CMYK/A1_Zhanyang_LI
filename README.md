# PROG2004 Assessment 1 — Health Service Appointment System

Java OOP demonstration: models health professionals, patients and appointments,
with a scripted console demonstration (no UI or menu, as required by the brief).

## Source layout (`src/`)

| Class | Role |
|---|---|
| `Weekday` | Enum of working weekdays. |
| `TimeSlot` | Enum of fixed 30-minute slots 08:00–15:30. |
| `HealthProfessional` | Abstract base: shared ID/name/availability, validation, equals/hashCode. |
| `GeneralPractitioner` | GP subclass (years of experience). |
| `Specialist` | Specialist subclass (field). |
| `Nurse` | Nurse subclass (ward). |
| `Dietitian` | Dietitian subclass (dietary focus). |
| `Patient` | Patient name + validated mobile number. |
| `Appointment` | Patient + professional + slot; `Comparable` for time sorting. |
| `AppointmentManager` | Collection: add (double-booking blocked), filter, sort, cancel. |
| `AssignmentOne` | Console program: seeds staff, then runs the scripted end-to-end demo (normal + error cases). |

## Console program (required by the brief)

Compile and run:

```bash
mkdir -p out
javac -d out src/*.java
java -cp out AssignmentOne
```

On start the program automatically runs the demonstration: list staff, add
appointments (normal and rejected cases), view/filter/sort, cancel (normal and
not-found cases), and show the updated collection.

## Notes

- The console program `AssignmentOne` is the deliverable matching the assessment brief.
- The brief specifies no user interface, no menu, no login and no saving; results are printed to the console.
- Tested with Eclipse Temurin OpenJDK 25; language features are compatible with JDK 17+.
