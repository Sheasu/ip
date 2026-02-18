package athena;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that needs to be done before a specific date/time.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
    protected LocalDateTime by;

    /**
     * Initializes a deadline task with a description and a deadline time.
     *
     * @param description Brief description of the task.
     * @param by The deadline time string in "yyyy-MM-dd HHmm" format.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDateTime.parse(by.trim(), INPUT_FORMAT);
    }

    @Override
    public String toSaveFormat() {
        return "D | " + super.toSaveFormat() + " | " + by.format(INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}
