package athena;

/**
 * Represents a task that occurs during a specific time period.
 */
public class Event extends Task {
    private static final String SAVE_TYPE = "E";
    private static final String SAVE_SEPARATOR = " | ";

    protected String from;
    protected String to;

    /**
     * Constructs an Event task.
     *
     * @param description The content of the event.
     * @param from The start time/description.
     * @param to The end time/description.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Formats the event for storage in the scrolls.
     *
     * @return A formatted string suitable for file saving.
     */
    @Override
    public String toSaveFormat() {
        return SAVE_TYPE + SAVE_SEPARATOR + super.toSaveFormat()
                + SAVE_SEPARATOR + from + SAVE_SEPARATOR + to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
