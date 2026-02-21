package athena;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific deadline.
 * Requires a description and a date/time by which the task must be completed.
 */
public class Deadline extends Task {
    private static final String INPUT_PATTERN = "yyyy-MM-dd HHmm";
    private static final String OUTPUT_PATTERN = "MMM dd yyyy, h:mma";

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern(INPUT_PATTERN);
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern(OUTPUT_PATTERN);

    private static final String SAVE_TYPE = "D";
    private static final String SAVE_SEPARATOR = " | ";

    protected LocalDateTime by;

    /**
     * Constructs a Deadline task.
     *
     * @param description The content of the deadline.
     * @param by The date and time string in "yyyy-MM-dd HHmm" format.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDateTime.parse(by.trim(), INPUT_FORMAT);
    }

    /**
     * Formats the deadline for storage in the scrolls.
     *
     * @return A formatted string suitable for file saving.
     */
    @Override
    public String toSaveFormat() {
        return SAVE_TYPE + SAVE_SEPARATOR + super.toSaveFormat() + SAVE_SEPARATOR + by.format(INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}
